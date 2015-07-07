package com.phunware.teams.android.nflteams.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;

import com.applaudo.teams.android.nflteams.R;
import com.phunware.teams.android.nflteams.fragment.TeamDetailFragment;
import com.phunware.teams.android.nflteams.fragment.TeamListFragment;
import com.phunware.teams.android.nflteams.model.Venue;


public class TeamListActivity extends ActionBarActivity
        implements TeamListFragment.Callbacks {

    private boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);
        if (findViewById(R.id.team_detail_container) != null) {
             mTwoPane = true;
            ((TeamListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.team_list))
                    .setActivateOnItemClick(true);
        }
    }

    @Override
    public void onItemSelected(Venue venue) {
        if (mTwoPane) {
            TeamDetailFragment fragment = TeamDetailFragment.newInstance(venue);
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.team_detail_container, fragment)
                    .commit();
        } else {
            Intent detailIntent = new Intent(this, TeamDetailActivity.class);
            detailIntent.putExtra(TeamDetailFragment.ARG_VENUE_ITEM, venue);
            startActivity(detailIntent);
        }
    }
}
