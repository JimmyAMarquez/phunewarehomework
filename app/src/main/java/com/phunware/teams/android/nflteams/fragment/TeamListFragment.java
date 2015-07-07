package com.phunware.teams.android.nflteams.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;


import com.phunware.teams.android.nflteams.adapter.TeamsAdapter;
import com.phunware.teams.android.nflteams.NflTeamsApplication;
import com.phunware.teams.android.nflteams.model.Venue;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class TeamListFragment extends ListFragment implements AbsListView.OnScrollListener {

    private static final int THRESHOLD = 20;
    private static final int ITEMS_PER_ITERATION = 15;
    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private static final String INDEX = "Index";
    private static final String TOP = "Top";
    private static List<Venue> mTeamListFull=new ArrayList<>() ;
    private static List<Venue> mTeamList = new ArrayList<>();
    private static int mCurrentRow = 0;
    ListView mTeamListView;
    TeamsAdapter mTeamsAdapter;

     private Callbacks mCallbacks = sTeamCallbacks;
     private int mActivatedPosition = ListView.INVALID_POSITION;

    public interface Callbacks {
         void onItemSelected(Venue venue);
    }


    private static Callbacks sTeamCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(Venue venue) {
        }
    };


    public TeamListFragment() {
    }
    


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTeamsAdapter = new TeamsAdapter(getActivity(), mTeamList);
        //To prevent reload data that has been already downloaded
        if(mTeamListFull.size() == 0 ) {
            //Calling the appContextInstance
            NflTeamsApplication mReadTeam = (NflTeamsApplication)getActivity().getApplicationContext();
           mReadTeam.getApiService().getTeams(new Callback<ArrayList<Venue>>() {
                @Override
                public void success(ArrayList<Venue> s, Response response) {
                    mTeamListFull = s;
                    for (Venue t : mTeamListFull) {
                        mTeamList.add(t);
                        mCurrentRow += 1;
                        if (mCurrentRow >= THRESHOLD) {
                            break;
                        }
                        mTeamsAdapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void failure(RetrofitError error) {
                    error.getMessage();
                }
            });
        }
        setListAdapter(mTeamsAdapter);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == SCROLL_STATE_IDLE) {
            if (mTeamListView.getLastVisiblePosition() >= mTeamListView.getCount() - 1 - THRESHOLD) {
                for(int i= 0; i <ITEMS_PER_ITERATION;i++){
                    if(mCurrentRow >mTeamListFull.size()-1){
                        mTeamsAdapter.notifyDataSetChanged();return;}
                    Venue temp = mTeamListFull.get(mCurrentRow);
                    mTeamList.add(temp);
                    mCurrentRow++;
                }
                mTeamsAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(INDEX)&&savedInstanceState.containsKey(TOP))
                getListView().setSelectionFromTop(savedInstanceState.getInt(INDEX)
                        ,savedInstanceState.getInt(TOP));
        }
        mTeamListView = getListView();
        mTeamListView.setOnScrollListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Reset the active callbacks interface to the Team implementation.
        mCallbacks = sTeamCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        mCallbacks.onItemSelected(mTeamListFull.get(position));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }else {
            ListView lv = getListView();
            int index = lv.getFirstVisiblePosition();
            View v = lv.getChildAt(0);
            int top = (v == null) ? 0 : (v.getTop() - lv.getPaddingTop());

            outState.putInt(INDEX,index);
            outState.putInt(TOP,top);

        }
        super.onSaveInstanceState(outState);
    }


    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);

    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }
        mActivatedPosition = position;
    }
}
