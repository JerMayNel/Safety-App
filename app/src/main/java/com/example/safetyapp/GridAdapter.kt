package com.example.safetyapp

// Create a new Kotlin class GridAdapter.kt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class GridAdapter(
    private val context: Context,
    private val names: Array<String>,
    private val roles: Array<String>,
    private val images: IntArray
) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int = names.size

    override fun getItem(position: Int): Any = names[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: inflater.inflate(R.layout.grid_item, parent, false)

        val imageView: ImageView = view.findViewById(R.id.imageView)
        val nameView: TextView = view.findViewById(R.id.name)
        val roleView: TextView = view.findViewById(R.id.role)

        imageView.setImageResource(images[position])
        nameView.text = names[position]
        roleView.text = roles[position]

        return view
    }
}
