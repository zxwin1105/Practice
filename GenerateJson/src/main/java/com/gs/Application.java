package com.gs;

import com.gs.parser.ExcelParser;

/**
 * @author zxwin
 * @date 2022/12/29
 */
public class Application {

    public static void main(String[] args) {
        // excel目录或者excel文件本身路径
        String rootDir = "C:\\Users\\zxwin\\Desktop\\例子";

        // 输出json文件目录
        String outDir = "C:\\Users\\zxwin\\Desktop\\result";

        if (args != null && args.length == 2) {
            rootDir = args[0];
            outDir = args[1];
        }
        ExcelParser parser = new ExcelParser(rootDir, outDir);
        parser.parser();
    }
}
