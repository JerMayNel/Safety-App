package com.example.safetyapp.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.safetyapp.R

class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>(){

    private var contactList = emptyList<Contacts>()

    class ContactsViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        return ContactsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val currentItem = contactList[position]
        val idText = holder.itemView.findViewById<TextView>(R.id.id_txt)
        val nameText = holder.itemView.findViewById<TextView>(R.id.name)
        val numberText = holder.itemView.findViewById<TextView>(R.id.phone)
        val relationText = holder.itemView.findViewById<TextView>(R.id.relation)

        idText.text = currentItem.id.toString()
        nameText.text = currentItem.name
        numberText.text = currentItem.number
        relationText.text = currentItem.relation
    }
    fun setData(contacts: List<Contacts>){
        this.contactList = contacts
        notifyDataSetChanged()
    }
}