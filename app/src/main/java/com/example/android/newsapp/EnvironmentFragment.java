package com.example.android.newsapp;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnvironmentFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<List<Article>> {

    /**
     * URL for article data from the Guardian api dataset
     */
    private static final String GUARDIAN_URL =
            "https://content.guardianapis.com/search?section=environment&format=json&api-key=139ab88d-97c6-46d0-bb52-fdee37c3f794&show-fields=all&orderby=newest";

    /**
     * Constant value for the article loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int ARTICLE_LOADER_ID = 1;

    /**
     * Adapter for the list of articles
     */
    private ArticleAdapter mAdapter;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    private NetworkInfo mNetworkInfo = null;

    public EnvironmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        ListView articleListView = (ListView) rootView.findViewById(R.id.list);

        mEmptyStateTextView = (TextView) rootView.findViewById(R.id.empty_view);
        articleListView.setEmptyView(mEmptyStateTextView);

        mAdapter = new ArticleAdapter(getActivity(), new ArrayList<Article>());

        articleListView.setAdapter(mAdapter);

        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current earthquake that was clicked on
                Article currentArticle = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri articleUri = Uri.parse(currentArticle.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, articleUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        
        checkNetworkConnectivity();

        // If there is a network connection, fetch data
        if (mNetworkInfo != null && mNetworkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader, Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface)
            loaderManager.initLoader(ARTICLE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = rootView.findViewById(R.id.progress_bar);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        return rootView;
    }

    /**
     *
     * @return the mNetworkInfo either null or with a network
     */
    private NetworkInfo checkNetworkConnectivity() {
        // Get a reference to the Connectivity Manager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        mNetworkInfo = connMgr.getActiveNetworkInfo();

        return mNetworkInfo;
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        return new ArticleLoader(getActivity(), GUARDIAN_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = getActivity().findViewById(R.id.progress_bar);
        loadingIndicator.setVisibility(View.GONE);

        checkNetworkConnectivity();

        if (mNetworkInfo == null) {
            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        } else {
            // Set empty state text to display "No Articles found."
            mEmptyStateTextView.setText(R.string.no_articles);
        }

        // Clear the adapter of previous Article data
        mAdapter.clear();

        // If there is a valid list of {@link Article}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        mAdapter.clear();
    }
}
