package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit.adapter.MyAdapter
import com.example.retrofit.repository.Repository
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val myAdapter by lazy { MyAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerview()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        val options: HashMap<String, String> = HashMap()
        options["_sort"] = "id"
        options["_order"] = "desc"

        button.setOnClickListener{
            val myNumber = number_editText.text.toString()
            viewModel.getCustomPosts2(Integer.parseInt(myNumber), options)
            viewModel.myCustomPosts2.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    response.body()?.let { myAdapter.setData(it) }
                } else {
                    Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun setupRecyclerview() {
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}