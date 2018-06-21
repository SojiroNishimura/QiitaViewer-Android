package com.example.snishimura.qiitaviewer

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.navigation.Navigation
import com.example.snishimura.qiitaviewer.data.Article
import kotlinx.android.synthetic.main.fragment_article_list.*
import kotlinx.android.synthetic.main.fragment_article_list.view.*
import kotlinx.android.synthetic.main.list_item.view.*

class ArticleListFragment : Fragment() {
    private lateinit var articleListViewModel: ArticleListViewModel

    private val articleClickListener = object : ArticleListAdapter.ViewHolder.ArticleClickListener {
        override fun onClick(article: Article) {
            view?.let {
                Navigation.findNavController(it).navigate(ArticleListFragmentDirections.toDetail().setUrl(article.url))
            }
        }
    }

    private val articleListAdapter: ArticleListAdapter = ArticleListAdapter(articleClickListener = articleClickListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        articleListViewModel = ViewModelProviders.of(this).get(ArticleListViewModel::class.java)
        articleListViewModel.articles.observe(this, Observer<List<Article>> {
            articleListAdapter.articles = it ?: emptyList()
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_article_list, container, false)
        root.articleList.apply {
            adapter = articleListAdapter
            layoutManager = LinearLayoutManager(context)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchBox.setOnEditorActionListener { textView, actionId, keyEvent ->
            /*
            * (条件式).apply { lambda } とすると条件式の評価結果をラムダの戻り値にできるので
            * Booleanを返す必要がある場合に簡潔に記述できる
            * */
            (actionId == EditorInfo.IME_ACTION_SEARCH).apply {
                articleListViewModel.requestArticles(textView.text.toString())
            }
        }
    }

    private class ArticleListAdapter(articles: List<Article> = emptyList(),
                                     private val articleClickListener: ViewHolder.ArticleClickListener) :
            RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {
        var articles: List<Article> = articles
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        class ViewHolder(view: View, private val clickListener: ArticleClickListener) : RecyclerView.ViewHolder(view) {
            /*
            * インタフェースの定義場所をFragment直下にするか悩ましいがリスナを直接使うのはViewHolderなのでここに定義しておく
            * */
            interface ArticleClickListener {
                fun onClick(article: Article)
            }

            fun bindItem(article: Article?) {
                article?.let {
                    itemView.title.text = it.title
                    itemView.userName.text = it.user.name
                    itemView.setOnClickListener {
                        clickListener.onClick(article)
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): ArticleListAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            return ViewHolder(view, articleClickListener)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindItem(articles.elementAtOrNull(position))
        }

        override fun getItemCount() = articles.size
    }
}
