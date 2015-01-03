package com.example.tranthuong.hostapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tranthuong.hostapp.R;
import com.example.tranthuong.hostapp.datamodel.DataModel;
import com.example.tranthuong.hostapp.datamodel.DataService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_home)
public class HomeFragment extends Fragment
{
    private static final String TAG = "HomeFragment";//"HomeFragment";
    @Bean
    protected DataService dataService;

    @ViewById(R.id.homeTextMsg_id)
    protected TextView homeTextMsgTv;

    @ViewById(R.id.getUserDataBt_id)
    protected Button getUserDataBt;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    public static HomeFragment newInstance(String param1, String param2)
    {
        HomeFragment fragment = new HomeFragment_();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState)
    {
        Log.d(TAG, ">> onActivityCreated ***************** ");

        super.onActivityCreated(savedInstanceState);
        if(mListener!=null)
            mListener.setHeaderTitle(R.string.home_title);
    }

    @Override
    public void onStart() {
        Log.d(TAG, ">> onStart ***************** ");

        super.onStart();

        DataModel dataModel = dataService.getDataModel();
        if (dataModel ==null)
        {
            getUserDataBt.setVisibility(View.VISIBLE);
            homeTextMsgTv.setVisibility(View.GONE);
        }
        else
        {
            getUserDataBt.setVisibility(View.GONE);
            homeTextMsgTv.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onResume() {
        Log.d(TAG, ">> onResume ***************** ");

        super.onResume();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Click({R.id.getUserDataBt_id})
    void getUserData()
    {
        if(mListener!=null)
        {
            mListener.onFragmentInteraction( OnFragmentInteractionListener.LAUNCH_FETCHAPP_ACT);
        }
    }

    @AfterViews
    protected void afterViews()
    {
        Log.d(TAG, ">> afterViews ***************** ");
        if( mListener!=null)
        {
            mListener.setHeaderTitle(R.string.home_title);
        }

    }

}
