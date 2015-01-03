package com.example.tranthuong.hostapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.tranthuong.hostapp.R;
import com.example.tranthuong.hostapp.datamodel.DataModel;
import com.example.tranthuong.hostapp.datamodel.DataService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_settings)
public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "SettingsFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @Bean
    protected DataService dataService;

    @ViewById(R.id.settingMenImg_id)
    protected ImageView settingMenImg;

    @ViewById(R.id.settingWomenImg_id)
    protected ImageView settingWomenImg;

    @ViewById(R.id.minimunAgeTv_id)
    protected TextView minimunAgeTv;

    @ViewById(R.id.maximunAgeTv_id)
    protected TextView maximunAgeTv;

    @ViewById(R.id.rayonDistanceSb_id)
    protected SeekBar rayonDistanceSb;

    @ViewById(R.id.rayonDistanceTv_id)
    protected TextView rayonDistanceTv;

    private View.OnTouchListener seekBarLis = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            return true;
        }
    };

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment_();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SettingsFragment() {
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
        super.onActivityCreated(savedInstanceState);
        if(mListener!=null)
            mListener.setHeaderTitle(R.string.settings_title);
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
           if(dataModel.getInterestGender() == 0)
           {
               settingMenImg.setImageResource(R.drawable.selected_profile);
               settingWomenImg.setImageResource(R.drawable.unselected_profile);
           }
           else
           {
               settingMenImg.setImageResource(R.drawable.unselected_profile);
               settingWomenImg.setImageResource(R.drawable.selected_profile);
           }

            minimunAgeTv.setText(String.valueOf(dataModel.getAgeRangeMin()));
            maximunAgeTv.setText(String.valueOf(dataModel.getAgeRangeMax()));

            rayonDistanceTv.setText(String.valueOf(dataModel.getLocationRadius()));
            rayonDistanceSb.setProgress(dataModel.getLocationRadius());
            //rayonDistanceSb.setEnabled(false);
            rayonDistanceSb.setOnTouchListener(seekBarLis);
        }

        if(mListener!=null)
        {
            mListener.setHeaderTitle(R.string.settings_title);
        }
    }


}
