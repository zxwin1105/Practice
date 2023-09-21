package com.gs;

/**
 * @author zxwin
 * @date 2022/12/22
 */
public class StringUtil {

    public static boolean isBlank(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

}
