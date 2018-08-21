package com.shengc.mtranslater.utils;

/**
 * 全角字符与半角字符转换的util（unicode）
 *
 * @author sc
 * @create 2018-08-21-9:45
 * @referurl https://segmentfault.com/a/1190000010841143
 **/
public class AsciiUtil {
    // 全角空格 12288
    public static final char SBC_SPACE = 12288;
    //半角空格 32
    public static final char DBC_SPACE = 32;

    // ASCII character 33-126 <-> unicode 65281-65374
    public static final char ASCII_START = 33;

    public static final char ASCII_END = 126;

    public static final char UNICODE_START = 65281;

    public static final char UNICODE_END = 65374;
    // 全角半角转换间隔
    public static final char DBC_SBC_STEP = 65248;

    public static char sbc2dbc(char src){
        if (src == SBC_SPACE) {
            return DBC_SPACE;
        }

        if (src >= UNICODE_START && src <= UNICODE_END) {
            return (char) (src - DBC_SBC_STEP);
        }

        return src;
    }

    /**
     * Convert from SBC case to DBC case
     * 全角转半角
     * @param src
     * @return DBC case
     */
    public static String sbc2dbcCase(String src) {
        if (src == null) {
            return null;
        }
        char[] c = src.toCharArray();
        for (int i = 0; i < c.length; i++) {
            c[i] = sbc2dbc(c[i]);
        }
        return new String(c);
    }

    /**
     * 半角转全角
     * @param src
     * @return
     */
    public static char dbc2sbc(char src){
        if (src == DBC_SPACE) {
            return SBC_SPACE;
        }
        if (src <= ASCII_END) {
            return (char) (src + DBC_SBC_STEP);
        }
        return src;
    }

    /**
     * Convert from DBC case to SBC case.
     *
     * @param src
     * @return SBC case string
     */
    public static String dbc2sbcCase(String src) {
        if (src == null) {
            return null;
        }

        char[] c = src.toCharArray();
        for (int i = 0; i < c.length; i++) {
            c[i] = dbc2sbc(c[i]);
        }

        return new String(c);
    }

    public static void main(String[] args) {
        String data = "你 好　吗？hello!";
        System.out.println(data);
        System.out.println(dbc2sbcCase(data));
    }
}
