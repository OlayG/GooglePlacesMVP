package com.example.admin.googleplacesmvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import com.example.admin.googleplacesmvp.adapter.GooglePlacesRecyclerView;
import com.example.admin.googleplacesmvp.inject.DaggerMainActivityComponent;
import com.example.admin.googleplacesmvp.model.MessageEvent;
import com.example.admin.googleplacesmvp.model.Result;
import com.example.admin.googleplacesmvp.view.mainactivity.MainActivityContract;
import com.example.admin.googleplacesmvp.view.mainactivity.MainActivityPresenter;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class MainActivity extends AppCompatActivity implements MainActivityContract.view {

    private static final String TAG = "MainActivity";
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 10;

    @Inject
    MainActivityPresenter presenter;
    MapFragment mapFragment;
    @BindView(R.id.llPlacesRV)
    LinearLayout llPlacesRV;
    @BindView(R.id.rvNearbyPlaces)
    RecyclerView rvNearbyPlaces;

    RecyclerView.LayoutManager layoutManager;
    GooglePlacesRecyclerView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DaggerMainActivityComponent.create().inject(this);
        presenter.attachView(this);

        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.flFrame1, mapFragment, TAG).commit();

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    public void findPlace() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            //.setFilter(typeFilter)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    // A place has been received; use requestCode to track the request.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                mapFragment.loadNewMarker(place);
                llPlacesRV.setVisibility(View.VISIBLE);
                presenter.loadNearbyPlaces(place);
                Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    public void buttonOnClick(View view) {

        switch (view.getId()) {

            case R.id.fab:
                findPlace();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void loadRV(MessageEvent results) {

        layoutManager = new LinearLayoutManager(this);
        rvNearbyPlaces.setLayoutManager(layoutManager);
        adapter = new GooglePlacesRecyclerView(results.results, this);
  /*      AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        rvNearbyPlaces.setAdapter(alphaAdapter);*/

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        rvNearbyPlaces.setItemAnimator(itemAnimator);
        rvNearbyPlaces.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
