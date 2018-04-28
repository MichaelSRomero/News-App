package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Mrome on 1/10/2018.
 */

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    /** Tag for log messages */
    private static final String LOG_TAG = MainActivity.class.getName();

    /** Query Url */
    private String mUrl;

    /**
     *
     * @param context       of the activty
     * @param url           to load data from
     */
    public ArticleLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<Article> articles = QueryUtils.fetchArticleData(mUrl);
        return articles;
    }
}
