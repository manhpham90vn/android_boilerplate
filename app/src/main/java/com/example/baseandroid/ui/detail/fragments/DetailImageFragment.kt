package com.example.baseandroid.ui.detail.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.baseandroid.R
import com.example.baseandroid.databinding.FragmentDetailImageBinding
import com.example.baseandroid.di.ViewModelFactory
import com.example.baseandroid.ui.base.BaseFragment
import com.example.baseandroid.ui.detail.DetailHandle
import com.example.baseandroid.ui.detail.DetailViewModel
import com.wada811.databinding.withBinding
import javax.inject.Inject

class DetailImageFragment : BaseFragment(), DetailHandle {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<DetailViewModel>
    private val viewModel: DetailViewModel by activityViewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withBinding<FragmentDetailImageBinding> { binding ->
            binding.viewModel = viewModel
            binding.handle = this

            viewModel.img?.let {
                Glide.with(this).load(it).centerCrop().into(binding.img)
            }
        }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_detail_image
    }

    override fun didTapClose() {
        requireActivity().finish()
    }
}
