package me.hgj.jetpackmvvm.demo.ui.adapter

import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import me.hgj.jetpackmvvm.demo.R
import me.hgj.jetpackmvvm.demo.app.ext.setAdapterAnimation
import me.hgj.jetpackmvvm.demo.app.util.SettingUtil
import me.hgj.jetpackmvvm.demo.app.weight.customview.CollectView
import me.hgj.jetpackmvvm.demo.data.model.bean.LoanRecordResponse


class LoanRecordAdapter(data: ArrayList<LoanRecordResponse>) :
    BaseDelegateMultiAdapter<LoanRecordResponse, BaseViewHolder>(data) {
    private val Project = 2
    private var collectAction: (item: LoanRecordResponse, v: CollectView, position: Int) -> Unit =
        { _: LoanRecordResponse, _: CollectView, _: Int -> }

    init {

        setAdapterAnimation(SettingUtil.getListMode())

        // 第一步，设置代理
        setMultiTypeDelegate(object : BaseMultiTypeDelegate<LoanRecordResponse>() {
            override fun getItemType(data: List<LoanRecordResponse>, position: Int): Int {
                return Project
            }
        })
        // 第二步，绑定 item 类型
        getMultiTypeDelegate()?.let {
            it.addItemType(Project, R.layout.item_project)
        }
    }

    override fun convert(holder: BaseViewHolder, item: LoanRecordResponse) {
        when (holder.itemViewType) {
            Project -> {
                //项目布局的赋值
                item.run {
                    holder.setText(R.id.tv_project_name,"信用贷款 ￥ ${item.loanAmount}")
                    holder.setText(R.id.tv_project_time, item.createTime)
                    holder.setText(R.id.tv_project_status, item.loanStatus)
                }
            }
        }

    }

    fun setCollectClick(inputCollectAction: (item: LoanRecordResponse, v: CollectView, position: Int) -> Unit) {
        this.collectAction = inputCollectAction
    }

}


