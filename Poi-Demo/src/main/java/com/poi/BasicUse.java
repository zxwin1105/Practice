package com.poi;


import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Date;

/**
 * 包名称说明
 * <p>
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


    public static void main(String[] args) {
        new BasicUse().lifeCycles();
    }

    /**
     * 创建一个 Workbook对象，Workbook对象相当于一个excel文件
     */
    private void createWorkBook() {
        // HSSFWorkbook用于创建.xls类型文件
        Workbook hssfWorkbook = new HSSFWorkbook();
        // XSSFWorkbook用于创建.xlsx类型文件
        Workbook xssfWorkbook = new XSSFWorkbook();

        // 将workbook写出到excel文件
        try (OutputStream os = new FileOutputStream("hsff.xls")) {
            xssfWorkbook.write(os);
        } catch (IOException e) {
            log.error("写出excel失败");
        }


        // 可以将本地一个excel文件读入为Workbook对象

        try {
            HSSFWorkbook sheets = new HSSFWorkbook(new FileInputStream("hsff.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建sheet对象，sheet对象代表excel文件中的文件薄
     */
    public void createSheet() {
        // 1. 创建workbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 2. 给workbook创建sheet
        // workbook.createSheet("default");
        // sheet名称不能超过31个字符，且不能使用非法字符。推荐使用工具类获取Sheet名称
        // 返回 sheet-1
        String safeSheetName = WorkbookUtil.createSafeSheetName("sheet-1*");
        workbook.createSheet(safeSheetName);
    }


    /**
     * 创建Cell对象，cell对象是excel中的每一个单元格
     */
    private void createCell() {
        // 1. 创建workbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 2. 创建sheet
        String safeSheetName = WorkbookUtil.createSafeSheetName("sheet-1*");
        HSSFSheet sheet = workbook.createSheet(safeSheetName);
        // 3. 创建行Row
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("name");
        row.createCell(2).setCellValue("age");

        CreationHelper creationHelper = workbook.getCreationHelper();
        // 写出xls到文件
        try (OutputStream os = new FileOutputStream("createSheet.xls")) {
            workbook.write(os);
        } catch (IOException e) {
            log.error("写出excel失败");
        }
    }

    /**
     * Cell 样式控制
     */
    private void cellStyled() {
        // 1. 创建workbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 2. 创建sheet
        String safeSheetName = WorkbookUtil.createSafeSheetName("sheet-1*");
        HSSFSheet sheet = workbook.createSheet(safeSheetName);
        // 3. 创建行Row
        HSSFRow row = sheet.createRow(0);

        HSSFCreationHelper creationHelper = workbook.getCreationHelper();
        // 无格式日期
        row.createCell(0).setCellValue(new Date());
        // 使用 cellStyle格式化日期
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd hh:mm:ss"));
        HSSFCell cell = row.createCell(1);
        cell.setCellValue(new Date());
        cell.setCellStyle(cellStyle);


        // 写出xls到文件
        try (OutputStream os = new FileOutputStream("createSheet.xls")) {
            workbook.write(os);
        } catch (IOException e) {
            log.error("写出excel失败");
        }
    }

    /**
     * HSSFWorkbook 或XSSFWorkbook 应该通过POIFSFileSystem或OPCPackage 来管理workbook完整生命周期
     */
    private void lifeCycles() {
        POIFSFileSystem system = null;
        try {
            system = new POIFSFileSystem(new File("lifeCycles.xls"));
            HSSFWorkbook workbook = new HSSFWorkbook(system.getRoot(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null != system){
                try {
                    system.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
//    public static void main(String[] args) throws IOException {
//        // 创建excel的文档对象
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        // 创建sheet
//        XSSFSheet sheet = workbook.createSheet("uploadTemplate");
//
//        // 设置下拉列表
//
////        addList();
//
//
//        // 设置级联列表
//
//        // 填充数据
//        XSSFRow directory = sheet.createRow(0);
//        directory.createCell(0).setCellValue("编号");
//        directory.createCell(1).setCellValue("姓名");
//        directory.createCell(2).setCellValue("年龄");
//        directory.createCell(3).setCellValue("性别");
//        directory.createCell(4).setCellValue("部门");
//
//        // 将workbook写出到磁盘
//        String path = "C:/Users/T470/Desktop/" + "uploadTemplate.xlsx";
//        log.debug("dir:{}", path);
//
//
//        FileOutputStream fileOutputStream = new FileOutputStream(path);
//        workbook.write(fileOutputStream);
//
//    }
//
//    /**
//     * 添加下拉列表
//     *
//     * @param workbook 工作空间
//     * @param sheet    工作薄
//     * @param data     下拉列表数据
//     * @param column   下拉列表列号
//     * @param from     开始行
//     * @param end      结束行
//     */
//    private static void dropDownList(Workbook workbook, Sheet sheet, String[] data, char column, int from, int end) {
//        // 1. 创建一个隐藏的sheet
//        String hiddenSheetName = "sheet" + workbook.getNumberOfSheets();
//        Sheet hiddenSheet = workbook.createSheet(hiddenSheetName);
//        // 2. 在隐藏sheet中添加下拉列
//        int colIndex = 0;
//        for (int rowIndex = 0; rowIndex < data.length; rowIndex++) {
//            Row row = hiddenSheet.createRow(rowIndex);
//            Cell cell = row.createCell(colIndex);
//            cell.setCellValue(data[rowIndex]);
//        }
//
//        // 真正设置下拉列操作
//        setDropDownList(sheet, column, from, end);
//
//    }
//
//    private static void setDropDownList(Sheet sheet, char column, int from, int end) {
//        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(from, end, column - 'A', column - 'A');
////        DVConstraint constraint = DVConstraint.createFormulaListConstraint(nameName);
////        sheet.addValidationData(new XSSFDataValidation(constraint,cellRangeAddressList));
//
//
//    }
}
