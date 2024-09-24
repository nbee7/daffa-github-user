package com.techtest.daffa_github_user.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.techtest.daffa_github_user.R
import com.techtest.daffa_github_user.data.Resource
import com.techtest.daffa_github_user.databinding.FragmentDetailUserBinding
import com.techtest.daffa_github_user.domain.model.UserDetail
import com.techtest.daffa_github_user.ui.adapter.SectionPagerAdapter
import com.techtest.daffa_github_user.ui.adapter.SectionPagerAdapter.Companion.TAB_TITLES
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
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = detailUserFragmentArgs.username

        if (savedInstanceState == null) {
            username?.let { detailUserViewModel.getDetailUser(it) }
        }
        observeUserDetail()
        initViewPager()

    }

    private fun observeUserDetail() {
        detailUserViewModel.userDetail.observe(viewLifecycleOwner) { user ->
            if (user.data != null) {
                when (user) {
                    is Resource.Error -> {
                        showLoading(false)
                        Timber.e(user.message)
                        setViewEmpty()
                    }

                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        showLoading(false)
                        setViewUserDetail(user.data)
                    }
                }
            } else {
                setViewEmpty()
            }
        }
    }

    private fun initViewPager() {
        val sectionPagerAdapter = SectionPagerAdapter(requireActivity(), username)
        binding?.vpFollowRepo?.apply {
            adapter = sectionPagerAdapter
            offscreenPageLimit = 2
        }
        binding?.apply {
            val mediator = TabLayoutMediator(tabLayout, vpFollowRepo) { tab, pos ->
                tab.text = when (pos) {
                    0 -> getString(TAB_TITLES[0])
                    1 -> getString(TAB_TITLES[1])
                    else -> getString(TAB_TITLES[1])
                }
            }
            mediator.attach()
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
            tvCompany.text = user.company.ifEmpty { "-" }
            tvLocation.text = user.location.ifEmpty { "-" }
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