package com.ysn.examplearchcomponentroom.views.main;

import android.text.TextUtils;

import com.ysn.examplearchcomponentroom.views.base.Presenter;

/**
 * Created by root on 01/07/17.
 */

class MainActivityPresenter implements Presenter<MainActivityView> {

    private final String TAG = getClass().getSimpleName();
    private MainActivityView mainActivityView;

    @Override
    public void onAttach(MainActivityView view) {
        mainActivityView = view;
    }

    @Override
    public void onDetach() {
        mainActivityView = null;
    }



}
