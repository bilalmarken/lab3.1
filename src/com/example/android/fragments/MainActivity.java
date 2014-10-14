/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity 
        implements HeadlinesFragment.OnHeadlineSelectedListener {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	initArticles();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_articles);
        
        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            HeadlinesFragment firstFragment = new HeadlinesFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
        
    }

    public void onArticleSelected(int position) {
        // The user selected the headline of an article from the HeadlinesFragment

        // Capture the article fragment from the activity layout
        ArticlesFragment articleFrag = (ArticlesFragment)
                getSupportFragmentManager().findFragmentById(R.id.article_fragment);

        if (articleFrag != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            articleFrag.updateArticleView(position);

        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            ArticlesFragment newFragment = new ArticlesFragment();
            Bundle args = new Bundle();
            args.putInt(ArticlesFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }
    public void initArticles(){
    	ArrayList<ArticleContent> art = new ArrayList<ArticleContent>(); 
    	art.add(new ArticleContent("Apple Iphone 6","It becomes more bigger ...",R.drawable.ic_launcher));
    	art.add(new ArticleContent("Samsung Galaxy s4","It becomes more bigger than Apple Iphone 6 ...",R.drawable.iphone));
    	art.add(new ArticleContent("New Samsumg gear","itsdkfdsjoghsdif",R.drawable.ic_launcher));
    	Data.Articles.put("Phones", art);
    	ArrayList<ArticleContent> art2 = new ArrayList<ArticleContent>(); 
    	art2.add(new ArticleContent("Apple Iphone 6","It becomes more bigger ...",R.drawable.ic_launcher));
    	art2.add(new ArticleContent("Apple Iphone 6","It becomes more bigger ...",R.drawable.ic_launcher));
    	Data.Articles.put("Computers", art2);
    	new LoadTask().execute();
    }
    class LoadTask extends AsyncTask<Void, Void, Void> {
    	ArrayList<ArticleContent> art3= new ArrayList<ArticleContent>();
	    @Override
	    protected void onPreExecute() {
	      super.onPreExecute();

	    }
	    @Override
	    protected Void doInBackground(Void... params) {
	    	HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost("http://api.geonames.org/citiesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&lang=de&username=demo");
		    JSONObject jObj;
		try {
		        HttpResponse response = httpclient.execute(httppost);
		        String responseStr = EntityUtils.toString(response.getEntity());
		        jObj = new JSONObject(responseStr);

		        if (true){
		        	JSONArray jArr = jObj.getJSONArray("geonames");
		        	for (int i =0;i<2;i++){
		        		JSONObject curJ = jArr.getJSONObject(i);
		        		ArticleContent curA;			        
		        		curA = new ArticleContent(curJ.getString("toponymName"),curJ.getString("fcodeName"),R.drawable.ic_launcher);
		        		art3.add(curA);
		        	}
		        }
		    } catch (ClientProtocolException e) {
		        // TODO Auto-generated catch block
		    	System.out.println("Error1");
		   } catch (IOException e) {
		        // TODO Auto-generated catch block
				System.out.println("Error2");
		    } catch (JSONException e) {
				// TODO Auto-generated catch block
		    	System.out.println("Error3");
				e.printStackTrace();
		}
		    return null;
	    }
	    @Override
	    protected void onPostExecute(Void result) {
	      super.onPostExecute(result);
	      Data.Articles.put("Others", art3);
	    }
	} 
   
}