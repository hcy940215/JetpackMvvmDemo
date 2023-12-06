package me.hgj.jetpackmvvm.demo.app.weight

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hjq.base.BaseAdapter
import com.hjq.base.BaseDialog
import com.hjq.base.BottomSheetDialog
import me.hgj.jetpackmvvm.demo.R
import me.hgj.jetpackmvvm.demo.data.model.bean.LoanPayData


class PayDialog {

    class Builder(context: Context) : BaseDialog.Builder<Builder>(context),
        BaseAdapter.OnItemClickListener {

        private var listener: OnListener? = null
        private val ivImage: ImageView? by lazy { findViewById(R.id.iv_close) }

        private val tvReturnMoney: TextView? by lazy { findViewById(R.id.tv_repay_amount) }
        private val tvReturnBank: TextView? by lazy { findViewById(R.id.tv_repay_bank) }
        private val tvPay: TextView? by lazy { findViewById(R.id.tv_pay) }


        init {
            setContentView(R.layout.return_pay_dialog)

            ivImage?.setOnClickListener {
                dismiss()
            }

            tvReturnBank?.setOnClickListener {
                listener?.onSelectedBank(getDialog())
            }

            tvPay?.setOnClickListener {
                listener?.onSelected(getDialog())
            }
        }

        fun setData(
            money: String,
        ): Builder = apply {
            tvReturnMoney?.text = "￥$money"
        }

        fun setBank(
            banName: String,
        ): Builder = apply {
            tvReturnBank?.text = "$banName"
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


    }


    interface OnListener {

        fun onSelected(dialog: BaseDialog?)

        fun onSelectedBank(dialog: BaseDialog?)
    }
}