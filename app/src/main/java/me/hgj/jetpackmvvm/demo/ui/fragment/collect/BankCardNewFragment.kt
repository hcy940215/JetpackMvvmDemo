package me.hgj.jetpackmvvm.demo.ui.fragment.collect

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.kingja.loadsir.core.LoadService
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.include_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.include_toolbar.*
import me.hgj.jetpackmvvm.demo.R
import me.hgj.jetpackmvvm.demo.app.appViewModel
import me.hgj.jetpackmvvm.demo.app.base.BaseFragment
import me.hgj.jetpackmvvm.demo.app.ext.*
import me.hgj.jetpackmvvm.demo.app.weight.recyclerview.SpaceItemDecoration
import me.hgj.jetpackmvvm.demo.data.model.bean.BankCardResponse
import me.hgj.jetpackmvvm.demo.databinding.FragmentIntegralBinding
import me.hgj.jetpackmvvm.demo.ui.adapter.BankCardListAdapter
import me.hgj.jetpackmvvm.demo.viewmodel.request.BankCardViewModel
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

class BankCardNewFragment : BaseFragment<BankCardViewModel, FragmentIntegralBinding>() {

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    private val loanRecordAdapter: BankCardListAdapter by lazy { BankCardListAdapter(arrayListOf()) }

    override fun initView(savedInstanceState: Bundle?)  {
        toolbar.run {
            initClose("我的银行卡") {
                nav().navigateUp()
            }
        }
        //状态页配置
        loadsir = loadServiceInit(swipeRefresh) {
            //点击重试时触发的操作
            loadsir.showLoading()
            mViewModel.getBankCardList(true)
        }
        //初始化recyclerView
        recyclerView.init(LinearLayoutManager(context), loanRecordAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(12f)))
            it.initFooter(SwipeRecyclerView.LoadMoreListener {
                mViewModel.getBankCardList(false)
            })
            //初始化FloatingActionButton
            it.initFloatBtn(floatbtn)
        }
        //初始化 SwipeRefreshLayout
        swipeRefresh.init {
            //触发刷新监听时请求数据
            mViewModel.getBankCardList(true)
        }
        loanRecordAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                val item = adapter.getItem(position) as BankCardResponse
                arguments?.let {
                    val boolean = it.getBoolean("isSelect")
                    if (boolean) {
                        appViewModel.cardIdLiveData.value = item.cardId
                        val takeLast = item.cardNo?.takeLast(4)?:""
                        appViewModel.cardNameLiveData.value = "${item.bank}($takeLast)"
                        nav().navigateUp()
                    }
                }

            }
        }

        mDatabind.addCard.setOnClickListener {
            nav().navigateAction(R.id.action_bankcardFragment_to_bankcardFragment)
        }
    }

    override fun lazyLoadData() {
        loadsir.showLoading()
        mViewModel.getBankCardList(true)
    }

    override fun createObserver() {
        mViewModel.bankCardListDataState.observe(viewLifecycleOwner, Observer {
            //设值 新写了个拓展函数，搞死了这个恶心的重复代码
            loadListData(it, loanRecordAdapter, loadsir, recyclerView,swipeRefresh)
        })
    }
}