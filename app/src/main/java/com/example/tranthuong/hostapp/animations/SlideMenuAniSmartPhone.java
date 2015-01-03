package com.example.tranthuong.hostapp.animations;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.tranthuong.hostapp.R;

public class SlideMenuAniSmartPhone implements AnimationListener, View.OnClickListener
{
    Context context;

    RelativeLayout slideMenuLayout, otherLayout;

    private Animation filterSlideIn, filterSlideOut, otherSlideIn, otherSlideOut;

    private static int otherLayoutWidth, otherLayoutHeight;

    private boolean isOtherSlideOut = false;

    private int deviceWidth;

    public SlideMenuAniSmartPhone(Context context, Button menuSlideBt, RelativeLayout slideMenuLayout, RelativeLayout otherLayout)
    {
        this.context = context;

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        deviceWidth = displayMetrics.widthPixels;

        this.slideMenuLayout = slideMenuLayout;
        this.otherLayout = otherLayout;

        menuSlideBt.setOnClickListener(this);

        initializeFilterAnimations();
    }

    private void initializeFilterAnimations(RelativeLayout pSlideMenuLayout)
    {
        this.slideMenuLayout = pSlideMenuLayout;
        filterSlideIn = AnimationUtils.loadAnimation(context, R.anim.filter_slide_in);
        filterSlideOut = AnimationUtils.loadAnimation(context, R.anim.filter_slide_out);
    }

    private void initializeOtherAnimations(RelativeLayout otherLayout)
    {
        this.otherLayout = otherLayout;

        otherLayoutWidth = otherLayout.getWidth();

        otherLayoutHeight = otherLayout.getHeight();

        otherSlideIn = AnimationUtils.loadAnimation(context, R.anim.other_slide_in);
        otherSlideIn.setAnimationListener(this);

        otherSlideOut = AnimationUtils.loadAnimation(context, R.anim.other_slide_out);
        otherSlideOut.setAnimationListener(this);
    }

    private void initializeFilterAnimations()
    {
        final ViewTreeObserver filterObserver = slideMenuLayout.getViewTreeObserver();

        filterObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                slideMenuLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                DisplayMetrics displayMetrics = SlideMenuAniSmartPhone.this.context.getResources().getDisplayMetrics();

                int deviceWidth = displayMetrics.widthPixels;

                int filterLayoutWidth = (deviceWidth * 80) / 100;
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(filterLayoutWidth, RelativeLayout.LayoutParams.MATCH_PARENT);

                slideMenuLayout.setLayoutParams(params);

                initializeFilterAnimations(slideMenuLayout);

            }
        });

        final ViewTreeObserver findObserver = otherLayout.getViewTreeObserver();

        findObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                //findLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                slideMenuLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                initializeOtherAnimations(otherLayout);
            }
        });

    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();

        switch(id)
        {

            case R.id.slideMenuBt_id:
            case R.id.homeItem:
            case R.id.profileItem:
            case R.id.settingsItem:

                toggleSliding();

                break;
        }
    }

    private void toggleSliding()
    {
        if(isOtherSlideOut) //check if findLayout is already slided out so get so animate it back to initial position
        {
            slideMenuLayout.startAnimation(filterSlideOut);

            slideMenuLayout.setVisibility(View.INVISIBLE);

            otherLayout.startAnimation(otherSlideIn);
        }
        else //slide findLayout Out and slideMenuLayout In
        {
            otherLayout.startAnimation(otherSlideOut);

            slideMenuLayout.setVisibility(View.VISIBLE);

            slideMenuLayout.startAnimation(filterSlideIn);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation)
    {
        if(isOtherSlideOut) //Now here we will actually move our view to the new position,because animations just move the pixels not the view
        {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(otherLayoutWidth, otherLayoutHeight);

            otherLayout.setLayoutParams(params);

            isOtherSlideOut = false;
        }
        else
        {
            int margin = (deviceWidth * 80) / 100;

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(otherLayoutWidth, otherLayoutHeight);

            params.leftMargin = margin;

            params.rightMargin = -margin; //same margin from right side (negavite) so that our layout won't get shrink

            otherLayout.setLayoutParams(params);

            isOtherSlideOut = true;

            dimOtherLayout();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation)
    {

    }

    @Override
    public void onAnimationStart(Animation animation)
    {

    }

    private void dimOtherLayout()
    {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.5f);
        //alphaAnimation.setDuration(300);
        alphaAnimation.setFillAfter(true);
        otherLayout.startAnimation(alphaAnimation);
    }

}