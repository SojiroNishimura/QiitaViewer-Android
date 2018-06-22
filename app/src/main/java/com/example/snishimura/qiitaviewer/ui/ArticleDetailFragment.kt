package com.example.snishimura.qiitaviewer.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.snishimura.qiitaviewer.R
import kotlinx.android.synthetic.main.fragment_article_detail.*

class ArticleDetailFragment : Fragment() {
    val url: String by lazy {
        ArticleDetailFragmentArgs.fromBundle(arguments).url
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_article_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView?.let {
            it.webViewClient = WebViewClient()
            it.loadUrl(url)
        }
    }
}
