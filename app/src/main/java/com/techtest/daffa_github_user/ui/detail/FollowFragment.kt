package com.techtest.daffa_github_user.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.techtest.daffa_github_user.data.Resource
import com.techtest.daffa_github_user.databinding.FragmentFollowBinding
import com.techtest.daffa_github_user.domain.model.User
import com.techtest.daffa_github_user.ui.adapter.UserAdapter
import com.techtest.daffa_github_user.util.gone
import com.techtest.daffa_github_user.util.visible
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class FollowFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding
    private var sectionIndex: Int? = 0
    private var username: String? = null
    private val detailUserViewModel: DetailUserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sectionIndex = it.getInt(ARG_SECTION_INDEX)
            username = it.getString(ARG_USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerPage(savedInstanceState)
    }

    private fun observerPage(savedInstanceState: Bundle?) {
        when (sectionIndex) {
            PAGE_FOLLOWERS -> {
                if (savedInstanceState == null) username?.let { detailUserViewModel.userFollowers(it) }
                username?.let { detailUserViewModel.userFollowing(it) }
                setObserverFollowers()
            }

            PAGE_FOLLOWINGS -> {
                if (savedInstanceState == null) username?.let { detailUserViewModel.userFollowing(it) }
                username?.let { detailUserViewModel.userFollowing(it) }
                setObserverFollowings()
            }
        }
    }


    private fun setObserverFollowers() {
        detailUserViewModel.followers.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                when (user) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Error -> {
                        Timber.e(user.message)
                        showLoading(false)
                    }

                    is Resource.Success -> {
                        showLoading(false)
                        user.data?.let { setRecycleview(it) }
                    }
                }
            }
        }
    }

    private fun setObserverFollowings() {
        detailUserViewModel.followings.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                when (user) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Error -> {
                        Timber.e(user.message)
                        showLoading(false)
                    }

                    is Resource.Success -> {
                        showLoading(false)
                        Timber.e("disini ${user.data}")
                        user.data?.let { setRecycleview(it) }
                    }
                }
            }
        }
    }

    private fun setRecycleview(listUser: List<User>) {
        if (listUser.isNullOrEmpty()) {
            showLoading(false)
            binding?.rvListUser?.gone()
            binding?.lottieEmpty?.visible()
        } else {
            showLoading(false)
            val followAdapter = UserAdapter()
            followAdapter.submitList(listUser)
            binding?.lottieEmpty?.gone()
            binding?.rvListUser?.visible()
            binding?.rvListUser?.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = followAdapter
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding?.rvListUser?.gone()
            binding?.pbUser?.visible()
            binding?.lottieEmpty?.gone()
        } else {
            binding?.pbUser?.gone()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_SECTION_INDEX = "arg_section_index"
        const val ARG_USERNAME = "arg_username"
        const val PAGE_FOLLOWERS = 1
        const val PAGE_FOLLOWINGS = 2

        @JvmStatic
        fun newInstance(index: Int, username: String?) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_INDEX, index)
                    putString(ARG_USERNAME, username)
                }
            }
    }
}