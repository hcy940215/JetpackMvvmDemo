package me.hgj.jetpackmvvm.demo.ui.fragment.collect

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.hjq.base.BaseDialog
import kotlinx.android.synthetic.main.include_toolbar.*
import me.hgj.jetpackmvvm.demo.app.base.BaseFragment
import me.hgj.jetpackmvvm.demo.app.ext.initClose
import me.hgj.jetpackmvvm.demo.app.ext.showMessage
import me.hgj.jetpackmvvm.demo.app.weight.PayUseDialog
import me.hgj.jetpackmvvm.demo.databinding.FragmentLoanBinding
import me.hgj.jetpackmvvm.demo.databinding.FragmentNewLoanUserinfoBinding
import me.hgj.jetpackmvvm.demo.viewmodel.request.RequestNewLoanViewModel
import me.hgj.jetpackmvvm.demo.viewmodel.state.LoanViewModel
import me.hgj.jetpackmvvm.demo.viewmodel.state.NewLoanUserInfoViewModel
import me.hgj.jetpackmvvm.ext.nav

class LoanFragment : BaseFragment<LoanViewModel, FragmentLoanBinding>() {

    private val requestViewModel: RequestNewLoanViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()
    }

    override fun lazyLoadData() {
    }

    inner class ProxyClick {
        /**清空*/
        fun clear() {
            mViewModel.username.set("")
        }

        /**注册*/
        fun register() {
            when {
                mViewModel.username.get().isEmpty() -> showMessage("请填写手机号")
                mViewModel.age.get().isEmpty() -> showMessage("请填写年龄")
                mViewModel.cardNum.get().isEmpty() -> showMessage("请填写证件号码")
                mViewModel.work.get().isEmpty() -> showMessage("请填写工作收入")
                mViewModel.payUse.get().isEmpty() -> showMessage("请选择贷款用途")
                else -> requestViewModel.newLoan()
            }
        }

        fun showDialog() {
            val payUseDialog = PayUseDialog.Builder(this@LoanFragment.requireContext())
                .setListener(object : PayUseDialog.OnListener {
                    override fun onSelected(
                        dialog: BaseDialog?,
                        position: Int,
                        bean: PayUseDialog.PayUseData
                    ) {
                        mViewModel.payUse.set(bean.getName())

                    }
                })

            val data = mutableListOf<PayUseDialog.PayUseData>()
            data.add(PayUseDialog.PayUseData("个人日常消费",false))
            data.add(PayUseDialog.PayUseData("装修",false))
            data.add(PayUseDialog.PayUseData("教育",false))
            data.add(PayUseDialog.PayUseData("手机数码",false))
            data.add(PayUseDialog.PayUseData("电器",false))
            data.add(PayUseDialog.PayUseData("医疗",false))
            data.add(PayUseDialog.PayUseData("租房",false))
            data.add(PayUseDialog.PayUseData("家具家居",false))
            data.add(PayUseDialog.PayUseData("旅游",false))
            data.add(PayUseDialog.PayUseData("婚庆",false))
            payUseDialog.setData(data).show()
        }
    }

    override fun createObserver() {
    }

}