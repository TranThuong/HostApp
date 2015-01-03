package com.example.tranthuong.hostapp.datamodel;

import android.content.res.Configuration;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TRANTHUONG.
 */
public class DataModel
{
    private static final String TAG = "DataModel";
    private JSONObject jsDataModel;
    private String imgPosUrl_1, imgPosUrl_2, imgPosUrl_3;

    protected DataModel(JSONObject jsData)
    {
        jsDataModel = jsData;
    }

    public String getFirstName()
    {
        if(jsDataModel!=null)
        {
            try
            {
                return jsDataModel.getString("name");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return "";
    }

    public int getAge()
    {
        if(jsDataModel!=null)
        {
            try
            {
                return jsDataModel.getInt("age");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        return -1;
    }

    public int getAgeRangeMin()
    {
        if(jsDataModel!=null)
        {
            try
            {
                return jsDataModel.getInt("age_range_min");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        return 0;
    }

    public int getAgeRangeMax()
    {
        if(jsDataModel!=null)
        {
            try
            {
                return jsDataModel.getInt("age_range_max");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        return 0;
    }

    public int getGender()
    {
        if(jsDataModel!=null)
        {
            try
            {
                return jsDataModel.getInt("gender");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        return 0;// 0 men, 1 woman
    }

    public int getInterestGender()
    {
        if(jsDataModel!=null)
        {
            try
            {
                return jsDataModel.getInt("gender_interest");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        return 0;// 0 men, 1 woman
    }

    public int getLocationRadius()
    {
        if(jsDataModel!=null)
        {
            try
            {
                return jsDataModel.getInt("location_radius");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        return 0;// 0 men, 1 woman
    }

    public String getPhotoAtPos(int pos, int screenLayoutType)
    {
        String imgUrl = null;

        switch(pos)
        {
            case 1:
            {
                imgUrl = imgPosUrl_1;
            }
            break;
            case 2:
            {
                imgUrl = imgPosUrl_2;
            }
            break;
            case 3:
            default:
                imgUrl = imgPosUrl_3;
       }

        if(imgUrl == null)
        {
            imgUrl = getUrlImage( pos, screenLayoutType);
        }

        Log.i(TAG,"getPhotoAtPos imgUrl = "+imgUrl);


        return imgUrl;
    }

    private String getUrlImage(int pos, int screenLayoutType)
    {
        try
        {
            JSONArray photos = jsDataModel.getJSONArray("photos");
            String hostPhotoUrl =  jsDataModel.getString("photo_host");

            if(photos!=null && hostPhotoUrl!=null)
            {
                JSONObject photoJSObj = null;
                int vSize = photos.length();
                for ( int i=0; i< vSize; i++)
                {
                    photoJSObj = photos.getJSONObject(i);
                    if(photoJSObj!=null && photoJSObj.getInt("position") == pos)
                    {
                        break;
                    }
                }

                if(photoJSObj!=null)
                {
                    String screenType;
                    switch(screenLayoutType)
                    {
                        case Configuration.SCREENLAYOUT_SIZE_SMALL:
                        {
                            screenType = "small";
                        }
                        break;
                        case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                        {
                            screenType = "medium";
                        }
                        break;
                        case Configuration.SCREENLAYOUT_SIZE_LARGE:
                        default:
                            screenType = "large";
                    }

                    if(screenType!=null)
                    {
                        String imgUrl = hostPhotoUrl + photoJSObj.getString(screenType);

                        switch(pos)
                        {
                            case 1:
                            {
                                imgPosUrl_1 = imgUrl;
                            }
                            break;
                            case 2:
                            {
                                imgPosUrl_2= imgUrl;
                            }
                            break;
                            case 3:
                            default:
                                imgPosUrl_3= imgUrl;
                        }

                        Log.i(TAG,"getUrlImage imgUrl = "+imgUrl);

                        return imgUrl;

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
