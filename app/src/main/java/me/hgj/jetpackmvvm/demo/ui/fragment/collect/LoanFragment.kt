package me.hgj.jetpackmvvm.demo.ui.fragment.collect

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.hjq.base.BaseDialog
import me.hgj.jetpackmvvm.demo.R
import me.hgj.jetpackmvvm.demo.app.base.BaseFragment
import me.hgj.jetpackmvvm.demo.app.ext.showMessage
import me.hgj.jetpackmvvm.demo.app.weight.MenuDialog
import me.hgj.jetpackmvvm.demo.app.weight.MonthDialog
import me.hgj.jetpackmvvm.demo.app.weight.PayReturnDialog
import me.hgj.jetpackmvvm.demo.app.weight.PayUseDialog
import me.hgj.jetpackmvvm.demo.data.model.bean.BankCardResponse
import me.hgj.jetpackmvvm.demo.data.model.bean.LoanPayData
import me.hgj.jetpackmvvm.demo.databinding.FragmentLoanBinding
import me.hgj.jetpackmvvm.demo.viewmodel.request.BankCardViewModel
import me.hgj.jetpackmvvm.demo.viewmodel.request.RequestNewLoanViewModel
import me.hgj.jetpackmvvm.demo.viewmodel.state.LoanViewModel

class LoanFragment : BaseFragment<LoanViewModel, FragmentLoanBinding>() {
    // 底部选择框
    private var mPayReturnDialog: PayReturnDialog.Builder? = null
    private val requestViewModel: RequestNewLoanViewModel by viewModels()
    private val requestBankViewModel: BankCardViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()
    }

    override fun lazyLoadData() {
        requestBankViewModel.bankCardListDataState.observe(viewLifecycleOwner) {
            val data = it.listData
            // 底部选择框
            MenuDialog.Builder(this@LoanFragment.requireContext())
                // 设置 null 表示不显示取消按钮
                //.setCancel(getString(R.string.common_cancel))
                // 设置点击按钮后不关闭对话框
                //.setAutoDismiss(false)
                .setList(data)
                .setListener(object : MenuDialog.OnListener<BankCardResponse> {
                    override fun onSelected(
                        dialog: BaseDialog?,
                        position: Int,
                        data: BankCardResponse
                    ) {
                        val takeLast = data.cardNo?.takeLast(4) ?: ""
                        mViewModel.bank.set("${data.bank}($takeLast)")
                        mViewModel.bankId.set("${data.cardId}")
                    }

                    override fun onCancel(dialog: BaseDialog?) {
                    }
                })
                .show()
        }
        requestViewModel.loanResultDataState.observe(viewLifecycleOwner) {
            when (it) {
                is me.hgj.jetpackmvvm.state.ResultState.Success -> {
                    Toast.makeText(requireContext(), "借款成功!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(
                        R.id.main_navation, null,
                        NavOptions.Builder()
                            .setPopUpTo(R.id.main_navation, true).build()
                    )
                }
                is me.hgj.jetpackmvvm.state.ResultState.Error -> {
                }
                is me.hgj.jetpackmvvm.state.ResultState.Loading -> {
                    showLoading("借款中...")
                }
            }
        }
        requestViewModel.loanListDataState.observe(viewLifecycleOwner) {
            val data = it.listData

            val type = if (it.hasMore) {
                0
            } else {
                1
            }

            if (mPayReturnDialog == null) {
                mPayReturnDialog = PayReturnDialog.Builder(this@LoanFragment.requireContext())
                mPayReturnDialog!!
                    .setData(
                        data,
                        mViewModel.loanAmount.get(),
                        mViewModel.loanTerm.get(),
                        type
                    )
                    .setListener(object : PayReturnDialog.OnListener {
                        override fun onSelected(
                            dialog: BaseDialog?,
                            position: Int,
                            bean: LoanPayData
                        ) {

                        }

                        override fun onSelectedType(
                            dialog: BaseDialog?,
                            type: Int,
                            loanPayData: LoanPayData
                        ) {
                            requestViewModel.repaymentPlan(
                                mViewModel.loanAmount.get(),
                                mViewModel.loanTerm.get(),
                                type.toString()
                            )

                            mViewModel.repayMethod.set(type.toString())

                        }

                    })
                    .show()
            }

            mPayReturnDialog!!.setData(
                data,
                mViewModel.loanAmount.get(),
                mViewModel.loanTerm.get(),
                type
            ).show()

            if ((data.size > 0)) {
                val loanPayData = data[0]
                mDatabind.tvRepayAmount.text =
                    "预计${loanPayData.repayDate}首次还款，应还￥${loanPayData.repayAmount}"

                mDatabind.tvRepayMethod.text = if (type == 0) {
                    "等额本息"
                } else {
                    "先息后本"
                }
            }
        }
    }

    inner class ProxyClick {
        /**清空*/
        fun clear() {
        }

        /**注册*/
        fun register() {
            when {
                mViewModel.loanAmount.get().isEmpty() -> showMessage("请填写借款金额")
                mViewModel.month.get().isEmpty() -> showMessage("请选择借款月份")
                mViewModel.repayMethod.get().isEmpty() -> showMessage("请选择还款方式")
                mViewModel.bank.get().isEmpty() -> showMessage("请选择收款银行卡")
                else -> {

                    arguments?.let {
                        val realName = it.getString("realName", "")
                        val username = it.getString("username", "")
                        val age = it.getString("age", "")
                        val cardNum = it.getString("cardNum", "")
                        val work = it.getString("work", "")
                        val payUse = it.getString("payUse", "")
                        val score = it.getString("score", "")
                        val cardId1 = it.getString("cardId1", "")

                        requestViewModel.newLoan(
                            loanAmount = mViewModel.loanAmount.get(),// = "",// 2000,
                            loanTerm = mViewModel.loanTerm.get(),// = "",// 9,
                            repayMethod = mViewModel.repayMethod.get(),// = "",// 0,
                            name = realName,// = "",// "张山",
                            age = age,// = "",// "30",
                            cardNo = cardNum,// = "",// "111111111111111",
                            phone = username,// = "",// "18888888",
                            income = work,// = "",// 200,
                            alipayScore = score,// = "",// 200,
                            loanUse = payUse,// = "",// "个人消费",
                            faceImage = cardId1,// = "",// "/人脸照片",
                            cardId = mViewModel.bankId.get(),// = "",// "收款账户"
                        )
                    }

                }
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

                    }
                })

            val data = mutableListOf<PayUseDialog.PayUseData>()
            data.add(PayUseDialog.PayUseData("个人日常消费", false))
            data.add(PayUseDialog.PayUseData("装修", false))
            data.add(PayUseDialog.PayUseData("教育", false))
            data.add(PayUseDialog.PayUseData("手机数码", false))
            data.add(PayUseDialog.PayUseData("电器", false))
            data.add(PayUseDialog.PayUseData("医疗", false))
            data.add(PayUseDialog.PayUseData("租房", false))
            data.add(PayUseDialog.PayUseData("家具家居", false))
            data.add(PayUseDialog.PayUseData("旅游", false))
            data.add(PayUseDialog.PayUseData("婚庆", false))
            payUseDialog.setData(data).show()
        }

        fun showMonthDialog() {
            val data = ArrayList<String>()
            data.add("3")
            data.add("6")
            data.add("12")
            data.add("18")
            data.add("24")

            // 底部选择框
            MonthDialog.Builder(this@LoanFragment.requireContext())
                // 设置 null 表示不显示取消按钮
                //.setCancel(getString(R.string.common_cancel))
                // 设置点击按钮后不关闭对话框
                //.setAutoDismiss(false)
                .setList(data)
                .setListener(object : MonthDialog.OnListener<String> {
                    override fun onSelected(dialog: BaseDialog?, position: Int, data: String) {
                        mViewModel.month.set("${data}个月")
                        mViewModel.loanTerm.set(data)
                    }

                    override fun onCancel(dialog: BaseDialog?) {
                    }
                })
                .show()
        }

        fun showBankDialog() {
            requestBankViewModel.getBankCardList(true)
        }

        fun showPayMethodDialog() {
            when {
                mViewModel.loanAmount.get().isEmpty() -> showMessage("请填写借款金额")
                mViewModel.month.get().isEmpty() -> showMessage("请选择借款月份")
                else -> requestViewModel.repaymentPlan(
                    mViewModel.loanAmount.get(),
                    mViewModel.loanTerm.get(),
                    mViewModel.repayMethod.get().ifEmpty { "0" }
                )
            }

        }
    }

    override fun createObserver() {
    }

}