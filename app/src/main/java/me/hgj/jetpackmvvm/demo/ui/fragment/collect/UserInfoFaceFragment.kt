package me.hgj.jetpackmvvm.demo.ui.fragment.collect

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.viewModels
import com.esafirm.imagepicker.features.ImagePickerSavePath
import com.esafirm.imagepicker.features.cameraonly.CameraOnlyConfig
import com.esafirm.imagepicker.features.registerImagePicker
import com.hjq.permissions.XXPermissions
import me.hgj.jetpackmvvm.demo.R
import me.hgj.jetpackmvvm.demo.app.base.BaseFragment
import me.hgj.jetpackmvvm.demo.app.ext.showMessage
import me.hgj.jetpackmvvm.demo.databinding.FragmentUserinfoFaceBinding
import me.hgj.jetpackmvvm.demo.viewmodel.request.RequestUploadViewModel
import me.hgj.jetpackmvvm.demo.viewmodel.state.NewLoanUserInfoViewModel
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction
import me.hgj.jetpackmvvm.state.ResultState

class UserInfoFaceFragment :
    BaseFragment<NewLoanUserInfoViewModel, FragmentUserinfoFaceBinding>() {

    private val requestViewModel: RequestUploadViewModel by viewModels()

    private val frontLauncher = registerImagePicker {
        if (it.isNotEmpty()) {
            mViewModel.cardId1.set(it.first().path)
            requestViewModel.upload(it.first().path)
        }

    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.click = ProxyClick()
        addLoadingObserve(requestViewModel)
    }

    override fun lazyLoadData() {
    }

    inner class ProxyClick {
        fun register() {
            XXPermissions.with(requireActivity())
                .permission(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.MANAGE_EXTERNAL_STORAGE

                )
                .request { _, all ->
                    if (all) {
                        frontLauncher.launch(CameraOnlyConfig(
                            savePath = ImagePickerSavePath(
                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath,
                                false
                            )
                        ))
                    } else {
                        showMessage("请开启相机权限")
                    }
                }
        }

        fun close() {
            nav().navigateUp()
        }

    }

    override fun createObserver() {
        requestViewModel.repayResultDataState.observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Error -> {
                    showMessage(it.error.errorMsg)
                }
                is ResultState.Loading -> TODO()
                is ResultState.Success -> {
                    mViewModel.cardId1.set(it.data.url)
                    nav().navigateAction(
                        R.id.action_userInfoFaceFragment_to_loanFragment,
                        arguments?.apply {
                            putString("cardId1", mViewModel.cardId1.get())
                        })
                }
            }
        }
    }

}