package me.hgj.jetpackmvvm.demo.ui.fragment.home

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import me.hgj.jetpackmvvm.demo.R
import me.hgj.jetpackmvvm.demo.app.appViewModel
import me.hgj.jetpackmvvm.demo.app.base.BaseFragment
import me.hgj.jetpackmvvm.demo.app.base.BaseFragment1
import me.hgj.jetpackmvvm.demo.app.eventViewModel
import me.hgj.jetpackmvvm.demo.app.ext.*
import me.hgj.jetpackmvvm.demo.databinding.FragmentHomeNewBinding
import me.hgj.jetpackmvvm.demo.viewmodel.state.HomeViewModel
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/27
 * 描述　:
 */
class HomeNewFragment : BaseFragment<HomeViewModel, FragmentHomeNewBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.tvHome.setOnClickListener {
            findNavController().navigate(R.id.newLoanUserInfoFragment)
        }
    }

    /**
     * 懒加载
     */
    override fun lazyLoadData() {
    }

    override fun createObserver() {
        appViewModel.run {
            //监听账户信息是否改变 有值时(登录)将相关的数据设置为已收藏，为空时(退出登录)，将已收藏的数据变为未收藏
            userInfo.observeInFragment(this@HomeNewFragment, Observer {

            })
            //监听全局的主题颜色改变
            appColor.observeInFragment(this@HomeNewFragment) {
                setUiTheme(it)
            }
            //监听全局的列表动画改编
            appAnimation.observeInFragment(this@HomeNewFragment) {
            }
            //监听全局的收藏信息 收藏的Id跟本列表的数据id匹配则需要更新
            eventViewModel.collectEvent.observeInFragment(this@HomeNewFragment) {
            }
        }
    }
}