package com.ysn.examplearchcomponentroom.views.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.ysn.examplearchcomponentroom.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    private final String TAG = getClass().getSimpleName();
    private MainActivityPresenter mainActivityPresenter;

    @BindView(R.id.recycler_view_data_activity_main)
    RecyclerView recyclerViewDataActivityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPresenter();
    }

    private void initPresenter() {
        mainActivityPresenter = new MainActivityPresenter();
    }

    @Override
    protected void onResume() {
        onAttachView();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        onDetachView();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_add_menu_main_activity:
                // todo: intent to AddStudentActivity
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttachView() {
        mainActivityPresenter.onAttach(this);
    }

    @Override
    public void onDetachView() {
        mainActivityPresenter.onDetach();
    }

}
