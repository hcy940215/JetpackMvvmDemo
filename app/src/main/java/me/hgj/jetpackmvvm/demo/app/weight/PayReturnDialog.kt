package me.hgj.jetpackmvvm.demo.app.weight

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hjq.base.BaseAdapter
import com.hjq.base.BaseDialog
import com.hjq.base.BottomSheetDialog
import me.hgj.jetpackmvvm.demo.R
import me.hgj.jetpackmvvm.demo.data.model.bean.LoanPayData
import java.math.BigDecimal
import java.text.DecimalFormat


class PayReturnDialog {

    class Builder(context: Context) : BaseDialog.Builder<Builder>(context),
        BaseAdapter.OnItemClickListener {

        private var listener: OnListener? = null
        private val recyclerView: RecyclerView? by lazy { findViewById(R.id.rv_album_list) }
        private val ivImage: ImageView? by lazy { findViewById(R.id.iv_close) }
        private val tvPayType1: TextView? by lazy { findViewById(R.id.tv_return_type_1) }
        private val tvPayType2: TextView? by lazy { findViewById(R.id.tv_return_type_2) }

        private val tvReturnMonth: TextView? by lazy { findViewById(R.id.tv_return_month) }
        private val tvReturnMoney: TextView? by lazy { findViewById(R.id.tv_pay_return) }
        private val tvLoanMoney: TextView? by lazy { findViewById(R.id.tv_loan_money) }
        private val tvInterest: TextView? by lazy { findViewById(R.id.tv_interest) }

        private val adapter: PayUseAdapter

        init {
            setContentView(R.layout.loan_pay_dialog)
            adapter = PayUseAdapter(context)
            adapter.setOnItemClickListener(this)
            recyclerView?.adapter = adapter

            ivImage?.setOnClickListener {
                dismiss()
            }

            tvPayType1?.setOnClickListener {
                tvPayType2?.isSelected = false
                tvPayType1?.isSelected = true
                if (adapter.getData().isNotEmpty()) {
                    val loanPayData = adapter.getItem(0)
                    listener?.onSelectedType(getDialog(), 0, loanPayData)
                }
            }

            tvPayType2?.setOnClickListener {
                tvPayType1?.isSelected = false
                tvPayType2?.isSelected = true
                if (adapter.getData().isNotEmpty()) {
                    val loanPayData = adapter.getItem(0)
                    listener?.onSelectedType(getDialog(), 1, loanPayData)
                }

            }
        }

        fun setData(
            data: MutableList<LoanPayData>,
            money: String,
            month: String,
            type: Int
        ): Builder = apply {
            adapter.setData(data)
            val repayAmount = data.sumOf {
                it.repayAmount ?: 0.0
            }
            val interest = data.sumOf {
                it.interest ?: 0.0
            }
            tvReturnMonth?.text = "借满${month}期，应还总额"
            //应还总额
            tvReturnMoney?.text = "￥${formatToNumber(BigDecimal(repayAmount))}"
            //借款金额
            tvLoanMoney?.text = "￥${money}"
            //总利息
            tvInterest?.text = "￥${formatToNumber(BigDecimal(interest))}"

            tvPayType1?.isSelected = type == 0
            tvPayType2?.isSelected = type != 0

        }

        fun setListener(listener: OnListener?): Builder = apply {
            this.listener = listener
        }

        override fun onItemClick(recyclerView: RecyclerView?, itemView: View?, position: Int) {
        }

        override fun createDialog(context: Context, themeId: Int): BaseDialog {
            val dialog = BottomSheetDialog(context, themeId)
            dialog.getBottomSheetBehavior().peekHeight =
                getResources().displayMetrics.heightPixels / 2
            return dialog
        }

        private fun formatToNumber(obj: BigDecimal): String? {
            val df = DecimalFormat("#.00")
            return if (obj.compareTo(BigDecimal.ZERO) == 0) {
                "0.00"
            } else if (obj > BigDecimal.ZERO && obj < BigDecimal(1)) {
                "0" + df.format(obj).toString()
            } else {
                df.format(obj).toString()
            }
        }

    }

    class PayUseAdapter constructor(context: Context) : AppAdapter<LoanPayData>(context) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder()
        }

        inner class ViewHolder : AppViewHolder(R.layout.pay_return_item) {

            private val tvNum: TextView? by lazy { findViewById(R.id.tv_num) }
            private val tvDate: TextView? by lazy { findViewById(R.id.tv_date) }
            private val tvRepayAmount: TextView? by lazy { findViewById(R.id.tv_repayAmount) }
            private val tvPrincipal: TextView? by lazy { findViewById(R.id.tv_principal_interest) }

            override fun onBindView(position: Int) {
                getItem(position).apply {

                    if (position == 0) {
                        tvNum?.text = "首期"
                    } else {
                        tvNum?.text = "第${position + 1}期"
                    }

                    tvDate?.text = this.repayDate ?: ""
                    tvRepayAmount?.text = "￥${this.repayAmount}"
                    tvPrincipal?.text = "本金${this.principal}+利息${this.interest}"
                }

            }
        }
    }


    interface OnListener {

        /**
         * 选择条目时回调
         */
        fun onSelected(dialog: BaseDialog?, position: Int, bean: LoanPayData)
        fun onSelectedType(dialog: BaseDialog?, type: Int, loanPayData: LoanPayData)
    }
}