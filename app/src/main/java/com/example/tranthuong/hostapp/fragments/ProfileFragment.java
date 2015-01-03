package com.example.tranthuong.hostapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tranthuong.hostapp.R;
import com.example.tranthuong.hostapp.datamodel.DataModel;
import com.example.tranthuong.hostapp.datamodel.DataService;
import com.example.tranthuong.hostapp.datamodel.DeviceDataModel;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_profile)
public class ProfileFragment extends Fragment
{
    private static final String TAG = "ProfileFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @Bean
    protected DataService dataService;

    @Bean
    protected DeviceDataModel deviceDataModel;

    @ViewById(R.id.profileFirstnameTv_id)
    protected TextView profileFirstnameTv;

    @ViewById(R.id.profileAgeTv_id)
    protected TextView profileAgeTv;

    @ViewById(R.id.profileImg_1_id)
    protected ImageView profileImg_1Iv;

    @ViewById(R.id.profileImg_2_id)
    protected ImageView profileImg_2Iv;

    @ViewById(R.id.profileImg_3_id)
    protected ImageView profileImg_3Iv;

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment_();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
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
    public void onActivityCreated (Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if(mListener!=null)
        {
            mListener.setHeaderTitle(R.string.profile_title);
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
    public void onStart()
    {
        Log.d(TAG, ">> onStart ***************** ");
        super.onStart();

        //Picasso.with(getActivity()).load("http://ppft.s3.amazonaws.com/u/2014/128/7165/medium_1_1411795598.jpg").into(profileImg_1Iv);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @AfterViews
    protected void afterViews()
    {
        Log.d(TAG, ">> afterViews ***************** ");

        DataModel dataModel = dataService.getDataModel();
        if (dataModel !=null)
        {
            profileFirstnameTv.setText(dataModel.getFirstName());
            profileAgeTv.setText(String.valueOf(dataModel.getAge()));

            //Log.d(TAG, ">> " + dataModel.getPhotoAtPos(1,deviceDataModel.getScreenSizeLayoutType()) );

            Picasso.with(getActivity()).load(dataModel.getPhotoAtPos(1,deviceDataModel.getScreenSizeLayoutType())).error(R.drawable.error_img).into(profileImg_1Iv);
            Picasso.with(getActivity()).load(dataModel.getPhotoAtPos(2,deviceDataModel.getScreenSizeLayoutType())).error(R.drawable.error_img).into(profileImg_2Iv);
            Picasso.with(getActivity()).load(dataModel.getPhotoAtPos(3,deviceDataModel.getScreenSizeLayoutType())).error(R.drawable.error_img).into(profileImg_3Iv);

        }

        if(mListener!=null)
        {
            mListener.setHeaderTitle(R.string.profile_title);
        }
    }

}
