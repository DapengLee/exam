package com.snow.selfexam.app.utils;

/**
 * 用于关流的工具类
 * Created by amew_000 on 2016/12/7.
 */

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {
    /**
     * 关闭流
     */
    public static boolean close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                LogUtils.e(e);
            }
        }
        return true;
    }
}
