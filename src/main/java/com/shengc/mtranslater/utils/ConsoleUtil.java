package com.shengc.mtranslater.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * 用于控制台输入输出信息的 util
 *
 * @author sc
 * @create 2018-08-14-11:10
 **/
public class ConsoleUtil {
    /**
     * 打印信息到控制台
     * @param msg
     */
    public static void print(String msg) {
        System.out.println(msg);
    }

    /**
     * 获取用户在控制台输入的一行数据
     * @return
     * @throws IOException
     */
    public static String inputBack() throws IOException {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}
