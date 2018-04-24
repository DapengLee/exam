package com.snow.selfexam.app.config;


import android.widget.ImageView;

import com.snow.selfexam.app.utils.LogUtils;
import com.snow.selfexam.mvp.modle.BannerBean;

/**
 * Created by amew_000 on 2016/12/7.
 */
public class Constants {
    /*
    LogUtils.LEVEL_ALL:打开日志(显示所有的日志输出)
    LogUtils.LEVEL_OFF:关闭日志(屏蔽所有的日志输出)
    */
    public static final int DEBUGLEVEL = LogUtils.LEVEL_ALL;

    /*
    http://news.baidu.com/ns?cl=2&rn=20&tn=news&word=bibi
    https://eapply.abchina.com/onlinetrade/BusinessLoans/smesApplicationloans
    全局的URL地址在这里修改
     */
    public static final class URLS {
        // http://news.baidu.com/ns?cl=2&rn=20&tn=news&word=bibi
        //http://192.168.1.115:8888/goodexamsys/mobile/applogin?phone=13257495112&password=admin
        //public static final String BASE_URL ="http://192.168.1.140:8888/mobile";
        public static final String BASE_URL ="http://www.csshankao.com/goodexamsys/mobile";
        public static final String LOGIN_URL ="/applogin" ;   //登录账号
        public static final String REGIST_URL ="/register" ;  //注册账号
        public static final String FIX_URL="/editpwd" ;  //修改密码
        //http://192.168.1.118:8080/goodexamsys/mobile/choice
        public static final String CHOICE_URL="/choice" ;  //获取专业
        public static final String BANNER_URL="/getindex" ;
        //获取轮播图  http://192.168.1.156:8888/mobile/getbanner
        public static final String DB_URL="/getspec" ;  //获取专业和题库路径




    }

    public static final ImageView[] BANNER_IMGS = {

          //  BannerBean.DataBean =new BannerBean();
                  /*  "http://upload.jianshu.io/admin_banners/app_images/2542/5b04b8ccb01432425cb13dab1aed399da607b454.jpg",
                    "http://upload.jianshu.io/admin_banners/app_images/2547/ae57560a074ccbf0213c1ac004e6c8e27e9e4094.jpg",
                    "http://upload.jianshu.io/admin_banners/app_images/2523/7207d4150a150f14efbdfc4411bf05b0199ceffa.jpg",
                    "http://upload.jianshu.io/admin_banners/app_images/2540/9a7011f88c7fa0f471824111011ec8ffd08860f2.jpg"*/



            };
}