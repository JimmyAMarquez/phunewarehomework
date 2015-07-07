package com.phunware.teams.android.nflteams.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.applaudo.teams.android.nflteams.R;
import com.phunware.teams.android.nflteams.fragment.TeamDetailFragment;
import com.phunware.teams.android.nflteams.model.Venue;

public class TeamDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
           TeamDetailFragment fragment =
                   TeamDetailFragment.newInstance((Venue)getIntent().getParcelableExtra(TeamDetailFragment.ARG_VENUE_ITEM));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.team_detail_container, fragment)
                    .commit();
        }

    }
}
