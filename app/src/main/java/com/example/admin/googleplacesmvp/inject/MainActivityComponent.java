package com.example.admin.googleplacesmvp.inject;

import com.example.admin.googleplacesmvp.MainActivity;

import dagger.Component;

/**
 * Created by Admin on 8/25/2017.
 */
@Component(modules = MainActivityModule.class)
public interface MainActivityComponent {

    void inject(MainActivity mainActivity);
}
