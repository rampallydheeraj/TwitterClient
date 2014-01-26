package com.codepath.apps.mytwitterapp;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {

	Button loadmore;
	ListView lvTweets;
	TextView tv;
	ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	int count=25;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		MyTwitterApp.getRestClient().getHomeTimeLine(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsontweets)
			{
				tweets = Tweet.fromJson(jsontweets);
				lvTweets = (ListView)findViewById(R.id.lvtweets);
				TweetsAdapter adapter = new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);
				
				lvTweets.setOnScrollListener(new EndlessScrollListener() {
					@Override
					public void onLoadMore(int page, int totalItemsCount) {
						count = count + 10;
						loadMoreTweets(count);
					}
		        });
			}
		},count);
		
	}
	private void loadMoreTweets(int count) 
	{
		MyTwitterApp.getRestClient().getHomeTimeLine(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsontweets)
			{
				tweets = Tweet.fromJson(jsontweets);
				lvTweets = (ListView)findViewById(R.id.lvtweets);
				TweetsAdapter adapter = new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);
			}
		},count);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	public void onComposeAction(MenuItem mi) {
	     // handle click here
		Intent i = new Intent(this,ComposeActivity.class);
		i.putExtra("label", "Please Compose");
		startActivity(i);
		
		
	  }
}
