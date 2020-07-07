package com.picpay.desafio.android.view.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.model.User

class ContactListAdapter : RecyclerView.Adapter<ContactListItemViewHolder>() {

    private var users = emptyList<User>()

    fun updateList(newUsers: List<User>) {
        val result = DiffUtil.calculateDiff(
            ContactListDiffCallback(
                users,
                newUsers
            )
        )
        users = newUsers
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_user, parent, false)
        return ContactListItemViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: ContactListItemViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size
}