package com.example.snishimura.qiitaviewer

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.snishimura.qiitaviewer.data.Article

class ArticleListViewModel : ViewModel() {
    private val _articles: MutableLiveData<List<Article>> = MutableLiveData()

    /*
    * 直接的なデータ操作を禁止するためgetterでシャドウイングする
    * 値の更新はLiveDataのsetValue or postValueを使う
    * */
    val articles: MutableLiveData<List<Article>>
        get() = _articles
}