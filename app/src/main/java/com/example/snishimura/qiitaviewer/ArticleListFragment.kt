package com.example.snishimura.qiitaviewer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.snishimura.qiitaviewer.api.requestArticles
import com.example.snishimura.qiitaviewer.data.Article
import kotlinx.android.synthetic.main.fragment_article_list.*
import kotlinx.android.synthetic.main.fragment_article_list.view.*
import kotlinx.android.synthetic.main.list_item.view.*

class ArticleListFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_article_list, container, false)
        root.articleList.apply {
            adapter = ArticleListAdapter()
            layoutManager = LinearLayoutManager(context)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        buttonRequestArticles.setOnClickListener {
            requestArticles()
        }

        button1.setOnClickListener {
            Navigation.findNavController(it).navigate(ArticleListFragmentDirections.toDetail().setUrl("Test Url"))
        }
    }

    private class ArticleListAdapter(articles: List<Article> = listOf<Article>()) :
            RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {
        var articles: List<Article> = articles
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        private class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            fun bindItem(article: Article?) {
                article?.let {
                    itemView.title.text = it.title
                    itemView.userName.text = it.user.name
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): ArticleListAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindItem(articles.elementAtOrNull(position))
        }

        override fun getItemCount() = articles.size
    }
}
