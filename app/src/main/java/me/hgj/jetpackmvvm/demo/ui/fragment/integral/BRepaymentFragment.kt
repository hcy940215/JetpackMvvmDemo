package me.hgj.jetpackmvvm.demo.ui.fragment.integral

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.kingja.loadsir.core.LoadService
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.include_recyclerview.*
import me.hgj.jetpackmvvm.demo.app.base.BaseFragment
import me.hgj.jetpackmvvm.demo.app.ext.*
import me.hgj.jetpackmvvm.demo.app.weight.recyclerview.SpaceItemDecoration
import me.hgj.jetpackmvvm.demo.databinding.FragmentRepaymentBinding
import me.hgj.jetpackmvvm.demo.ui.adapter.RepaymentListAdapter
import me.hgj.jetpackmvvm.demo.viewmodel.request.LoanRecordListViewModel

/**
 * 还款界面
 */
class BRepaymentFragment : BaseFragment<LoanRecordListViewModel, FragmentRepaymentBinding>() {

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    private val repaymentListAdapter: RepaymentListAdapter by lazy { RepaymentListAdapter(arrayListOf()) }

    override fun initView(savedInstanceState: Bundle?)  {
//        //状态页配置
        loadsir = loadServiceInit(recyclerView) {
            //点击重试时触发的操作
            loadsir.showLoading()
            mViewModel.selectBRepayment()
        }
        //初始化recyclerView
        recyclerView.init(LinearLayoutManager(context), repaymentListAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(12f)))
            it.initFooter(SwipeRecyclerView.LoadMoreListener {
                mViewModel.selectBRepayment()
            })
            //初始化FloatingActionButton
//            it.initFloatBtn(floatbtn)
        }
        //初始化 SwipeRefreshLayout
//        swipeRefresh.init {
//            //触发刷新监听时请求数据
//            mViewModel.getListLoanData(true)
//        }
        repaymentListAdapter.run {
            setOnItemClickListener { _, view, position ->
            }
        }
    }

    override fun lazyLoadData() {
        loadsir.showLoading()
        mViewModel.selectBRepayment()
    }

    override fun createObserver() {
        mViewModel.repaymentListDataState.observe(viewLifecycleOwner, Observer {
            //设值 新写了个拓展函数，搞死了这个恶心的重复代码
            loadListData(it, repaymentListAdapter, loadsir, recyclerView)
        })
        mViewModel.repaymentDataState.observe(viewLifecycleOwner) {
            mDatabind.tvRepaymentAmount.text = it.totalRepayAmount
            mDatabind.tvRepaymentDate.text = it.recentPlans
            mDatabind.tvRepaymentNum.text = "使用中的贷款(共${it.bLoanNum}笔)"
        }
    }
}