package me.hgj.jetpackmvvm.demo.viewmodel.state

import android.view.View
import androidx.databinding.ObservableInt
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.databind.StringObservableField

class LoanViewModel : BaseViewModel() {

    var loanAmount = StringObservableField()
    var month = StringObservableField()
    var loanTerm = StringObservableField()
    var repayMethod = StringObservableField("")
    var bank = StringObservableField()
    var bankId = StringObservableField()

    var clearVisible = object : ObservableInt(repayMethod){
        override fun get(): Int {
            return if(repayMethod.get().isEmpty()){
                View.VISIBLE
            }else{
                View.GONE
            }
        }
    }

    var repayVisible = object : ObservableInt(repayMethod){
        override fun get(): Int {
            return if(repayMethod.get().isEmpty()){
                View.GONE
            }else{
                View.VISIBLE
            }
        }
    }

}