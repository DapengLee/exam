package com.snow.selfexam.mvp.view.weight;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

import com.snow.selfexam.R;

/**
 * Created by Administrator on 2017/11/20.
 */

public class DialogFactory {


    // 进度框
    private static ProgressDialog progressDialog = null;

    /**
     * 系统提示框
     */
    private DialogFactory() {
    }

    /**
     * 显示进度提示框
     *
     * @param context
     * @param title
     * @param message
     */
    public static void showProgressDialog(Context context, CharSequence title,
                                          CharSequence message) {
    // progressDialog.setView(View.inflate(context,R.layout.loading_progress_dialog,null));
        if (progressDialog != null) {
            dismissProgressDialog();
        }
        progressDialog = ProgressDialog.show(context, title, message);
    }

    /**
     * 隐藏进度提示框
     */
    public static void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
