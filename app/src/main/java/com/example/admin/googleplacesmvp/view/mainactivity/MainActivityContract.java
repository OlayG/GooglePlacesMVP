package com.example.admin.googleplacesmvp.view.mainactivity;

import com.example.admin.googleplacesmvp.BasePresenter;
import com.example.admin.googleplacesmvp.BaseView;
import com.example.admin.googleplacesmvp.model.MessageEvent;
import com.example.admin.googleplacesmvp.model.Result;
import com.google.android.gms.location.places.Place;

import java.util.List;

/**
 * Created by Admin on 8/25/2017.
 */

public interface MainActivityContract {

    interface view extends BaseView{

        void loadRV(MessageEvent results);
    }

    interface presenter extends BasePresenter<view>{

        void loadNearbyPlaces(Place place);
    }
}
