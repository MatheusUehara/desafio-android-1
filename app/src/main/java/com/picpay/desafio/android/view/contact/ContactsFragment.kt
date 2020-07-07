package com.picpay.desafio.android.view.contact

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.model.ErrorData
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.util.observeOnError
import com.picpay.desafio.android.util.observeOnLoading
import com.picpay.desafio.android.util.observeOnSuccess
import kotlinx.android.synthetic.main.fragment_contacts.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactsFragment : Fragment(R.layout.fragment_contacts) {

    private val adapter: ContactListAdapter = ContactListAdapter()
    private val viewModel: ContactViewModel by viewModel()
    private val navController: NavController by lazy { requireView().findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()
    }

    private fun initViews() {
        contacts_recyclerView.adapter = adapter
        contacts_recyclerView.layoutManager = LinearLayoutManager(requireContext())
        error_retry_button.setOnClickListener {
            viewModel.loadContacts()
        }
    }

    private fun initObservers() {
        viewModel.contacts
            .observeOnSuccess(viewLifecycleOwner, ::onSuccess)
            .observeOnLoading(viewLifecycleOwner, ::onLoading)
            .observeOnError(viewLifecycleOwner, ::onError)
    }

    private fun onLoading() {
        content_view.visibility = View.GONE
        error_view.visibility = View.GONE
        loading_view.visibility = View.VISIBLE
    }

    private fun onSuccess(users: List<User>) {
        adapter.updateList(users)
        loading_view.visibility = View.GONE
        error_view.visibility = View.GONE
        content_view.visibility = View.VISIBLE
    }

    private fun onError(errorData: ErrorData) {
        loading_view.visibility = View.GONE
        content_view.visibility = View.GONE
        error_view.visibility = View.VISIBLE
    }

}