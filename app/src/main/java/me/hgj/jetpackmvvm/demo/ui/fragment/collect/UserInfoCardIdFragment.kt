package me.hgj.jetpackmvvm.demo.ui.fragment.collect

import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.esafirm.imagepicker.features.ImagePickerSavePath
import com.esafirm.imagepicker.features.cameraonly.CameraOnlyConfig
import com.esafirm.imagepicker.features.registerImagePicker
import com.hjq.permissions.XXPermissions
import kotlinx.android.synthetic.main.include_toolbar.*
import me.hgj.jetpackmvvm.demo.app.appViewModel
import me.hgj.jetpackmvvm.demo.app.base.BaseFragment
import me.hgj.jetpackmvvm.demo.app.ext.initClose
import me.hgj.jetpackmvvm.demo.app.ext.showMessage
import me.hgj.jetpackmvvm.demo.databinding.FragmentUserinfoCardIdBinding
import me.hgj.jetpackmvvm.demo.viewmodel.request.RequestUploadViewModel
import me.hgj.jetpackmvvm.demo.viewmodel.state.NewLoanUserInfoViewModel
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.state.ResultState

class UserInfoCardIdFragment :
    BaseFragment<NewLoanUserInfoViewModel, FragmentUserinfoCardIdBinding>() {

    private val requestViewModel: RequestUploadViewModel by viewModels()

    private val frontLauncher = registerImagePicker {
        if (it.isNotEmpty()) {
            mDatabind.ivCardIdFront.setImageURI(it.first().uri)
            requestViewModel.upload(it.first().path, true)
        }

    }
    private val backLauncher = registerImagePicker {
        if (it.isNotEmpty()) {
            mDatabind.ivCardIdBack.setImageURI(it.first().uri)
            requestViewModel.upload(it.first().path)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.click = ProxyClick()
        addLoadingObserve(requestViewModel)
        toolbar.run {
            initClose("上传证件") {
                nav().navigateUp()
            }
        }

        mDatabind.ivCardIdFront.setOnClickListener {
            permission {
                frontLauncher.launch(
                    CameraOnlyConfig(
                        savePath = ImagePickerSavePath(
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath,
                            false
                        )
                    )
                )
            }

        }

        mDatabind.ivCardIdBack.setOnClickListener {
            permission {
                backLauncher.launch(
                    CameraOnlyConfig(
                        savePath = ImagePickerSavePath(
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath,
                            false
                        )
                    )
                )
            }
        }
    }

    private fun permission(block: (() -> Unit)) {
        XXPermissions.with(requireActivity())
            .permission(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
            )
            .request { _, all ->
                if (all) {
                    block.invoke()
                } else {
                    showMessage("请开启相机权限")
                }
            }
    }

    override fun lazyLoadData() {
    }

    inner class ProxyClick {
        fun register() {
            when {
                mViewModel.cardId1.get().isEmpty() -> showMessage("请上传身份证正面")
                mViewModel.cardId2.get().isEmpty() -> showMessage("请上传身份证反面")
                else -> {
                    appViewModel.userCardId1LiveData.value = mViewModel.cardId1.get()
                    appViewModel.userCardId2LiveData.value = mViewModel.cardId1.get()
                    nav().navigateUp()
                }
            }
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
                    if (it.data.isFront) {
                        Toast.makeText(context, "身份证正面上传成功", Toast.LENGTH_SHORT).show()
                        mViewModel.cardId1.set(it.data.url)
                    } else {
                        Toast.makeText(context, "身份证反面上传成功", Toast.LENGTH_SHORT).show()
                        mViewModel.cardId2.set(it.data.url)
                    }
                }
            }
        }
    }

}