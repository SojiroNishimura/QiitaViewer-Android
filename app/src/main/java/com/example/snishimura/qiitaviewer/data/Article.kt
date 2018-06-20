package com.example.snishimura.qiitaviewer.data

import com.squareup.moshi.Json

data class Article(
        @Json(name = "rendered_body") val renderdBody: String,
        val body: String,
        val coediting: Boolean,
        @Json(name = "comments_count") val commentsCount: Int,
        @Json(name = "created_at") val createdAt: String,
        val group: Any?,
        val id: String,
        @Json(name = "likes_count") val likesCount: Int,
        val private: Boolean,
        @Json(name = "reactions_count") val reactionsCount: Int,
        val tags: List<QiitaTag>,
        val title: String,
        @Json(name = "updated_at") val updatedAt: String,
        val url: String,
        val user: User,
        @Json(name = "page_views_count") val pageViewsCount: Int?)