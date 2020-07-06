package com.picpay.desafio.android.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.model.ErrorData
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.util.observeOnError
import com.picpay.desafio.android.util.observeOnLoading
import com.picpay.desafio.android.util.observeOnSuccess
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val adapter: UserListAdapter = UserListAdapter()
    private val viewModel: UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("UEHARA", " INICIANDO ACTIVITY")
        initObservers()
        initViews()
        viewModel.fetchUsers()
    }

    private fun initViews() {
        contacts_recyclerView.adapter = adapter
        contacts_recyclerView.layoutManager = LinearLayoutManager(this)
        error_retry_button.setOnClickListener {
            viewModel.fetchUsers()
        }
    }

    private fun initObservers() {
        viewModel.contacts
            .observeOnSuccess(this, ::onSuccess)
            .observeOnLoading(this, ::onLoading)
            .observeOnError(this, ::onError)
    }

    private fun onLoading() {
        contacts_recyclerView.visibility = View.GONE
        error_view.visibility = View.GONE
        user_list_progress_bar.visibility = View.VISIBLE
    }

    private fun onSuccess(users: List<User>) {
        adapter.updateList(users)
        user_list_progress_bar.visibility = View.GONE
        error_view.visibility = View.GONE
        contacts_recyclerView.visibility = View.VISIBLE
    }

    private fun onError(errorData: ErrorData) {
        user_list_progress_bar.visibility = View.GONE
        contacts_recyclerView.visibility = View.GONE
        error_view.visibility = View.VISIBLE
    }
}
