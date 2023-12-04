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


class PayUseDialog {

    class Builder(context: Context) : BaseDialog.Builder<Builder>(context),
        BaseAdapter.OnItemClickListener {

        private var listener: OnListener? = null
        private val recyclerView: RecyclerView? by lazy { findViewById(R.id.rv_album_list) }
        private val ivImage: ImageView? by lazy { findViewById(R.id.iv_close) }
        private val adapter: PayUseAdapter

        init {
            setContentView(R.layout.album_dialog)
            adapter = PayUseAdapter(context)
            adapter.setOnItemClickListener(this)
            recyclerView?.adapter = adapter

            ivImage?.setOnClickListener {
                dismiss()
            }
        }

        fun setData(data: MutableList<PayUseData>): Builder = apply {
            adapter.setData(data)
            // 滚动到选中的位置
            for (i in data.indices) {
                if (data[i].isSelect()) {
                    recyclerView?.scrollToPosition(i)
                    break
                }
            }
        }

        fun setListener(listener: OnListener?): Builder = apply {
            this.listener = listener
        }

        override fun onItemClick(recyclerView: RecyclerView?, itemView: View?, position: Int) {
            val data = adapter.getData()
            for (info in data) {
                if (info.isSelect()) {
                    info.setSelect(false)
                    break
                }
            }
            adapter.getItem(position).setSelect(true)
            adapter.notifyDataSetChanged()

            listener?.onSelected(getDialog(), position, adapter.getItem(position))
        }

        override fun createDialog(context: Context, themeId: Int): BaseDialog {
            val dialog = BottomSheetDialog(context, themeId)
            dialog.getBottomSheetBehavior().peekHeight =
                getResources().displayMetrics.heightPixels / 2
            return dialog
        }
    }

    class PayUseAdapter constructor(context: Context) : AppAdapter<PayUseData>(context) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder()
        }

        inner class ViewHolder : AppViewHolder(R.layout.album_item) {

            private val nameView: TextView? by lazy { findViewById(R.id.tv_album_name) }

            override fun onBindView(position: Int) {
                getItem(position).apply {
                    nameView?.text = getName()
                    nameView?.isSelected = isSelect()
                }
            }
        }
    }

    /**
     * 专辑信息类
     */
    class PayUseData(

        private var name: String,
        private var select: Boolean
    ) {

        fun setName(name: String) {
            this.name = name
        }

        fun setSelect(select: Boolean) {
            this.select = select
        }


        fun getName(): String {
            return name
        }

        fun isSelect(): Boolean {
            return select
        }
    }

    interface OnListener {

        /**
         * 选择条目时回调
         */
        fun onSelected(dialog: BaseDialog?, position: Int, bean: PayUseData)
    }
}