package com.snow.selfexam.app.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.snow.selfexam.R;
import com.snow.selfexam.mvp.view.activities.LoginActivity;
import com.zhougf.mytool.tools.Out;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SmallTools {

    public static void activityIn(Activity activity) {
        activity.overridePendingTransition(R.anim.push_right_in,
                R.anim.push_left_out);
    }

    public static void activityOut(Activity activity) {
        activity.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_right_out);
    }

    public static ProgressDialog show(Context context) {
        return show(context, null, false);
    }

    public static ProgressDialog show(Context context, CharSequence content) {
        return show(context, content, false);
    }

    public static ProgressDialog show(Context context, CharSequence content,
                                      boolean cancelable) {
        ProgressDialog pd = ProgressDialog.show(context, "", "");
        View view = LayoutInflater.from(context).inflate(
                R.layout.progress_dialog, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_myProgressDialog_);
        if (content == null || content.length() < 1) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(content);
        }
        pd.setContentView(view);
        pd.setCancelable(cancelable);
        pd.show();
        return pd;
    }


    public static void openLoginActivity(Activity at) {
        Intent intent = new Intent(at, LoginActivity.class);
        at.startActivity(intent);
        SmallTools.activityIn(at);
    }


    /**
     * 判断WIFI网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断网络连接的类型
     *
     * @param context
     * @return
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }


    /**
     * 打电话
     *
     * @param context
     * @param number
     */
    public static void call(Context context, String number) {
        if (number != null && number.length() > 0) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            if (number.startsWith("tel:")) {
                intent.setData(Uri.parse(number));
            } else {
                intent.setData(Uri.parse("tel:" + number));
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            // ToastUtils.showMsg("");
        }
    }

    /**
     * @param idCard
     * @return
     * @方法说明:身份证号码判断
     * @方法名称:isIdCard
     * @返回值:boolean
     */
    public static boolean isIdCard(String idCard) {
        String format = "(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])";
        Pattern pemail = Pattern.compile(format);
        Matcher m = pemail.matcher(idCard);

        return m.matches();
    }

    /**
     * 判断一个字符串是否为邮箱
     *
     * @param testemail
     * @return
     */
    public static boolean isEmail(String testemail) {
        // 匹配邮箱
        String format = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

        Pattern pemail = Pattern.compile(format);
        Matcher m = pemail.matcher(testemail);

        return m.matches();
    }

    /**
     * 判断一个字符串是否为手机号码
     *
     * @param testTel
     * @return
     */
    public static boolean isTEL(String testTel) {
        // 匹配电话
        // String MOBILE = "^1(3[0-9]|4[0-9]|5[0-35-9]|8[0-25-9])\\d{8}$";
        String MOBILE = "^1[345678]\\d{9}$";
        Pattern pTel = Pattern.compile(MOBILE);
        Matcher m = pTel.matcher(testTel);

        return m.matches();
    }

    public static int getVersionCode(Context ct) {
        try {
            PackageManager manager = ct.getPackageManager();
            PackageInfo info = manager.getPackageInfo(ct.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }


    /**
     * 清除/data/data/com.xxx.xxx/files下的内容 * * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
     */
    public static void deleteFilesByDirectory(File directory) {
        Out.println("deleteFilesByDirectory............................");
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                Out.println("deleteFilesByDirectory.name:" + item.getName());
                item.delete();
            }
        }
    }


    public static void hideInput(Activity activity, View v) {
        if (activity != null && v != null) {
            ((InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(v.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void hideInput(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            ((InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(activity.getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 手机的串号是手机的身份证，每部手机都对应一个全球唯一的串号。用手机按*#06#，看你的手机电池下面的串号和你的盒外包装的串号是不是一样，一样的话基本可以保证手机是正品，不一样的话有可能是翻新机。
     *
     * @param context 手机号
     * @return Imei  号
     */
    public static String getImei(Context context) {
        String imei = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            imei = telephonyManager.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }

}
