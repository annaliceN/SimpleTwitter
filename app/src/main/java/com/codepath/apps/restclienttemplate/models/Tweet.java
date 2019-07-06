package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Tweet {

    // list of the attributes
    public String body;
    public long uid; // database ID for the tweet
    public String createdAt;
    public User user;
    public int retweets;
    public int favorites;

    // empty constructor
    public Tweet() {

    };

    // deserialize data
    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        // extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        tweet.retweets = jsonObject.getInt("retweet_count");
        tweet.favorites = jsonObject.getInt("favorite_count");


        return tweet;
    }


}
