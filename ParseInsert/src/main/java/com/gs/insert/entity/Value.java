package com.gs.insert.entity;

/**
 * @author zhaixinwei
 * @date 2023/1/11
 */
public class Value {

    private Object val;

    private boolean isStr;


    public Value(Object val, boolean isStr) {
        this.val = val;
        this.isStr = isStr;
    }

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }

    public boolean isStr() {
        return isStr;
    }

    public void setStr(boolean str) {
        isStr = str;
    }

    @Override
    public String toString() {
        return "Value{" +
                "val=" + val +
                ", isStr=" + isStr +
                '}';
    }
}
