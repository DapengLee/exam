package com.snow.selfexam.mvp.presenter;

import android.os.SystemClock;

import com.snow.selfexam.app.base.BasePresenter;
import com.snow.selfexam.mvp.modle.HomeItemInfo;
import com.snow.selfexam.mvp.view.fragments.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/9 0009.
 */

public class HomePresenter extends BasePresenter {
    private HomeFragment homeFragment;

    public HomePresenter(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    public List<HomeItemInfo> getAppList() {
        //get请求服务
        List<HomeItemInfo> list=new ArrayList<>();
        for(int i=0;i<1;i++)
        {
            HomeItemInfo info=new HomeItemInfo();
            info.appName="2018年4月自学考试注意事项： \n" +
                    "1、本次考试时间：2018年4月14-15日，每天考2门课程，具体考试时间分别是上午：9:00-11:30；下午2:30-5:00；\n" +
                    "2、考试必带证：身份证原件、准考证 。\n" +
                    "3、考试必备工具：0.5毫米黑色签字笔、2B铅笔、橡皮擦 ；\n" +
                    "4、确认考点：考前一天（13号）各位考生最好抽个时间到考点去看看，不要到14号上午考试的时候找不到考场，一定要保证第一门考试顺利到达考点教室；\n" +
                    " 5、大家要看清楚准考证上的考点：以准考证为准，千万不要找错了考点；\n" +
                    " 6、天气：请百度长沙14-15日天气，合理安排衣物；\n" +
                    " 7、考试必须全部写满，有卷面分，不得空任何一题；\n" +
                    " 8、考试一定要准确粘贴条形码，完整填写笔记卡（诚信应考承诺），未粘贴，未抄写无成绩，\n" +
                    " 9.考试期间一定注意饮食和交通安全，切忌胡吃海喝，保管好财物。\n" +
                    "\n" +
                    "本次考试人数多，考点多，临近请各位考试抓紧时间复习,提前安排住宿交通，祝大家考试考出理想成绩。"+ SystemClock.uptimeMillis()+i;
           // info.url="http://www.xuebao.com/image/1.jpg"+i;
            list.add(info);
        }
        return list;
    }
}
