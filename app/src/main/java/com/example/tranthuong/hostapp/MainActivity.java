package com.example.tranthuong.hostapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tranthuong.hostapp.animations.SlideMenuAniSmartPhone;
import com.example.tranthuong.hostapp.datamodel.DataService;
import com.example.tranthuong.hostapp.datamodel.DeviceDataModel;
import com.example.tranthuong.hostapp.fragments.HomeFragment;
import com.example.tranthuong.hostapp.fragments.OnFragmentInteractionListener;
import com.example.tranthuong.hostapp.fragments.ProfileFragment;
import com.example.tranthuong.hostapp.fragments.SettingsFragment;
import com.example.tranthuong.hostapp.tools.Tools;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_main)
public class MainActivity extends Activity implements OnFragmentInteractionListener
{
    private static final String TAG = "MainActivity";

    private static final String FRAG_NAMES[]        = {"home_frag", "profile_frag", "settings_frag"};
    private static final int MENU_ITEMS_IDS[]       = {R.id.homeItem, R.id.profileItem, R.id.settingsItem};

    private static final int    HOME_FRAG_INDEX     = 0;
    private static final int    PROFILE_FRAG_INDEX  = 1;
    private static final int    SETTING_FRAG_INDEX  = 2;

    @Bean
    protected DataService dataService;

    @Bean
    protected DeviceDataModel deviceDataModel;

    @ViewById(R.id.sectionTitle_id)
    protected TextView sectionTitleTv;

    private SlideMenuAniSmartPhone slideMenuAniSmartPhone;

    @InstanceState
    protected int currFragIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(savedInstanceState==null)
        {
            currFragIndex = HOME_FRAG_INDEX;
            Intent actIntent = getIntent();
            if(actIntent!=null && actIntent.getExtras()!=null)
            {
                internalOnNewIntent(actIntent);
            }
        }

        if(deviceDataModel.isTablet())
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        }
        else
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
    }

    @Override
    protected void onNewIntent (Intent intent)
    {
        Log.d(TAG, ">> onNewIntent currFragIndex="+currFragIndex);
        super.onNewIntent(intent);

        internalOnNewIntent(intent);

    }

    @AfterViews
    protected void afterViews()
    {
        Log.d(TAG, "afterViews");
        RelativeLayout slideMenuLayout = (RelativeLayout) findViewById(R.id.slidemenu_layout);

        if(deviceDataModel.isTablet())
        {
            // is tablet
            slideMenuLayout.setVisibility(View.VISIBLE);

        }
        else
        {
            //is phone
            slideMenuLayout = (RelativeLayout)findViewById(R.id.slidemenu_layout);
            slideMenuLayout.setVisibility(View.INVISIBLE);
            RelativeLayout bodyLayout = (RelativeLayout) findViewById(R.id.main_layout);

            slideMenuAniSmartPhone = new SlideMenuAniSmartPhone(this,
                    (Button)findViewById(R.id.slideMenuBt_id),
                    slideMenuLayout, bodyLayout);

        }

        setMenu(currFragIndex);

    }

    public void onMenuItemClick(View itemView)
    {
        if(!deviceDataModel.isTablet())
        {
            if (slideMenuAniSmartPhone!=null)
            {
                slideMenuAniSmartPhone.onClick(itemView);
            }
        }

        int viewId = itemView.getId();
        Log.d(TAG, "onMenuItemClick id = " + viewId);
        switch (viewId)
        {
            case R.id.homeItem:
                setMenu(HOME_FRAG_INDEX);
                break;
            case R.id.profileItem:
                setMenu(PROFILE_FRAG_INDEX);
                break;
            case R.id.settingsItem:
                setMenu(SETTING_FRAG_INDEX);
                break;
            default:
                //unknown action
        }
    }

    private void setMenu(int selection)
    {
        if (selection >=0 && selection<FRAG_NAMES.length)
        {
            setFocusMenuItem(currFragIndex, false);
            currFragIndex = selection;
            showFragment(FRAG_NAMES[currFragIndex]);
            setFocusMenuItem(currFragIndex, true);
        }
    }

    private void setFocusMenuItem(int itemIndex, boolean isFocus)
    {
        View itemView = findViewById(MENU_ITEMS_IDS[itemIndex]);
        if(itemView!=null && itemView instanceof TextView)
        {
            int color = isFocus?getResources().getColor(R.color.menu_item_txt_color): Color.WHITE;
            ((TextView)itemView).setTextColor(color);
        }
    }

    private void showFragment(String fragName)
    {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        Fragment f = getFragmentManager().findFragmentByTag(fragName);

        if (f == null)
        {
            if (fragName.equals(FRAG_NAMES[HOME_FRAG_INDEX]))
            {
                f = HomeFragment.newInstance(null, null);
            }
            else if (fragName.equals(FRAG_NAMES[PROFILE_FRAG_INDEX]))
            {
                f = ProfileFragment.newInstance(null, null);
            }
            else if (fragName.equals(FRAG_NAMES[SETTING_FRAG_INDEX]))
            {
                f = SettingsFragment.newInstance(null, null);
            }
        }

        transaction.replace(R.id.contenuPage_id, f, fragName);
        //transaction.addToBackStack(null);
        transaction.commit();

    }

    private void internalOnNewIntent(Intent intent)
    {
        String dataStr = intent.getStringExtra("fetched.data");
        //Log.d(TAG, "internalOnNewIntent  dataStr = \n "+dataStr);
        if(dataStr!=null && dataService!=null)
        {
            dataService.loadData(dataStr);

            setMenu(HOME_FRAG_INDEX);
        }
    }

    @Override
    public void onFragmentInteraction(int actionId)
    {
        if(actionId == LAUNCH_FETCHAPP_ACT)
        {
            Intent intent = getPackageManager().getLaunchIntentForPackage("com.tranthuong.fetchapp");
            if (intent != null)
            {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            else
            {
                Tools.showAlert(this, getString(R.string.fetchapp_non_installed_err));
            }
        }
    }

    @Override
    public void setHeaderTitle(int resId)
    {
        sectionTitleTv.setText(resId);
    }

}