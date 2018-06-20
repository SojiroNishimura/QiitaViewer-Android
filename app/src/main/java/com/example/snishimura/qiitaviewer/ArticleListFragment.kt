package com.example.snishimura.qiitaviewer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.snishimura.qiitaviewer.api.requestArticles
import kotlinx.android.synthetic.main.fragment_article_list.*

class ArticleListFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        buttonRequestArticles.setOnClickListener {
            requestArticles()
        }

        button1.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_articleListFragment_to_articleDetailFragment)
        }
    }
}
