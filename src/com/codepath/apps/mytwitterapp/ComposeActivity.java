package com.codepath.apps.mytwitterapp;


import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}

	 public void onCompose(View view) {
	        EditText etBody = (EditText) findViewById(R.id.etBody);
	        String body = etBody.getText().toString();
	        MyTwitterApp.getRestClient().postTweet(new JsonHttpResponseHandler() {
	            @Override
	            public void onSuccess(JSONObject jsonTweet) {
	                returnJsonTweetAsString(jsonTweet);
	            }
	        }, body);
	    }

	    private void returnJsonTweetAsString(JSONObject jsonTweet) {
	        String tweetString = jsonTweet.toString();
	        Intent data = new Intent();
	        data.putExtra("tweetString", tweetString);
	        setResult(RESULT_OK, data);
	        Intent i = new Intent(getApplicationContext(),TimelineActivity.class);
		    startActivity(i);
	    }
	
}
