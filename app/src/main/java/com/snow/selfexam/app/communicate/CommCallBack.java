package com.snow.selfexam.app.communicate;



/**
 * 通信回调函数接口。正常：调用 onBeanInfo 例外：调用 onException
 */
public interface CommCallBack {
    void onBeanInfo(Object beanInfo);

    void onException(NormalException e);
}
