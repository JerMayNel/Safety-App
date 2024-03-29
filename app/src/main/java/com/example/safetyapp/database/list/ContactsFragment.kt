package com.example.safetyapp.database.list

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.safetyapp.data.ContactsAdapter
import com.example.safetyapp.R
import com.example.safetyapp.data.Contacts
import com.example.safetyapp.data.ContactsViewModel
import com.example.safetyapp.database.add.AddContacts
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContactsFragment : Fragment(), ContactsAdapter.OnItemClickListener {

    private lateinit var mcontactsViewModel: ContactsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val interview = inflater.inflate(R.layout.fragment_contacts, container, false)
        val fab = interview.findViewById<FloatingActionButton>(R.id.AddContactsButton)

        val adapter = ContactsAdapter(this) // Pass listener to the adapter
        val recyclerView = interview.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.contactsRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())

        mcontactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)
        mcontactsViewModel.readAllData.observe(viewLifecycleOwner, Observer { contacts ->
            adapter.setData(contacts)
        })

        fab.setOnClickListener {
            val intent = Intent(activity, AddContacts::class.java)
            startActivity(intent)
        }
        // Inflate the layout for this fragment
        return interview
    }

    override fun onDeleteClick(contact: Contacts) {
        // Call the deleteContact function from the ViewModel to delete the contact
        mcontactsViewModel.deleteContact(contact)
    }

}
