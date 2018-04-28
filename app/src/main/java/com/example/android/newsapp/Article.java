package com.example.android.newsapp;

/**
 * Created by Mrome on 1/9/2018.
 */

public class Article {
    private String mThumbnail;
    private String mHeadline;
    private String mPublishDate;
    private String mAuthor;
    private String mSectionId;
    private String mUrl;

    /**
     *
     * @param thumbnail             image of article
     * @param headline              title of article
     * @param publishDate           date that article was published
     * @param author                author of article
     * @param sectionId             a tag representing the genre of article (IE. "FILM")
     * @param url                   a link to the entire article
     */
    public Article(String thumbnail, String headline, String publishDate,
                   String author, String sectionId, String url) {
        mThumbnail = thumbnail;
        mHeadline = headline;
        mPublishDate = publishDate;
        mAuthor = author;
        mSectionId = sectionId;
        mUrl = url;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public String getHeadline() {
        return mHeadline;
    }

    public String getPublishDate() {
        return mPublishDate;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getSectionId() {
        return mSectionId;
    }

    public String getUrl() {
        return mUrl;
    }
}
