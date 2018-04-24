package com.snow.selfexam.app.communicate;

/**
 * Created by Administrator on 2017/11/18.
 */

public class NormalException extends Exception {

    // 错误编码(没有为0)
    private String errorCode = "错误编码";

    public NormalException(Exception e) {
        super(e);
    }

    public NormalException(String errorString) {
        super(errorString);
    }

    public NormalException(String errorCode, String errorString) {
        super(errorString);
        this.errorCode = errorCode;
    }

    public NormalException(Exception e, String errorString) {
        super(errorString, e);
    }

    /**
     * 是否有错误编码(有错误编码的，有服务器返回的错误信息，没有的一般为网络异常)
     *
     * @return 是否含有错误编码
     */
    public boolean hasErrorCode() {
        return !errorCode.equals("OK");
    }
}