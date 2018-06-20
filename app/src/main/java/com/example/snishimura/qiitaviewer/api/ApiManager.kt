package com.example.snishimura.qiitaviewer.api

import com.example.snishimura.qiitaviewer.data.Article
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.IOException

const val BASE_URI = "http://qiita.com/api/v2"

fun requestArticles() {
    "$BASE_URI/items".httpGet().response { request, response, result ->
        when (result) {
            is Result.Success -> {
                val articles = parseArticles(String(response.data))
                println(articles)
            }
            is Result.Failure -> {
                println(result.getException())
            }
        }
    }
}

private fun parseArticles(jsonStr: String): List<Article>? {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val type = Types.newParameterizedType(List::class.java,Article::class.java)

    val adapter: JsonAdapter<List<Article>> = moshi.adapter(type)
    return try { adapter.fromJson(jsonStr) } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
}