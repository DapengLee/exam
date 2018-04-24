package com.zhougf.mytool.tools;

/**
 * Created by Administrator on 2016/9/22.
 */
public class Out {
    public static boolean isPrint = false;
    public static String imei = "";// "353918058397155"
    private static final String ss = "353918058397155,866335020026061";

    public static void println(String s) {
        if (isPrint || indexOf() >= 0) {
            System.out.println(s);
            // Log.i("Out", s);
        }
    }

    public static void print(String s) {
        if (isPrint || indexOf() >= 0) {
            System.out.print(s);
            // Log.i("Out", s);
        }
    }

    private static int indexOf() {
        if (imei.length() > 0) {
            return ss.indexOf(imei);
        } else {
            return -1;
        }
    }
}
