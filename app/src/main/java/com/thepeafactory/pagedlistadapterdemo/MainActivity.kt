package com.thepeafactory.pagedlistadapterdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.thepeafactory.pagedlistadapterdemo.adapter.NewsPagedListAdapter
import com.thepeafactory.pagedlistadapterdemo.view_model.NewsListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter = NewsPagedListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        news_list_rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        ViewModelProviders.of(this).get(NewsListViewModel::class.java)
            .getNewsList()
            .observe(this, Observer { items ->
                adapter.submitList(items)
            })
        news_list_rv.adapter = adapter

    }

}
