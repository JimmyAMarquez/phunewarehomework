package com.phunware.teams.android.nflteams.api;

import com.phunware.teams.android.nflteams.model.Venue;
import java.util.ArrayList;
import retrofit.Callback;
import retrofit.http.GET;


public interface NflApi {
    public static final String BASE_URL = "https://s3.amazonaws.com/jon-hancock-phunware";
    @GET("/nflapi-static.json")
    void getTeams(Callback<ArrayList<Venue>> cb);
}
