package com.example.android.newsapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Mrome on 1/9/2018.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {

    /**
     *
     * @param context            The current context. Used to inflate the layout file
     * @param articles           A list of Article objects to display in a list
     */
    public ArticleAdapter(Activity context, List<Article> articles) {
        super(context, 0, articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_item, parent, false);
        }

        Article currentArticle = getItem(position);

        ImageView thumbnail = (ImageView) listItemView.findViewById(R.id.thumbnail);
        Glide.with(getContext()).load(currentArticle.getThumbnail()).into(thumbnail);

        TextView headline = (TextView) listItemView.findViewById(R.id.headline);
        headline.setText(currentArticle.getHeadline());

        String dateAndTime = currentArticle.getPublishDate();
        String formattedDate = formatDate(dateAndTime);

        TextView date = (TextView) listItemView.findViewById(R.id.date);
        date.setText(formattedDate);

        TextView author = (TextView) listItemView.findViewById(R.id.author);
        author.setText(currentArticle.getAuthor());

        TextView sectionId = (TextView) listItemView.findViewById(R.id.section_id);
        sectionId.setText(currentArticle.getSectionId());

        return listItemView;
    }

    /**
     *
     * @param inputDate         is the date from the current Article object
     * @return                  a formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate (String inputDate) {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        DateFormat outputFormat = new SimpleDateFormat("LLL dd, yyyy");
        Date date = null;

        // Checks that {@link inputDate) is not empty or set to null
        if (inputDate != null && !inputDate.isEmpty()) {
            try {
                // Replace character "Z" with "+0000" to parse correctly
                // Otherwise app crashes and throws a NullPointerException
                date = inputFormat.parse(inputDate.replaceAll("Z$", "+0000"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String outputDate = outputFormat.format(date);
        return outputDate;
    }
}
