package com.gs;

/**
 * @author zxwin
 * @date 2022/12/29
 */
public class RowContent {
    private String key;

    private Object value;

    public RowContent(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
