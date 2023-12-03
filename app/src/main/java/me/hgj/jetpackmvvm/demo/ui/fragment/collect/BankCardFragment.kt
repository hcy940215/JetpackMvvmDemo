package me.hgj.jetpackmvvm.demo.ui.fragment.collect

import android.os.Bundle
import kotlinx.android.synthetic.main.include_toolbar.*
import me.hgj.jetpackmvvm.demo.app.base.BaseFragment
import me.hgj.jetpackmvvm.demo.app.ext.initClose
import me.hgj.jetpackmvvm.demo.databinding.FragmentCollectBinding
import me.hgj.jetpackmvvm.demo.viewmodel.request.BankCardViewModel
import me.hgj.jetpackmvvm.ext.nav

class BankCardFragment:BaseFragment<BankCardViewModel,FragmentCollectBinding>() {

    override fun initView(savedInstanceState: Bundle?)  {
        toolbar.initClose("我的银行卡"){
            nav().navigateUp()
        }
    }
}