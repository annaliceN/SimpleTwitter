package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        Button tweet = (Button) findViewById(R.id.btTweet);
    }


    public void onClick(View v) {
        JsonHttpResponseHandler handler;
        client = TwitterApp.getRestClient(this);

        EditText etCompose = (EditText) findViewById(R.id.etCompose);

        // Prepare data intent
        Intent data = new Intent();

        // Pass relevant data back as a result
        data.putExtra("name", etCompose.getText().toString());
        data.putExtra("code", 200); // ints work too

        setResult(RESULT_OK, data);// set result code and bundle data for response
        // Activity finished ok, return the data
        handler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Tweet tweet;
                try {
                    tweet = Tweet.fromJson(response);
                    Intent intent = new Intent(ComposeActivity.this, TimelineActivity.class);
                    intent.putExtra("tweet", Parcels.wrap(tweet));


                    finish(); // closes the activity, pass data to parent
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }
        };
        client.sendTweet(etCompose.getText().toString(), handler);
        finish();

    }

}
