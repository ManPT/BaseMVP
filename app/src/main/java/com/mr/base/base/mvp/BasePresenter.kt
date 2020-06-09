package com.lib.base.mvp

import java.lang.ref.Reference
import java.lang.ref.WeakReference

open abstract class BasePresenter<IView :BaseView> {
  var  mViewRef : Reference<IView>? = null

    /**
     * presenter关联的具体view
     *
     * @param view
     */
    open fun attchView(view: IView) {
        mViewRef = WeakReference<IView>(view)
    }

    /**
     * 解除当前presenter依赖view关系
     */
    open fun detachView() {
        if (mViewRef != null) {
            mViewRef!!.clear()
            mViewRef = null
        }
    }

    /**
     * 获取关联到当前presenter的view
     *
     * @return
     */
    protected val view: IView?
        protected get() = if (mViewRef == null) {
            null
        } else mViewRef!!.get()



}