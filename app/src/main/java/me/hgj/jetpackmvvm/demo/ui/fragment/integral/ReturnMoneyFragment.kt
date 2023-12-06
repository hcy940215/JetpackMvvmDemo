package me.hgj.jetpackmvvm.demo.ui.fragment.integral

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.blankj.utilcode.util.ToastUtils
import com.hjq.base.BaseDialog
import me.hgj.jetpackmvvm.demo.R
import me.hgj.jetpackmvvm.demo.app.appViewModel
import me.hgj.jetpackmvvm.demo.app.base.BaseFragment
import me.hgj.jetpackmvvm.demo.app.ext.showMessage
import me.hgj.jetpackmvvm.demo.app.weight.PayDialog
import me.hgj.jetpackmvvm.demo.databinding.FragmentReturnMoneyBinding
import me.hgj.jetpackmvvm.demo.viewmodel.request.RequestNewLoanViewModel
import me.hgj.jetpackmvvm.demo.viewmodel.state.PayReturnViewModel
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction
import me.hgj.jetpackmvvm.state.ResultState

/**
 * 还款界面
 */
class ReturnMoneyFragment : BaseFragment<PayReturnViewModel, FragmentReturnMoneyBinding>() {

    val requestViewModel: RequestNewLoanViewModel by viewModels()

    private val dialog by lazy {
        PayDialog.Builder(this@ReturnMoneyFragment.requireContext())
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()
        addLoadingObserve(requestViewModel)

        arguments?.let {
            mViewModel.repayId.set(it.getString("repayId", ""))
            mViewModel.loanAmount.set(it.getString("repayAmount", ""))
            mViewModel.repayMoney.set(it.getString("repayAmount", ""))
        }
    }

    override fun lazyLoadData() {
    }

    override fun createObserver() {
        appViewModel.cardIdLiveData.observeInFragment(this) {
            mViewModel.cardId.set(it)
        }
        appViewModel.cardNameLiveData.observeInFragment(this) {
            dialog.setBank(it)
            dialog.show()

        }
        requestViewModel.repayResultDataState.observe(this) {
            when (it) {
                is ResultState.Error -> {
                    showMessage(it.error.errorMsg)
                    dialog.dismiss()
                }
                is ResultState.Loading -> TODO()
                is ResultState.Success -> {
                    Toast.makeText(requireContext(), "还款成功", Toast.LENGTH_SHORT).show()
                    appViewModel.refreshLiveData.value = ""
                    dialog.dismiss()
                    nav().navigateUp()
                }
            }
        }
    }

    inner class ProxyClick {
        /**清空*/
        fun clear() {
        }

        fun pay() {
            when {
                mViewModel.cardId.get().isEmpty() -> showMessage("请选择还款银行卡")
                else -> {
                    requestViewModel.repayment(
                        mViewModel.repayId.get(),
                        mViewModel.loanTerm.get(),
                        mViewModel.cardId.get()
                    )
                }
            }
        }


        fun showPayDialog() {
            dialog.setData(mViewModel.repayMoney.get()).setListener(object : PayDialog.OnListener {
                override fun onSelected(dialog: BaseDialog?) {
                    pay()
                }

                override fun onSelectedBank(dialog: BaseDialog?) {
                    nav().navigateAction(R.id.action_returnMoneyFragment_to_bankcardFragment,Bundle().apply {
                        putBoolean("isSelect", true)
                    })
                    dialog?.dismiss()
                }

            }).show()
        }


    }

}