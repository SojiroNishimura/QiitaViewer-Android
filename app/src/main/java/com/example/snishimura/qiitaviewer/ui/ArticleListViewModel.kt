package com.example.snishimura.qiitaviewer.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.snishimura.qiitaviewer.data.Article
import com.example.snishimura.qiitaviewer.data.repository.ArticleRepository

class ArticleListViewModel : ViewModel() {
    /*
    * TODO Repositoryはインタフェース化してDIした方がよい
    * */
    private val articleRepository: ArticleRepository = ArticleRepository()
    private val _articles: LiveData<List<Article>> = articleRepository.articles

    val articles: LiveData<List<Article>>
        get() = _articles

    fun requestArticles(query: String = "") {
        articleRepository.requestArticles(query)
    }
}