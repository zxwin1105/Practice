package com.tuomin;

import com.tuomin.process.TuoMin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhaixinwei
 * @date 2023/1/5
 */
public class Context {

    private final int sheetNum;

    private final String rootDir;

    private final String outDir;

    private final static List<String> FILTER_KEY_LIST = new ArrayList<>();

    static {
        FILTER_KEY_LIST.addAll(Arrays.asList("sj","rq","dwdm"));
    }



    // 使用的脱敏链
    private final List<TuoMin> tuoMinStrategy = new ArrayList<>();

    public void addStrategy(TuoMin tuoMin) {
        tuoMinStrategy.add(tuoMin);
    }

    public Context(int sheetNum, String rootDir, String outDir) {
        this(sheetNum, rootDir, outDir, null);
    }

    public Context(int sheetNum, String rootDir, String ourDir, List<TuoMin> tuoMinStrategy) {
        this.sheetNum = sheetNum;
        this.rootDir = rootDir;
        this.outDir = ourDir;
        if (tuoMinStrategy != null) {
            this.tuoMinStrategy.addAll(tuoMinStrategy);
        }
    }

    public int getSheetNum() {
        return sheetNum;
    }

    public List<TuoMin> getTuoMinStrategy() {
        return tuoMinStrategy;
    }

    public String getRootDir() {
        return rootDir;
    }

    public String getOutDir() {
        return outDir;
    }

    public static List<String> getFilterKeyList() {
        return FILTER_KEY_LIST;
    }
}
