package com.poi;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.IOException;

/**
 * 解析excel文件
 *
 * @author zhaixinwei
 * @date 2022/10/31
 */
public class Analysis {

    public static void main(String[] args) {
        new Analysis().analysis();
    }

    private void analysis() {
        POIFSFileSystem system = null;
        try {
            system = new POIFSFileSystem(new File("lifeCycles.xls"));
            HSSFWorkbook workbook = new HSSFWorkbook(system, true);
            HSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                for (Cell cell : row) {
                    // 需要获取cell类型
                    switch (cell.getCellTypeEnum()){
                        case _NONE:
                            System.out.print("null");
                            break;
                        case NUMERIC:
                            System.out.print(cell.getNumericCellValue());
                            break;
                        case STRING:
                            System.out.print(cell.getStringCellValue());
                            break;
                        case FORMULA:
                            System.out.print(cell.getArrayFormulaRange());
                            break;
                        case BLANK:
                            System.out.print("");
                            break;
                        case BOOLEAN:
                            System.out.print(cell.getBooleanCellValue());
                            break;
                        case ERROR:
                            System.out.print(cell.getErrorCellValue());
                            break;
                    }
                }
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
