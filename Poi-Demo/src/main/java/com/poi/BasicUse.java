package com.poi;


import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 包名称说明
 *
 * HSSF提供读写Microsoft Excel XLS格式档案的功能。
 * XSSF提供读写Microsoft Excel OOXML XLSX格式档案的功能。
 * HWPF提供读写Microsoft Word DOC格式档案的功能。
 * HSLF提供读写Microsoft PowerPoint格式档案的功能。
 * HDGF提供读Microsoft Visio格式档案的功能。
 * HPBF提供读Microsoft Publisher格式档案的功能。
 * HSMF提供读Microsoft Outlook格式档案的功能。
 *
 * @author zhaixinwei
 * @date 2022/10/28
 */
@Slf4j
public class BasicUse {

    public static void main(String[] args) throws IOException {
        // 创建excel的文档对象
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建sheet
        XSSFSheet sheet = workbook.createSheet("uploadTemplate");

        // 设置下拉列表

//        addList();


        // 设置级联列表

        // 填充数据
        XSSFRow directory = sheet.createRow(0);
        directory.createCell(0).setCellValue("编号");
        directory.createCell(1).setCellValue("姓名");
        directory.createCell(2).setCellValue("年龄");
        directory.createCell(3).setCellValue("性别");
        directory.createCell(4).setCellValue("部门");

        // 将workbook写出到磁盘
        String path ="C:/Users/T470/Desktop/"+ "uploadTemplate.xlsx";
        log.debug("dir:{}",path);


        FileOutputStream fileOutputStream = new FileOutputStream(path);
        workbook.write(fileOutputStream);

    }

    /**
     * 添加下拉列表
     * @param workbook 工作空间
     * @param sheet 工作薄
     * @param data 下拉列表数据
     * @param column 下拉列表列号
     * @param from 开始行
     * @param end 结束行
     */
    private static void dropDownList(Workbook workbook, Sheet sheet, String[] data,char column, int from,int end) {
        // 1. 创建一个隐藏的sheet
        String hiddenSheetName = "sheet" + workbook.getNumberOfSheets();
        Sheet hiddenSheet = workbook.createSheet(hiddenSheetName);
        // 2. 在隐藏sheet中添加下拉列
        int colIndex = 0;
        for (int rowIndex = 0; rowIndex < data.length; rowIndex++) {
            Row row = hiddenSheet.createRow(rowIndex);
            Cell cell = row.createCell(colIndex);
            cell.setCellValue(data[rowIndex]);
        }

        // 真正设置下拉列操作
        setDropDownList(sheet,column,from,end);

    }

    private static void setDropDownList(Sheet sheet, char column, int from, int end) {
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(from, end, column - 'A', column - 'A');
//        DVConstraint constraint = DVConstraint.createFormulaListConstraint(nameName);
//        sheet.addValidationData(new XSSFDataValidation(constraint,cellRangeAddressList));


    }
}
