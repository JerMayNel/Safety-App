package com.example.safetyapp.database.add

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.safetyapp.data.Contacts
import com.example.safetyapp.data.ContactsViewModel
import com.example.safetyapp.databinding.AddingContactsBinding

class AddContacts : AppCompatActivity(){
    private lateinit var binding: AddingContactsBinding
    private lateinit var mContactsViewModel: ContactsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddingContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mContactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)

        binding.addingContactsSubmit.setOnClickListener{
            insertDataToDatabase()
        }
    }

    private fun insertDataToDatabase() {
        val name = binding.addingContactsName.text.toString()
        val number = binding.addingContactsPhone.text.toString()
        val relation = binding.addingContactsRelation.text.toString()

        if(inputCheck(name, number, relation)){
            val contact = Contacts(0, name, number, relation)
            mContactsViewModel.addContact(contact)
            Toast.makeText(this, "Successfully added!", Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(name: String, number: String, relation: String): Boolean {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(number) && TextUtils.isEmpty(relation))
    }
}