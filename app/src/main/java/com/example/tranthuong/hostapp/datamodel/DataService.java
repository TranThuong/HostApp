package com.example.tranthuong.hostapp.datamodel;

import android.content.Context;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EBean.Scope;
import org.androidannotations.annotations.RootContext;
import org.json.JSONException;
import org.json.JSONObject;

@EBean(scope=Scope.Singleton)
public class DataService {

    @RootContext
    Context context;

    private DataModel dataModel;

    @AfterInject
    protected void initService()
    {

    }

    public void loadData(String dataString)
    {
        try
        {
            dataModel = new DataModel( new JSONObject(dataString) );
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public DataModel getDataModel() {
        return dataModel;
    }


}
