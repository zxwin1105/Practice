package com.tuomin;

import com.tuomin.process.DataReplaceTuoMin;
import com.tuomin.process.NameReplaceTuoMin;

/**
 * @author zhaixinwei
 * @date 2023/1/5
 */
public class Application {
    public static void main(String[] args) {
        // 脱敏excel 路径
        String rootDir = "C:\\Users\\T470\\Desktop\\大港接口(未脱敏).xlsx";

        String outDir = "C:\\Users\\T470\\Desktop\\大港接口(完成脱敏).xlsx";


        Context context = new Context(3, rootDir, outDir);
        context.addStrategy(new DataReplaceTuoMin());
        context.addStrategy(new NameReplaceTuoMin());
        TuoMinTemplate tuoMinTemplate = new TuoMinTemplate(context);

        tuoMinTemplate.parse();

    }
}
