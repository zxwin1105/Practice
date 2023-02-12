package com.gs.insert;

import com.gs.insert.entity.Value;

import java.util.List;

/**
 * @author zhaixinwei
 * @date 2023/1/10
 */
public class SqlGenerate {

    private String tableName;

    private final static String SPACE = " ";
    private final static String ENTRY = "\n";

    public SqlGenerate(String tableName) {
        this.tableName = tableName;
    }

    public String getInsertSql(List<String> keyList, List<List<Value>> valList) {
        StringBuilder sql = new StringBuilder("insert into").append(SPACE);
        sql.append(tableName).append(SPACE).append("(");
        // 填充key
        for (int i = 0; i < keyList.size(); i++) {
            sql.append(keyList.get(i).toLowerCase());
            if(i != keyList.size()-1){
                sql.append(",");
            }
        }
        sql.append(")").append(ENTRY);
        // 填充value
        sql.append(SPACE).append("values").append(ENTRY);
        for (int i = 0; i < valList.size(); i++) {
            sql.append(SPACE).append("(");
            List<Value> valueList = valList.get(i);
            for (int j = 0; j < valueList.size(); j++) {
                Value value = valueList.get(j);
                if (value.isStr()){
                    sql.append("'").append(value.getVal()).append("'");
                }else {
                    sql.append(value.getVal());

                }
                if(j != valueList.size()-1){
                    sql.append(",");
                }
            }
            if(i != valList.size()-1) {
                sql.append("),").append(ENTRY);
            }else {
                sql.append(");").append(ENTRY);
            }
        }

        return sql.toString();
    }
}
