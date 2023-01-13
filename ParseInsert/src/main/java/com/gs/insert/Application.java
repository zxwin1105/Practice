package com.gs.insert;

import com.gs.insert.entity.Value;

import javax.sql.DataSource;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author zhaixinwei
 * @date 2023/1/10
 */
public class Application {

    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/talimu";

    private static final String USER = "postgres";
    private static final String PASS = "root";

    // 1. 读取json文件夹
    // 2. 解析json文件key 为字段名，value为属性名
    // 3. 插入数据库
    public static void main(String[] args) {
        String readDir = "C:\\Users\\T470\\Desktop\\塔里木\\数据对接\\data\\sd_well";
        // 数据库表名
        String tableName = "public.cd_well";

        List<String> filter = Collections.singletonList("well_type");

        File dir = new File(readDir);
        File[] files = dir.listFiles((dirFile, name) -> name.endsWith(".json"));
        if(files == null) return;

        for (File file : files) {
            System.out.println("开始解析文件:"+ file.getName());
            try {
                StringBuilder stringBuilder = new StringBuilder();
                InputStream is = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String temp = "";
                while ((temp = br.readLine()) != null){
                    stringBuilder.append(temp);
                }
                parser(tableName, stringBuilder.toString(), filter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void parser(String tableName, String json, List<String> filter){
        List<String> keyList = new ArrayList<>();
        List<List<Value>> valList = new ArrayList<>();
        new JsonParser().parser(json, keyList, valList, filter);

        // 生产sql
        SqlGenerate sqlGenerate = new SqlGenerate(tableName);
        String sql = sqlGenerate.getInsertSql(keyList, valList);
        // 插入数据
        DataSourceOpt dataSourceOpt = new DataSourceOpt(JDBC_DRIVER, DB_URL, USER, PASS);
        dataSourceOpt.option(sql);
    }
}
