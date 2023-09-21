package com.gs.replace;

import com.google.common.collect.Multimap;

/**
 * @author zxwin
 * @date 2022/12/22
 */
public class ReplaceTemplate {

    private final Multimap<String,String> replaceMap;

    private final String rootPath;
    private final String outPath;

    private final static String DEVICE_PATH = "/deviceList";
    private final static String CONFIG_PATH = "/config";
    private final static String TELEMETRY_PATH = "/telemetry";

    public ReplaceTemplate(Multimap<String,String> map, String rootPath, String outPath) {
        this.replaceMap = map;
        this.rootPath = rootPath;
        this.outPath = outPath;
    }

    public void replace(){
        // 替换 DeviceList文件
        ReplaceDeviceList replaceDeviceList = new ReplaceDeviceList();
        System.out.println("********替换DeviceList文件***********");
        replaceDeviceList.replace(replaceMap, rootPath + DEVICE_PATH, outPath + DEVICE_PATH);
        // 替换 Config文件
        ReplaceConfig replaceConfig = new ReplaceConfig();
        System.out.println("********替换Config文件***********");
        replaceConfig.replace(replaceMap, rootPath + CONFIG_PATH, outPath + CONFIG_PATH);

        // 替换 telemetry
        System.out.println("********替换telemetry***********");
        replaceConfig.replace(replaceMap, rootPath + TELEMETRY_PATH, outPath + TELEMETRY_PATH);
    }
}
