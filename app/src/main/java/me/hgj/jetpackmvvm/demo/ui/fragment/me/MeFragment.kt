package me.hgj.jetpackmvvm.demo.ui.fragment.me

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import me.hgj.jetpackmvvm.demo.R
import me.hgj.jetpackmvvm.demo.app.appViewModel
import me.hgj.jetpackmvvm.demo.app.base.BaseFragment
import me.hgj.jetpackmvvm.demo.app.ext.jumpByLogin
import me.hgj.jetpackmvvm.demo.app.ext.showMessage
import me.hgj.jetpackmvvm.demo.app.network.NetworkApi
import me.hgj.jetpackmvvm.demo.app.util.CacheUtil
import me.hgj.jetpackmvvm.demo.databinding.FragmentMeBinding
import me.hgj.jetpackmvvm.demo.viewmodel.state.MeViewModel
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction
import me.hgj.jetpackmvvm.ext.util.notNull

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/23
 * 描述　: 我的
 */

class MeFragment : BaseFragment<MeViewModel, FragmentMeBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()
//        appViewModel.appColor.value?.let { setUiTheme(it, me_linear, me_integral) }
        appViewModel.userInfo.value?.let { mViewModel.name.set(if (it.userName.isEmpty()) it.userName else it.userName) }
    }

    override fun lazyLoadData() {
        appViewModel.userInfo.value?.run {

        }
    }

    override fun onResume() {
        super.onResume()
        mDatabind.registerSub.isVisible = CacheUtil.isLogin()
    }

    override fun createObserver() {


        appViewModel.run {
            appColor.observeInFragment(this@MeFragment, Observer {
            })
            userInfo.observeInFragment(this@MeFragment, Observer {
                it.notNull({
                    mViewModel.name.set(if (it.userName.isEmpty()) it.userName else it.userName)
                }, {
                    mViewModel.name.set("请先登录~")
                    mViewModel.info.set("id：--　排名：--")
                    mViewModel.integral.set(0)
                })
            })
        }
    }

    inner class ProxyClick {

        /** 登录 */
        fun login() {
            nav().jumpByLogin {}
        }

        /** 登录 */
        fun logout() {
            showMessage(
                "确定退出登录吗",
                positiveButtonText = "退出",
                negativeButtonText = "取消",
                positiveAction = {
                    //清空cookie
                    NetworkApi.INSTANCE.cookieJar.clear()
                    CacheUtil.setUser(null)
                    appViewModel.userInfo.value = null
                    nav().jumpByLogin {}
                })
        }

        /** 收藏 */
        fun collect() {
            nav().jumpByLogin {
                it.navigateAction(R.id.action_mainfragment_to_bankcardFragment)
            }
        }

        /** 积分 */
        fun integral() {
            nav().jumpByLogin {
                it.navigateAction(R.id.action_mainfragment_to_integralFragment,
                    Bundle().apply {
                    }
                )
            }
        }

    }
}