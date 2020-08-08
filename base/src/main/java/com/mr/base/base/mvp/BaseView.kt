package com.lib.base.mvp

import android.widget.Toast
import com.mr.base.tools.ToastTool

abstract interface BaseView {

    fun toast(message:String){
        ToastTool.showToast(message,Toast.LENGTH_SHORT)
    }

}