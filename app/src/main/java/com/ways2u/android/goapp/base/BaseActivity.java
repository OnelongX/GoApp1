package com.ways2u.android.goapp.base;
/**
 * Created by Onelong on 2015/10/31.
 */

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.socks.library.KLog;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.ways2u.android.goapp.BaseMessageHandler;
import com.ways2u.android.goapp.IAppDelegate;
import com.ways2u.android.goapp.ICreateCallback;
import com.ways2u.android.goapp.IFinishable;
import com.ways2u.android.goapp.BaseUiDelegate;

import java.lang.ref.WeakReference;


import com.ways2u.android.goapp.FinishableStack;
import com.ways2u.android.goapp.IMessageHandler;


public abstract class BaseActivity extends RxAppCompatActivity implements IFinishable,
        ICreateCallback {
    private static int sStartedActivityCount;
    //注入
    public BaseUiDelegate uiDelegate;
    //注入
    public IAppDelegate appDelegate;
    //注入，//用于组件直接通信
    public IMessageHandler messageHandler;

    private String TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getName();
        getUiDelegate();
        messageHandler = new BaseMessageHandler(this);
        setContentView(getLayoutId());
        initView();
        setListener();
        initData(savedInstanceState);
        FinishableStack.putFinishList(this);
    }


    @Override
    public void finish() {
        FinishableStack.removeFromFinishList(this);
        super.finish();
    }

    @Override
    final public void exit() {
        try {
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        sStartedActivityCount++;
        if (1 == sStartedActivityCount && appDelegate != null) {
            appDelegate.applicationDidEnterForeground();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sStartedActivityCount--;
        if (0 == sStartedActivityCount && appDelegate != null) {
            appDelegate.applicationDidEnterBackground();
        }
    }


    @Override
    protected void onDestroy() {

        if (messageHandler != null) {
            messageHandler.destory();
        }
        messageHandler = null;

        if (uiDelegate != null) {
            uiDelegate.destory();
        }
        uiDelegate = null;

        super.onDestroy();
    }

    public abstract void handleMessage(Message msg);

    protected BaseUiDelegate getUiDelegate() {
        if (uiDelegate == null) {
            uiDelegate = BaseUiDelegate.create(this);
        }
        return uiDelegate;
    }

    //-----------------------------------------------
    public <T extends View> T find(@IdRes int viewId) {
        return (T) this.findViewById(viewId);
    }

    /**
     * 获取输入框的文本
     *
     * @param e
     * @return
     */
    public String getEditTextString(EditText e) {
        String str;
        str = (e == null ? "" : e.getText().toString().trim());
        return str;
    }

    /**
     * 文本编辑器是否有输入内容
     *
     * @param et
     * @return true代表没有输入内容
     */
    public boolean checkEditContentIsNull(EditText et) {
        if (et == null) {
            return true;
        } else {
            if (!getEditTextString(et).equals("")) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param Message
     */
    public void e(String Message) {
        if (Message != null) {
            KLog.e(TAG, Message);
        }
    }

    /**
     * @param Message
     */
    public void i(String Message) {
        if (Message != null) {
            KLog.i(TAG, Message);
        }
    }

    /**
     * @param Message
     */
    public void d(String Message) {
        if (Message != null) {
            KLog.d(TAG, Message);
        }
    }

}