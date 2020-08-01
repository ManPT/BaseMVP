package com.lib.base.mvp

import com.lib.base.BaseActivity
import com.lib.tools.ClassTool

abstract class BaseMVPActivity<IView : BaseView, Mode : BaseMode<Presenter,IView>, Presenter : BasePresenter<IView>> :
    BaseActivity() {
    var baseMode: Mode? = null
    var mPresenter: Presenter? = null

    override fun requestData() {
        baseMode = ClassTool.getT<Mode>(this, 1)
        mPresenter = ClassTool.getT<Presenter>(this, 2)
        mPresenter!!.attchView(this as IView)
        baseMode!!.mPresenter = mPresenter
        baseMode!!.request()

    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter!!.detachView()
    }

}