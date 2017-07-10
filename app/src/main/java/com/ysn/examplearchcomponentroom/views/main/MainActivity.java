package com.ysn.examplearchcomponentroom.views.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ysn.examplearchcomponentroom.R;
import com.ysn.examplearchcomponentroom.db.entity.Student;
import com.ysn.examplearchcomponentroom.views.main.adapter.AdapterDataStudentMainActivity;
import com.ysn.examplearchcomponentroom.views.submenu.student.add.StudentAddActivity;
import com.ysn.examplearchcomponentroom.views.submenu.student.edit.StudentEditActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

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
        EventBus.getDefault().register(this);
        doLoadData();
    }

    private void doLoadData() {
        mainActivityPresenter.onLoadData(this);
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
                startActivity(new Intent(this, StudentAddActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onMessageEvent(Map<String, Object> mapDataStudent) {
        String action = mapDataStudent.get("action").toString();
        if (action.equalsIgnoreCase("insert")) {
            Object objectStudent = mapDataStudent.get("student");
            Student student = null;
            if (objectStudent instanceof Student) {
                student = (Student) objectStudent;
            }
            mainActivityPresenter.onAddNewItemAdapter(student);
        } else if (action.equalsIgnoreCase("update")) {
            Object objectIndexChanged = mapDataStudent.get("indexChanged");
            Object objectStudent = mapDataStudent.get("student");
            int indexChanged = 0;
            Student student = null;
            if (objectStudent instanceof Student) {
                student = (Student) objectStudent;
            }
            if (objectIndexChanged instanceof Integer) {
                indexChanged = (Integer) objectIndexChanged;
            }
            mainActivityPresenter.onUpdateItemAdapter(indexChanged, student);
        }
    }

    @Override
    public void onAttachView() {
        mainActivityPresenter.onAttach(this);
    }

    @Override
    public void onDetachView() {
        mainActivityPresenter.onDetach();
    }


    @Override
    public void loadData(AdapterDataStudentMainActivity adapterDataStudentMainActivity) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewDataActivityMain.setLayoutManager(linearLayoutManager);
        recyclerViewDataActivityMain.setAdapter(adapterDataStudentMainActivity);
    }

    @Override
    public void loadDataFailed() {
        Toast.makeText(this, "Internal error!", Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void addNewItemAdapter() {
        // nothing to do in here
    }

    @Override
    public void updateItemAdapter() {
        // nothing to do in here
    }

    @Override
    public void clickEdit(Student student) {
        Intent intentStudentEditActivity = new Intent(this, StudentEditActivity.class);
        EventBus.getDefault().postSticky(student);
        startActivity(intentStudentEditActivity);
    }

    @Override
    public void clickDelete(final Student student) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setCancelable(false);
        builder.setMessage("Are you sure to delete this data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mainActivityPresenter.onClickDelete(student);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialogDelete = builder.create();
        alertDialogDelete.show();
    }
}
