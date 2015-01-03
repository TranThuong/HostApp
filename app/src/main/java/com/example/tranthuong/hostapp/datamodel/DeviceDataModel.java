package com.example.tranthuong.hostapp.datamodel;

import android.content.Context;
import android.content.res.Configuration;

import com.example.tranthuong.hostapp.R;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by TRANTHUONG.
 */
@EBean(scope= EBean.Scope.Singleton)
public class DeviceDataModel {

    @RootContext
    Context context;

    private boolean isTablet;

    private int screenSizeLayoutType;

    @AfterInject
    protected void initDataModel()
    {
        isTablet                = context.getResources().getBoolean(R.bool.isTablet);
        screenSizeLayoutType    = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
    }

    public boolean isTablet()
    {
        return isTablet;
    }

    public int getScreenSizeLayoutType() {
        return screenSizeLayoutType;
    }

    public boolean isSmallScreenLayoutSize()
    {
        return (screenSizeLayoutType == Configuration.SCREENLAYOUT_SIZE_SMALL);
    }

    public boolean isNormalScreenLayoutSize()
    {
        return (screenSizeLayoutType == Configuration.SCREENLAYOUT_SIZE_NORMAL);
    }

    public boolean isLargeScreenLayoutSize()
    {
        return (screenSizeLayoutType == Configuration.SCREENLAYOUT_SIZE_LARGE);
    }

    public boolean isXLargeScreenLayoutSize()
    {
        return (screenSizeLayoutType == Configuration.SCREENLAYOUT_SIZE_XLARGE);
    }

}