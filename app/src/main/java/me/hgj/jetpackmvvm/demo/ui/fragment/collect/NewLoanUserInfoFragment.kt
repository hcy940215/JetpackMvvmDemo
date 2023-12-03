package me.hgj.jetpackmvvm.demo.ui.fragment.collect

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.include_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.include_toolbar.*
import me.hgj.jetpackmvvm.demo.R
import me.hgj.jetpackmvvm.demo.app.base.BaseFragment
import me.hgj.jetpackmvvm.demo.app.ext.*
import me.hgj.jetpackmvvm.demo.databinding.FragmentBankcardAddBinding
import me.hgj.jetpackmvvm.demo.databinding.FragmentNewLoanUserinfoBinding
import me.hgj.jetpackmvvm.demo.viewmodel.request.BankCardViewModel
import me.hgj.jetpackmvvm.demo.viewmodel.request.RequestNewLoanViewModel
import me.hgj.jetpackmvvm.demo.viewmodel.state.BankCardAddViewModel
import me.hgj.jetpackmvvm.demo.viewmodel.state.NewLoanUserInfoViewModel
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.state.ResultState

class NewLoanUserInfoFragment : BaseFragment<NewLoanUserInfoViewModel, FragmentNewLoanUserinfoBinding>() {

    private val requestViewModel: RequestNewLoanViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()
        toolbar.run {
            initClose("绑定银行卡") {
                nav().navigateUp()
            }
        }
    }

    override fun lazyLoadData() {
    }

    override fun createObserver() {
    }

    inner class ProxyClick {
        /**清空*/
        fun clear() {
            mViewModel.username.set("")
        }

        /**注册*/
        fun register() {
            when {
//                mViewModel.realName.get().isEmpty() -> showMessage("请填写姓名")
//                mViewModel.username.get().isEmpty() -> showMessage("请填写手机号")
//                mViewModel.smsCode.get().isEmpty() -> showMessage("请填写验证码")
//                mViewModel.cardNum.get().isEmpty() -> showMessage("请填写卡号")
//                mViewModel.bankName.get().isEmpty() -> showMessage("请填写银行名称")
                else -> requestViewModel.newLoan()
            }
        }

    }

}