package me.hgj.jetpackmvvm.demo.viewmodel.state

import android.view.View
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.databind.BooleanObservableField
import me.hgj.jetpackmvvm.callback.databind.IntObservableField
import me.hgj.jetpackmvvm.callback.databind.StringObservableField
import me.hgj.jetpackmvvm.demo.R
import java.math.BigDecimal
import java.text.DecimalFormat

class PayReturnViewModel : BaseViewModel() {


    var loanAmount = StringObservableField()
    var loanTerm = StringObservableField("")
    var repayId = StringObservableField()
    var repayMoney = StringObservableField()
    var cardId = StringObservableField()

    //还款方式
    var fullAmountReturnField = IntObservableField(R.drawable.checkbox_checked_ic)
    var installmentReturnField = IntObservableField(R.drawable.compound_normal_ic)

    val fullAmountReturnClick = View.OnClickListener { _ ->
        installmentReturnField.set(R.drawable.compound_normal_ic)
        fullAmountReturnField.set(R.drawable.checkbox_checked_ic)

        loanTerm.set("")
        repayMoney.set(loanAmount.get())

        three.set(false)
        six.set(false)
        nine.set(false)
        twelve.set(false)

    }

    val installmentReturnClick = View.OnClickListener { _ ->
        fullAmountReturnField.set(R.drawable.compound_normal_ic)
        installmentReturnField.set(R.drawable.checkbox_checked_ic)

        loanTerm.set("3")

        three.set(true)
        six.set(false)
        nine.set(false)
        twelve.set(false)

        repayMoney.set(formatToNumber(3))
    }

    //选择还款月份
    var three = BooleanObservableField(false)
    var six = BooleanObservableField(false)
    var nine = BooleanObservableField(false)
    var twelve = BooleanObservableField(false)


    val threeClick = View.OnClickListener { _ ->
        loanTerm.set("3")
        three.set(true)
        six.set(false)
        nine.set(false)
        twelve.set(false)
        repayMoney.set(formatToNumber(3))

    }

    val sixClick = View.OnClickListener { _ ->
        loanTerm.set("6")
        three.set(false)
        six.set(true)
        nine.set(false)
        twelve.set(false)
        repayMoney.set(formatToNumber(6))

    }

    val nineClick = View.OnClickListener { _ ->
        loanTerm.set("9")
        three.set(false)
        six.set(false)
        nine.set(true)
        twelve.set(false)
        repayMoney.set(formatToNumber(9))
    }

    val twelveClick = View.OnClickListener { _ ->
        loanTerm.set("12")
        three.set(false)
        six.set(false)
        nine.set(false)
        twelve.set(true)
        repayMoney.set(formatToNumber(12))
    }

    private fun formatToNumber(month: Int): String {
        val obj = BigDecimal(loanAmount.get().toDouble() / month)
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