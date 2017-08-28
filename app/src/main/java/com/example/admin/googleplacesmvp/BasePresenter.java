package com.example.admin.googleplacesmvp;

/**
 * Created by Admin on 8/25/2017.
 */

public interface BasePresenter<V extends BaseView> {

    void attachView(V view);
    void detachView();
}
