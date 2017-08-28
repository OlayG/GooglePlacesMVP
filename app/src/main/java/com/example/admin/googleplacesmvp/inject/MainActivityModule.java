package com.example.admin.googleplacesmvp.inject;

import com.example.admin.googleplacesmvp.view.mainactivity.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Admin on 8/25/2017.
 */
@Module
public class MainActivityModule {

    @Provides
    MainActivityPresenter providesMainActivityPresenter() {
        return new MainActivityPresenter();
    }
}
