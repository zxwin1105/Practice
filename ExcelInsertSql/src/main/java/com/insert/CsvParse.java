package com.insert;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaixinwei
 * @date 2023/2/6
 */
public class CsvParse {


    public List<String> parse(String path) throws IOException {
        System.out.println("解析CSV");
        CSVReader reader = new CSVReaderBuilder(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))
                .build();
        reader.skip(1);
        List<String> res = new ArrayList<>();
        for (String[] strings : reader) {
            StringBuilder fields = new StringBuilder();
            for (int i = 0; i < strings.length; i++) {
                String field = strings[i];

                if ("".equals(field)) {
                    fields.append("null").append(",");
                }else {
                    if (i == 9) {
                        // 处理日期
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date parse = null;
                        try {
                            parse = format1.parse(field);
                        } catch (ParseException e) {
                            parse = new Date();
                        }
                        String format2 = format.format(parse);
                        fields.append("\'").append(format2).append("\'").append(",");

                    } else if (isNum(field)) {
                        fields.append(field).append(",");
                    } else {
                        fields.append("\'").append(field).append("\'").append(",");
                    }
                }
            }
//            }
//            System.out.println(fields.toString());
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            res.add(fields.substring(0, fields.length() - 1));
        }
        return res;
    }


    private boolean isNum(Object num) {
        if (num instanceof Number) {
            return true;
        } else if (num instanceof String) {
            try {
                Double.parseDouble((String) num);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public static void main(String[] args) throws ParseException {
        // 处理日期
        String field = "18/12/2020 00:00:00";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date parse = format1.parse(field);
        String format2 = format.format(parse);

        System.out.println(format2);
    }

}
