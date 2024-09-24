package com.techtest.daffa_github_user.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.techtest.daffa_github_user.data.Resource
import com.techtest.daffa_github_user.databinding.FragmentSearchBinding
import com.techtest.daffa_github_user.domain.model.User
import com.techtest.daffa_github_user.ui.adapter.UserAdapter
import com.techtest.daffa_github_user.util.gone
import com.techtest.daffa_github_user.util.visible
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding
    private val searchViewModel: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUser()
        actionSearch()
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


    private fun observeUser() {
        searchViewModel.listUser.observe(viewLifecycleOwner) { user ->
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

    private fun actionSearch() {
        binding?.svUser?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchViewModel.searchUser(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
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

    private fun onItemUserClicked(username: String) {
        val action = SearchFragmentDirections.searchToDetail(username)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}