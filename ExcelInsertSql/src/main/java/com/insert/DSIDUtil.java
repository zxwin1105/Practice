package com.insert;


import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.generator.UUIDGenerator;

/**
 * @author zhaixinwei
 * @date 2023/2/6
 */
public class DSIDUtil {

    public static String generateId(String prefix) {
        String res = UUID.fastUUID().toString().replace("-","");
        if (prefix != null && !"".equals(prefix)) {
            res = prefix + res;
        }
        return res;
    }

}


