package com.example.snishimura.qiitaviewer.data.repository

import android.arch.lifecycle.MutableLiveData
import com.example.snishimura.qiitaviewer.data.Article
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.result.Result
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.IOException

class ArticleRepository {
    private val baseUri = "http://qiita.com/api/v2"

    val articles: MutableLiveData<List<Article>> = MutableLiveData()

    private val articlesAdapter: JsonAdapter<List<Article>> by lazy {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val type = Types.newParameterizedType(List::class.java, Article::class.java)
        moshi.adapter<List<Article>>(type)
    }

    init {
        FuelManager.instance.basePath = baseUri
    }

    fun requestArticles(query: String) {
        val param = if (query.isNotEmpty()) listOf("query" to query) else emptyList()
        Fuel.get("/items", param).response { request, response, result ->
            println("Requested URL ${request.url}")
            when (result) {
                is Result.Success -> articles.postValue(parseArticles(String(response.data)))
                is Result.Failure -> println(result.getException())
            }
        }
    }

    private fun parseArticles(jsonStr: String): List<Article> {
        return try {
            articlesAdapter.fromJson(jsonStr) ?: emptyList()
        } catch (e: IOException) {
            e.printStackTrace()
            emptyList()
        }
    }
}