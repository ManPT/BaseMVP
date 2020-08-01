package com.lib.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.lib.tools.ActivityManagerTool
import com.lib.tools.DataTool
import com.lib.tools.LogTool

abstract class BaseActivity : AppCompatActivity() {

    var TAG: String? = ""
    public lateinit var mContext:Context
    /**
     * parentView的Id
     */
    abstract fun getContentViewId(): Int

    /**
     * 初始化view监听
     */
    abstract fun init()

    /**
     * 获取传值
     */
    abstract fun getIntentData(intent: Intent?)

    /**
     * 数据请求
     */
    abstract fun requestData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        ActivityManagerTool.createActivity(this)
        setContentView(getContentViewId())
        TAG = javaClass.simpleName
        LogTool.i("Life Cycle ：", TAG + " :onCreate()")
        setContentView(getContentViewId())
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        if (intent != null) {
            getIntentData(intent)
        }
        // UIConfigure();
        init()
        requestData()
    }

    override fun onResume() {
        super.onResume()
        Log.i("Life Cycle ：", TAG + " :onResume()")
    }

    override fun onStop() {
        super.onStop()
        LogTool.i("Life Cycle ：", TAG + " :onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManagerTool.destroyActivity(this)
        LogTool.i("Life Cycle ：", TAG + " :onDestroy()")
    }

}
