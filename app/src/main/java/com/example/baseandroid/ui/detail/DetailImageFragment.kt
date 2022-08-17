package com.example.baseandroid.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.baseandroid.R
import com.example.baseandroid.databinding.FragmentDetailImageBinding
import com.example.baseandroid.di.ViewModelFactory
import com.example.baseandroid.ui.base.BaseFragment
import com.wada811.databinding.withBinding
import javax.inject.Inject

class DetailImageFragment : BaseFragment(), DetailHandle {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<DetailViewModel>
    private val viewModel: DetailViewModel by viewModels { viewModelFactory }

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

    override fun didTapClose() {
        activity?.onBackPressed()
    }
}
