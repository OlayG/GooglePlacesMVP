package com.example.admin.googleplacesmvp.view.mainactivity;

import android.util.Log;

import com.example.admin.googleplacesmvp.model.MessageEvent;
import com.example.admin.googleplacesmvp.model.NearbyPlacesModel;
import com.example.admin.googleplacesmvp.model.Result;
import com.google.android.gms.location.places.Place;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Admin on 8/25/2017.
 */

public class MainActivityPresenter implements MainActivityContract.presenter {

    private static final String TAG = "MainActivityPresenter";

    private static final String GEO_KEY = "AIzaSyB8ZaDZOdVAPQBB-gRpAJdHRZZSQbcPlRM";
    private static final String GEO_RADIUS = "2000";
    MainActivityContract.view view;

    public void attachView(MainActivityContract.view view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void loadNearbyPlaces(Place place) {
        String latlng = "" + place.getLatLng().latitude + "," + place.getLatLng().longitude;

        OkHttpClient client = new OkHttpClient();
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("maps.googleapis.com")
                .addPathSegment("maps")
                .addPathSegment("api")
                .addPathSegment("place")
                .addPathSegment("nearbysearch")
                .addPathSegment("json")
                .addQueryParameter("location", latlng)
                .addQueryParameter("radius", GEO_RADIUS)
                .addQueryParameter("key", GEO_KEY)
                .build();

        final Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();

                NearbyPlacesModel nearbyPlacesModel = gson.fromJson(response.body().string(), NearbyPlacesModel.class);
                List<Result> results = nearbyPlacesModel.getResults();
                Log.d(TAG, "onResponse: " + nearbyPlacesModel.toString());
                EventBus.getDefault().post(new MessageEvent(results));


            }
        });
    }
}
