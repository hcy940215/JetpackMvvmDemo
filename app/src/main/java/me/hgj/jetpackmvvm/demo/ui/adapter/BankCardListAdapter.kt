package me.hgj.jetpackmvvm.demo.ui.adapter

import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import me.hgj.jetpackmvvm.demo.R
import me.hgj.jetpackmvvm.demo.app.ext.setAdapterAnimation
import me.hgj.jetpackmvvm.demo.app.util.SettingUtil
import me.hgj.jetpackmvvm.demo.app.weight.customview.CollectView
import me.hgj.jetpackmvvm.demo.data.model.bean.BankCardResponse


class BankCardListAdapter(data: ArrayList<BankCardResponse>) :
    BaseDelegateMultiAdapter<BankCardResponse, BaseViewHolder>(data) {
    private val Project = 2

    init {

        setAdapterAnimation(SettingUtil.getListMode())

        // 第一步，设置代理
        setMultiTypeDelegate(object : BaseMultiTypeDelegate<BankCardResponse>() {
            override fun getItemType(data: List<BankCardResponse>, position: Int): Int {
                return Project
            }
        })
        // 第二步，绑定 item 类型
        getMultiTypeDelegate()?.let {
            it.addItemType(Project, R.layout.item_bank_card_list)
        }
    }

    override fun convert(holder: BaseViewHolder, item: BankCardResponse) {
        when (holder.itemViewType) {
            Project -> {
                //项目布局的赋值
                item.run {
                    holder.setText(R.id.tv_project_name,"${item.bank}")

                    val takeLast = item.cardNo?.takeLast(4)?:""
                    holder.setText(R.id.tv_project_time,"**** **** **** $takeLast")
                }
            }
        }

    }


}


