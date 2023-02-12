package com.tuomin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tuomin.process.TuoMin;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author zhaixinwei
 * @date 2023/1/5
 */
public class TuoMinTemplate {
    public static final String JSON_DATA = "data";
    public static final String JSON_CODE = "code";

    private final Context context;


    public TuoMinTemplate(Context context) {
        this.context = context;
    }

    /**
     * 读取并解析excel
     */
    public void parse() {
        String rootDir = this.context.getRootDir();
        int sheetNum = this.context.getSheetNum();


        try {
            InputStream inputStream = new FileInputStream(rootDir);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            for (int i = 0; i < sheetNum; i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                System.out.println("解析sheet:" + sheet.getSheetName());
                Iterator<Row> rowIterator = sheet.rowIterator();

                while (rowIterator.hasNext()) {
                    Row next = rowIterator.next();
                    if (next.getRowNum() == 0) {
                        continue;
                    }
                    Cell cell = next.getCell(2);
                    if (cell == null || "".equals(cell.toString())) {
                        continue;
                    }
                    String json = cell.getStringCellValue();

                    String tuoMinJson = this.tuoMin(json);

                    if (tuoMinJson != null && !"".equals(tuoMinJson)) {
                        cell.setCellValue(tuoMinJson);
//                    Cell tuoMinFlag = next.getCell(3);
//                    tuoMinFlag.setCellValue("√");
                    }
                }
            }

            // 写出
            workbook.write(new FileOutputStream(this.context.getOutDir()));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String tuoMin(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json, Feature.OrderedField);
        String codeStr = String.valueOf(jsonObject.get(JSON_CODE));
        if (!"0".equals(codeStr)) {
            return null;
        }

        // 替换data中的数据
        Object jsonData = jsonObject.get(JSON_DATA);
        if (null == jsonData) {
            return null;
        }
        if (jsonData instanceof JSONObject) {
            replace((JSONObject) jsonData);
        } else if (jsonData instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) jsonData;
            Iterator<Object> iterator = jsonArray.stream().iterator();
            while (iterator.hasNext()) {
                JSONObject next = (JSONObject) iterator.next();
                replace(next);
            }
        } else {
            return null;
        }
        return jsonObject.toString(SerializerFeature.PrettyFormat, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue);
    }

    private void replace(JSONObject jsonData) {
        List<TuoMin> tuoMinStrategy = this.context.getTuoMinStrategy();
        for (TuoMin tuoMin : tuoMinStrategy) {
            tuoMin.doTuoMin(jsonData, Context.getFilterKeyList());
        }
    }

}
