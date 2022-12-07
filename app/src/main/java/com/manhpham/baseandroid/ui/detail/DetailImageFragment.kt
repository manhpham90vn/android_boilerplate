package com.manhpham.baseandroid.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.manhpham.baseandroid.R
import com.manhpham.baseandroid.databinding.FragmentDetailImageBinding
import com.manhpham.baseandroid.ui.base.BaseFragment
import com.manhpham.baseandroid.ui.base.ScreenType
import com.wada811.databinding.withBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailImageFragment : BaseFragment(), DetailHandle {

    private val viewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withBinding<FragmentDetailImageBinding> { binding ->
            binding.viewModel = viewModel
            binding.handle = this
            arguments?.getString("key")?.let {
                viewModel.img = it
            }
        }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_detail_image
    }

    override fun screenType(): ScreenType {
        return ScreenType.IMG_DETAIL
    }

    override fun didTapClose() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }
}
