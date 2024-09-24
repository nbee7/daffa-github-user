package com.techtest.daffa_github_user.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.techtest.daffa_github_user.R
import com.techtest.daffa_github_user.data.Resource
import com.techtest.daffa_github_user.databinding.FragmentDetailUserBinding
import com.techtest.daffa_github_user.domain.model.UserDetail
import com.techtest.daffa_github_user.util.gone
import com.techtest.daffa_github_user.util.setImageUrl
import com.techtest.daffa_github_user.util.toShortNumberDisplay
import com.techtest.daffa_github_user.util.visible
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class DetailUserFragment : Fragment() {
    private var _binding: FragmentDetailUserBinding? = null
    private val binding get() = _binding
    private val detailUserFragmentArgs: DetailUserFragmentArgs by navArgs()
    private val detailUserViewModel: DetailUserViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = detailUserFragmentArgs.username
    }

    private fun observeUserDetail(name: String) {
        detailUserViewModel.getDetailGame(name).observe(viewLifecycleOwner) { user ->
            when (user) {
                is Resource.Error -> {
                    showLoading(false)
                    Timber.e(user.message)
                    setViewEmpty()
                }

                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    user.data?.let { setViewUserDetail(it) }
                }
            }

        }
    }

    private fun setViewUserDetail(user: UserDetail) {
        binding?.apply {
            activity?.let {
                ivUser.setImageUrl(
                    it,
                    user.avatarUrl,
                    pbImageUser,
                    R.drawable.ic_no_image
                )
            }
            tvUsername.text = user.name
            tvCompany.text = user.company
            tvLocation.text = user.location
            tvFollowers.text = user.followers.toShortNumberDisplay()
            tvFollowings.text = user.following.toShortNumberDisplay()
        }
    }

    private fun setViewEmpty() {
        binding?.apply {
            tvCompany.text = "-"
            tvLocation.text = "-"
        }
    }

    private fun showLoading(state: Boolean) {
        binding?.apply {
            if (state) {
                pbDetailUser.visible()
            } else {
                pbDetailUser.gone()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}