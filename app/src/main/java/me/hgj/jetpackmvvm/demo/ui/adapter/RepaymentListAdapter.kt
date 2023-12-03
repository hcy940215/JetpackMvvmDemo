package me.hgj.jetpackmvvm.demo.ui.adapter

import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import me.hgj.jetpackmvvm.demo.R
import me.hgj.jetpackmvvm.demo.app.ext.setAdapterAnimation
import me.hgj.jetpackmvvm.demo.app.util.SettingUtil
import me.hgj.jetpackmvvm.demo.app.weight.customview.CollectView
import me.hgj.jetpackmvvm.demo.data.model.bean.LoanRecordResponse
import me.hgj.jetpackmvvm.demo.data.model.bean.RepaymentItemResponse


class RepaymentListAdapter(data: ArrayList<RepaymentItemResponse>) :
    BaseDelegateMultiAdapter<RepaymentItemResponse, BaseViewHolder>(data) {
    private val Project = 2
    private var collectAction: (item: RepaymentItemResponse, v: CollectView, position: Int) -> Unit =
        { _: RepaymentItemResponse, _: CollectView, _: Int -> }

    init {

        setAdapterAnimation(SettingUtil.getListMode())

        // 第一步，设置代理
        setMultiTypeDelegate(object : BaseMultiTypeDelegate<RepaymentItemResponse>() {
            override fun getItemType(data: List<RepaymentItemResponse>, position: Int): Int {
                return Project
            }
        })
        // 第二步，绑定 item 类型
        getMultiTypeDelegate()?.let {
            it.addItemType(Project, R.layout.item_repayment)
        }
    }

    override fun convert(holder: BaseViewHolder, item: RepaymentItemResponse) {
        when (holder.itemViewType) {
            Project -> {
                //项目布局的赋值
                item.run {
                    holder.setText(R.id.tv_project_name,"${item.bRepaymentInfor}")
                    holder.setText(R.id.tv_project_time, item.repaymentDate)
                    holder.setText(R.id.tv_repayMethod, item.repayMethod)
                    holder.setText(R.id.tv_repayment_date,"${item.bLoanInfor}")
                    holder.setText(R.id.tv_project_status, "立即还款")
                }
            }
        }

    }

    fun setCollectClick(inputCollectAction: (item: RepaymentItemResponse, v: CollectView, position: Int) -> Unit) {
        this.collectAction = inputCollectAction
    }

}


