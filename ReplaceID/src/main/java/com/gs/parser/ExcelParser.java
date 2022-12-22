package com.gs.parser;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zxwin
 * @date 2022/12/22
 */
public class ExcelParser {

    private final Map<String,String> replaceMap = new HashMap<>();

    public void parser(String path){
        try {
            XSSFWorkbook book = new XSSFWorkbook(new FileInputStream(path));
            XSSFSheet sheet = book.getSheetAt(0);

            for (Row row : sheet) {
                if(row.getRowNum()==0){
                    continue;
                }
                // 第一列为new，第二列为old
                String key = row.getCell(1).getStringCellValue();
                String val = row.getCell(0).getStringCellValue();
                replaceMap.put(key, val);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String,String> getMap(){
        return replaceMap;
    }
}
