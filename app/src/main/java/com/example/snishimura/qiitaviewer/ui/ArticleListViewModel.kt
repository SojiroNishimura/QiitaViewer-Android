package com.example.snishimura.qiitaviewer.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.snishimura.qiitaviewer.data.Article
import com.example.snishimura.qiitaviewer.data.repository.ArticleRepository

class ArticleListViewModel : ViewModel() {
    /*
    * TODO できればRepositoryはDIした方がよい
    * */
    private val articleRepository: ArticleRepository = ArticleRepository()
    private val _articles: LiveData<List<Article>> = articleRepository.articles

    /*
    * 直接的なデータ操作を禁止するためgetterでシャドウイングする
    * 値の更新はRepository側で行うためこのクラスでは変更を許可しない(=MutableLiveDataにしない)
    * */
    val articles: LiveData<List<Article>>
        get() = _articles

    fun requestArticles(query: String = "") {
        articleRepository.requestArticles(query)
    }
}