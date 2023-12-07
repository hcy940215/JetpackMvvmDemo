package me.hgj.jetpackmvvm.demo.ui.fragment.login

import android.os.Bundle
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import kotlinx.android.synthetic.main.include_toolbar.*
import me.hgj.jetpackmvvm.demo.R
import me.hgj.jetpackmvvm.demo.app.appViewModel
import me.hgj.jetpackmvvm.demo.app.base.BaseFragment
import me.hgj.jetpackmvvm.demo.app.ext.initClose
import me.hgj.jetpackmvvm.demo.app.ext.showMessage
import me.hgj.jetpackmvvm.demo.app.util.CacheUtil
import me.hgj.jetpackmvvm.demo.data.model.bean.UserInfo
import me.hgj.jetpackmvvm.demo.databinding.FragmentRegisterNewBinding
import me.hgj.jetpackmvvm.demo.viewmodel.request.RequestLoginRegisterViewModel
import me.hgj.jetpackmvvm.demo.viewmodel.state.LoginRegisterViewModel
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction
import me.hgj.jetpackmvvm.ext.parseState

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/24
 * 描述　:
 */
class RegisterFragment : BaseFragment<LoginRegisterViewModel, FragmentRegisterNewBinding>() {

    private val requestLoginRegisterViewModel:RequestLoginRegisterViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()
        toolbar.initClose("注册") {
            nav().navigateUp()
        }
        //设置颜色跟主题颜色一致
        appViewModel.appColor.value?.let {
//            SettingUtil.setShapColor(registerSub, it)
//            toolbar.setBackgroundColor(it)
        }
    }

    override fun createObserver() {
        requestLoginRegisterViewModel.loginResult.observe(
            viewLifecycleOwner,
            Observer { resultState ->
                parseState(resultState, {
                    it.user?.token = it.token
                    CacheUtil.setUser(it.user)
                    CacheUtil.setIsLogin(true)
                    appViewModel.userInfo.value = it.user
                    findNavController().navigate(
                        R.id.main_navation, null,
                        NavOptions.Builder()
                            .setPopUpTo(R.id.main_navation, true).build()
                    )
                }, {
                    showMessage(it.errorMsg)
                })
            })

        appViewModel.userCardId1LiveData.observeInFragment(this, Observer {
            mViewModel.cardId1.set(it)
        })

        appViewModel.userCardId2LiveData.observeInFragment(this, Observer {
            mViewModel.cardId2.set(it)
        })
    }


    inner class ProxyClick {
        /**清空*/
        fun clear() {
            mViewModel.username.set("")
        }

        /**注册*/
        fun register() {
            when {
                mViewModel.realName.get().isEmpty() -> showMessage("请填写姓名")
                mViewModel.username.get().isEmpty() -> showMessage("请填写手机号")
                mViewModel.smsCode.get().isEmpty() -> showMessage("请填写验证码")
                mViewModel.username.get().isEmpty() -> showMessage("请填写账号")
                mViewModel.password.get().isEmpty() -> showMessage("请填写密码")
                mViewModel.password2.get().isEmpty() -> showMessage("请填写确认密码")
                mViewModel.cardId1.get().isEmpty() -> showMessage("请上传身份证正面")
                mViewModel.cardId2.get().isEmpty() -> showMessage("请上传身份证反面")
                mViewModel.password.get().length < 6 -> showMessage("密码最少6位")
                mViewModel.password.get() != mViewModel.password2.get() -> showMessage("密码不一致")
                else -> requestLoginRegisterViewModel.registerAndLogin(
                    realName = mViewModel.realName.get(),
                    username = mViewModel.username.get(),
                    smsCode = mViewModel.smsCode.get(),
                    password = mViewModel.password.get(),
                    mViewModel.cardId1.get(),
                    mViewModel.cardId2.get()
                )
            }
        }

        fun getCode() {

            if (mViewModel.username.get().length != 11) {
                showMessage(getString(R.string.common_phone_input_error))
            }

            if (true) {
                showMessage(getString(R.string.common_code_send_hint))
                mDatabind.cvRegisterCountdown.start()
            }

            when {
                mViewModel.username.get().isEmpty() -> showMessage("请填写手机号")
                mViewModel.username.get().length != 11 -> showMessage("请填写正确的手机号")
                else -> requestLoginRegisterViewModel.getSmsCode(username = mViewModel.username.get())
            }
        }

        fun uploadCard() {
            nav().navigateAction(R.id.action_registerFragment_to_userCardFragment)
        }

        var onCheckedChangeListener1 = CompoundButton.OnCheckedChangeListener { _, isChecked ->
            mViewModel.isShowPwd.set(isChecked)
        }
        var onCheckedChangeListener2 = CompoundButton.OnCheckedChangeListener { _, isChecked ->
            mViewModel.isShowPwd2.set(isChecked)
        }
    }
}