package com.phunware.teams.android.nflteams.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.applaudo.teams.android.nflteams.R;
import com.phunware.teams.android.nflteams.model.Venue;

import java.util.List;

public class TeamsAdapter extends BaseAdapter {


    private Activity mActivity;
    private LayoutInflater mInflater;
    private List<Venue> mTeamItems;

    public TeamsAdapter(Activity mActivity, List<Venue> mTeamItems) {
        this.mTeamItems = mTeamItems;
        mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mTeamItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mTeamItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mTeamItems.get(position).getId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.fragment_team_list, null);
            final STeamItem sTeamItem = new STeamItem();
            sTeamItem.mVenueName = (TextView)view.findViewById(R.id.venue_name);
            sTeamItem.mVenueLocation = (TextView)view.findViewById(R.id.venue_location);
            view.setTag(sTeamItem);
        }else{
            view = convertView;
        }
        STeamItem sTeamItem = (STeamItem)view.getTag();

        String fullVenueLocation = mTeamItems.get(position).getCityStateZip();

        sTeamItem.mVenueName.setText(mTeamItems.get(position).getName());
        sTeamItem.mVenueLocation.setText(fullVenueLocation);

        return view;
    }

    static class STeamItem {
        protected TextView mVenueName;
        protected TextView mVenueLocation;
    }

}
