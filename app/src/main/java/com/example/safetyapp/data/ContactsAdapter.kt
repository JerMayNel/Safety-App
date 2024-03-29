package com.example.safetyapp.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.safetyapp.R

class ContactsAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    private var contactList = emptyList<Contacts>()

    interface OnItemClickListener {
        fun onDeleteClick(contact: Contacts)
    }

    inner class ContactsViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView) {
        val nameText: TextView = itemsView.findViewById(R.id.name)
        val numberText: TextView = itemsView.findViewById(R.id.phone)
        val relationText: TextView = itemsView.findViewById(R.id.relation)
        val deleteButton: ImageButton = itemsView.findViewById(R.id.delete_button)

        init {
            // Set click listener for the delete button
            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val contact = contactList[position]
                    listener.onDeleteClick(contact)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        return ContactsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val currentItem = contactList[position]
        holder.nameText.text = currentItem.name
        holder.numberText.text = currentItem.number
        holder.relationText.text = currentItem.relation
    }

    fun setData(contacts: List<Contacts>) {
        this.contactList = contacts
        notifyDataSetChanged()
    }
}