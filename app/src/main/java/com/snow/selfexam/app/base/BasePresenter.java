/*
  * Copyright 2017 JessYan
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *      http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package com.snow.selfexam.app.base;


/**
 * Created by jess on 16/4/28.
 */
public class BasePresenter implements IPresenter {
    protected final String TAG = this.getClass().getSimpleName();

    public BasePresenter() {
        onStart();
    }


    @Override
    public void onStart() {
       // if (useEventBus())//如果要使用eventbus请将此方法返回true
       //     EventBus.getDefault().register(this);//注册eventbus
    }

    @Override
    public void onDestroy() {
     /*   if (useEventBus())//如果要使用eventbus请将此方法返回true
        //    EventBus.getDefault().unregister(this);//解除注册eventbus*/



    }




}
