package com.techtest.daffa_github_user.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.techtest.daffa_github_user.data.Resource
import com.techtest.daffa_github_user.databinding.FragmentHomeBinding
import com.techtest.daffa_github_user.domain.model.User
import com.techtest.daffa_github_user.ui.adapter.UserAdapter
import com.techtest.daffa_github_user.util.gone
import com.techtest.daffa_github_user.util.visible
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUser()
    }

    private fun observeUser() {
        homeViewModel.listUser.observe(viewLifecycleOwner) { user ->
            when (user) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    setRecyclerview(user.data)
                }

                is Resource.Error -> {
                    showLoading(false)
                    Timber.e(user.message)
                    binding?.ltUserNotFound?.visible()
                }
            }
        }
    }

    private fun setRecyclerview(users: List<User>?) {
        if (users.isNullOrEmpty()) {
            binding?.apply {
                rvListUser.gone()
                ltUserNotFound.visible()
            }
        } else {
            val userAdapter = UserAdapter { username ->
                onItemUserClicked(username)
            }
            userAdapter.submitList(users)
            binding?.apply {
                ltUserNotFound.gone()
                rvListUser.visible()
                rvListUser.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                rvListUser.setHasFixedSize(true)
                rvListUser.adapter = userAdapter
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding?.apply {
                rvListUser.gone()
                pbUser.visible()
                ltUserNotFound.gone()
            }
        } else {
            binding?.apply {
                pbUser.gone()
                ltUserNotFound.gone()
            }
        }
    }

    private fun onItemUserClicked(username: String) {
        val action = HomeFragmentDirections.homeToDetail(username)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}