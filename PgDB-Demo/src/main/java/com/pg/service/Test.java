package com.pg.service;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author zhaixinwei
 * @date 2022/6/5
 * @description
 */
public class Test {

    private static  Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        // 输入行数
//        System.out.print("输入行数");
//        int row = scanner.nextInt();
//        String[] datas = new String[row];
//        // 每行数据
//        System.out.print("输入数值");
//        for (int i = 0; i < row; i++) {
//            datas[i] = scanner.nextLine();
//        }

        int row = 2;
        String[] datas = {
          "113222324323334414243445253", "2373845464748545556575864656667687475767778"
        };
        // 周长
        int[] c = new int[row];
        // 解析
        int[][] table = new int[64][64];

        for (int i = 0; i < row; i++) {
            int len = datas[i].length();
            char[] data = datas[i].toCharArray();
            // 填充值
            int num =  Integer.parseInt(data[0]+"");
            for (int j = 1; j < len; j+=2) {
                // 获取本次填充位置
                int dataRow = Integer.parseInt(data[j]+""),dataColumn = Integer.parseInt(data[j+1]+"");
                // 填充
                table[dataRow][dataColumn] = num;
                // 计算周长，需要判断当前位置左边和右边位置不为num周长+1；
                if(dataRow-1 < 0){
                    c[i]+=1;
                }else {
                    if(table[dataRow - 1][dataColumn] != num) {
                        c[i] += 1;
                    }
                }
                if(dataColumn-1 < 0 ){
                    c[i] +=1;
                }else {
                    if (table[dataRow][dataColumn-1] != num){
                        c[i]+=1;
                    }
                }
            }
        }
        for (int i = 0; i < table.length; i++) {
            System.out.println(Arrays.toString(table[i]));
        }
        System.out.println(Arrays.toString(c));
    }
}
