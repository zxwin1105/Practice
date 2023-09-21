package com.gs.parser;

import com.alibaba.fastjson.JSONObject;
import com.gs.JsonBuilder;
import com.gs.RowContent;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author zxwin
 * @date 2022/12/29
 */
public class ExcelParser {

    private final String ROOT_DIR;
    private final String OUT_DIR;

    private final JsonBuilder jsonBuilder = new JsonBuilder();


    private static final String SKIP_SHEET_NAME = "填写示例";


    public ExcelParser(String rootDir, String outDir) {
        ROOT_DIR = rootDir;
        OUT_DIR = outDir;

    }

    public void parser() {
        File fIle = new File(ROOT_DIR);

        if (fIle.isDirectory()) {
            // 解析目录
            File[] files = fIle.listFiles((dir, name) -> (name.endsWith(".xlsx") || name.endsWith(".xls")) &&
                    !name.startsWith("~$"));
            if (Objects.isNull(files)) {
                return;
            }
            for (File file : files) {
                doParser(file.getPath());
            }
        } else {
            // 解析文件
            doParser(ROOT_DIR);
        }
    }

    private void doParser(String rootDir) {
        List<RowContent> list = new ArrayList<>();

        try {
            XSSFWorkbook book = new XSSFWorkbook(new FileInputStream(rootDir));
            Iterator<Sheet> sheetIterator = book.sheetIterator();
            // 遍历文件中的sheet，跳过示例sheet
            while (sheetIterator.hasNext()) {
                Sheet next = sheetIterator.next();
                if (!SKIP_SHEET_NAME.equals(next.getSheetName())) {
                    // 遍历row
                    int index = 0;
                    Iterator<Row> rowIterator = next.rowIterator();
                    while (rowIterator.hasNext()) {
                        Row row = rowIterator.next();
                        if (row.getRowNum() != 0) {
                            list.add(new RowContent(row.getCell(0).getStringCellValue(), row.getCell(1)));
                        }
                    }
                    // 将解析完的sheet内容，变为json格式，输出到目录
                    JSONObject generate = jsonBuilder.generate(list, OUT_DIR);
                    jsonBuilder.write(generate, new File(OUT_DIR + "\\" + next.getSheetName() + ".json"));
                    list.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
