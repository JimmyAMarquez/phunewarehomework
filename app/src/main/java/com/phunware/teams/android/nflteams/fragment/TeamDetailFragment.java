package com.phunware.teams.android.nflteams.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.applaudo.teams.android.nflteams.R;
import com.phunware.teams.android.nflteams.model.ScheduleItem;
import com.phunware.teams.android.nflteams.model.Venue;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;



public class TeamDetailFragment extends Fragment  {

    public static final String ARG_VENUE_ITEM = "venue";
    private Venue mItem;
    private ShareActionProvider mShareActionProvider;

    public TeamDetailFragment(){
    }

    public static TeamDetailFragment newInstance(Venue venue) {
        TeamDetailFragment myFragment = new TeamDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_VENUE_ITEM, venue);
        myFragment.setArguments(arguments);
        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                if (getArguments().containsKey(ARG_VENUE_ITEM)) {
                    mItem = getArguments().getParcelable(ARG_VENUE_ITEM);
                }
            }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_team_detail, container, false);
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.mFullVenueLocation)).setText(mItem.getName());

            Glide.with(this)
                    .load(mItem.getImageUrl())
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
                    .into(((android.widget.ImageView) rootView.findViewById(R.id.mImgStadium)));

            String fullVenueLocation = mItem.getCityStateZip();

            ((TextView)rootView.findViewById(R.id.mFullVenueLocation)).setText(fullVenueLocation);
            ((TextView)rootView.findViewById(R.id.mStadiumName)).setText(mItem.getName());


            (rootView.findViewById(R.id.mBtnShare)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =shareContent();
                    startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_via)));
                }
            });

            List ls = new ArrayList<>();
            for(ScheduleItem si : mItem.getSchedule()) {
                ls.add(si.getDecoratedDate());
            }
            ((android.widget.ListView) rootView.findViewById(R.id.mScheduleTime))
                    .setAdapter(new android.widget.ArrayAdapter<>(
                            rootView.getContext(),
                            android.R.layout.simple_list_item_activated_1,
                            android.R.id.text1,
                            ls));
        }
        return rootView;
    }


     private Intent shareContent() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareText = mItem.getShareText();
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                getResources().getString( R.string.nfl_teams_title));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
        return sharingIntent;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.share_menu, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        setShareIntent(shareContent());
    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
            case R.id.action_share:
                Intent intent =shareContent();
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_via)));
        }
        return super.onOptionsItemSelected(item);
    }

}
