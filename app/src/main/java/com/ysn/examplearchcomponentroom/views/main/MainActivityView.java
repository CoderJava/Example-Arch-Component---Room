package com.ysn.examplearchcomponentroom.views.main;

import com.ysn.examplearchcomponentroom.views.base.View;
import com.ysn.examplearchcomponentroom.views.main.adapter.AdapterDataStudentMainActivity;

/**
 * Created by root on 01/07/17.
 */

interface MainActivityView extends View {

    void loadData(AdapterDataStudentMainActivity adapterDataStudentMainActivity);

    void loadDataFailed();

    void addNewItemAdapter();

    void updateItemAdapter();
}
