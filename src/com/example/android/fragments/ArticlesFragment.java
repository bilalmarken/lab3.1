package com.example.android.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@SuppressLint("NewApi")
public class ArticlesFragment extends ListFragment {
    final static String ARG_POSITION = "position";
    int mCurrentPosition = 0;
    int layout;
    AdapterArticles adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We need to use a different list item layout for devices older than Honeycomb
        layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        // Create an array adapter for the list view, using the Ipsum headlines array
        adapter = new AdapterArticles(getActivity(),Data.Articles.get(Data.Headlines[mCurrentPosition]) );
        setListAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateArticleView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateArticleView(mCurrentPosition);
        }
    }

    public void updateArticleView(int position) {
    	mCurrentPosition = position;
    	adapter = new AdapterArticles(getActivity(),Data.Articles.get(Data.Headlines[mCurrentPosition]) );
        setListAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent inti = new Intent(getActivity(),Article.class);
        inti.putExtra("text", Data.Articles.get(Data.Headlines[mCurrentPosition]).get(position).text);
        inti.putExtra("img", Data.Articles.get(Data.Headlines[mCurrentPosition]).get(position).img);
        inti.putExtra("title", Data.Articles.get(Data.Headlines[mCurrentPosition]).get(position).title);
        startActivity(inti);
    }
}