package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.retrofit.repository.Repository

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    var content: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        content = findViewById(R.id.textView)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getPost()
        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                Log.d("Response", "1")
                Log.d("Response", response.body()?.myUserId.toString())
                Log.d("Response", response.body()?.id.toString())
                content?.text = response.body()?.title!!
                Log.d("Response", response.body()?.body.toString())
            } else {
                Log.d("Response", "2")
                Log.d("Response", response.errorBody().toString())
                content?.text = response.code().toString()
            }
        })
        Log.d("Response", "Does it work?")
    }
}