package com.insert;

import java.util.List;

/**
 * @author zhaixinwei
 * @date 2023/2/6
 */
public class GenerateSql {

    private final static String SPACE = " ";
    private final static String ENTRY = "\n";
    private final static int threshold = 100;

    private String tableName;
    private List<String> csvField;
    private List<String> fixField;

    private List<String> csvValue;

    private DataSourceOpt dataSourceOpt;

    public GenerateSql(String tableName, List<String> csvField, List<String> fixField, List<String> csvValue, DataSourceOpt dataSourceOpt) {
        this.csvField = csvField;
        this.fixField = fixField;
        this.tableName = tableName;
        this.csvValue = csvValue;
        this.dataSourceOpt = dataSourceOpt;
    }

    public String generateSql(FixFieldPopulate populate) {
        StringBuilder sql = new StringBuilder("insert into").append(SPACE);
        sql.append(tableName).append(SPACE).append("(");
        // 填充key
        for (int i = 0; i < fixField.size(); i++) {
            sql.append(fixField.get(i).toLowerCase());
            sql.append(",");

        }
        for (int i = 0; i < csvField.size(); i++) {
            sql.append(csvField.get(i).toLowerCase());
            if (i != csvField.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(")").append(ENTRY);

        // 生成values
        sql.append(SPACE).append("values").append(ENTRY);

        StringBuilder values = new StringBuilder();

        for (int i = 0; i < csvValue.size(); i++) {
            values.append(SPACE).append("(");
            // 固定字段
            if(populate != null) {
                values.append(populate.populate());
            }
            // cvs字段
            values.append(csvValue.get(i));
            if (i % threshold == 0 || i == csvValue.size() - 1) {
                values.append(");").append(ENTRY);
                insert(sql.toString() + values.toString());
                values = new StringBuilder();
            } else {
                values.append("),").append(ENTRY);
            }
        }


        return null;
    }

    public void insert(String sql) {
        System.out.println("执行SQL:" + sql);
//        dataSourceOpt.option(sql);
    }
}
