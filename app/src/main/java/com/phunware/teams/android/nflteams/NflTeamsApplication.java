package com.phunware.teams.android.nflteams;


import android.app.Application;
import com.phunware.teams.android.nflteams.api.NflApi;
import com.phunware.teams.android.nflteams.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


public class NflTeamsApplication extends Application
{
    private static NflTeamsApplication singleton;
    private NflApi apiService;
    @Override
    public void onCreate() {
        super.onCreate();
        Gson gson = new GsonBuilder()
                .setDateFormat(ScheduleItem.DATE_FORMAT)
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(NflApi.BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();
        apiService = restAdapter.create(NflApi.class);
        singleton = this;
    }

    public NflApi getApiService() {
        return apiService;
    }

    public NflTeamsApplication getInstance(){
        return singleton;
    }
}
