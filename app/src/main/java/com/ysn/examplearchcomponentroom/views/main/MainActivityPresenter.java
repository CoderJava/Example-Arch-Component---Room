package com.ysn.examplearchcomponentroom.views.main;

import android.content.Context;

import com.ysn.examplearchcomponentroom.db.AppDatabase;
import com.ysn.examplearchcomponentroom.db.entity.Student;
import com.ysn.examplearchcomponentroom.views.base.Presenter;
import com.ysn.examplearchcomponentroom.views.main.adapter.AdapterDataStudentMainActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by root on 01/07/17.
 */

class MainActivityPresenter implements Presenter<MainActivityView> {

    private final String TAG = getClass().getSimpleName();
    private MainActivityView mainActivityView;
    private List<Student> listDataStudent;
    private AdapterDataStudentMainActivity adapterDataStudentMainActivity;
    private Context context;

    @Override
    public void onAttach(MainActivityView view) {
        mainActivityView = view;
    }

    @Override
    public void onDetach() {
        mainActivityView = null;
    }

    void onLoadData(Context context) {
        this.context = context;
        if (listDataStudent == null) {
            listDataStudent = new ArrayList<>();
        }
        AppDatabase.getInstance(context).studentDao()
                .getAll()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<Student>>() {
                            @Override
                            public void accept(@NonNull List<Student> students) throws Exception {
                                listDataStudent = students;
                                adapterDataStudentMainActivity = new AdapterDataStudentMainActivity(
                                        listDataStudent,
                                        new AdapterDataStudentMainActivity.ListenerAdapterDataStudentMainActivity() {
                                            @Override
                                            public void onClickDelete(Student student) {
                                                // todo: on click item button delete
                                            }

                                            @Override
                                            public void onClickEdit(Student student) {
                                                // todo: on click item button edit
                                                mainActivityView.clickEdit(student);
                                            }
                                        }
                                );
                                mainActivityView.loadData(adapterDataStudentMainActivity);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                throwable.printStackTrace();
                                mainActivityView.loadDataFailed();
                            }
                        }
                );
    }

    void onAddNewItemAdapter(Student student) {
        adapterDataStudentMainActivity.addNewItemAdapter(student);
        mainActivityView.addNewItemAdapter();
    }

    void onUpdateItemAdapter(int indexChanged, Student student) {
        adapterDataStudentMainActivity.updateItemAdapter(indexChanged, student);
        mainActivityView.updateItemAdapter();
    }

    void updateAdapterItem(Student student) {
        for (int a = 0; a < listDataStudent.size(); a++) {
            Student studentTemp = listDataStudent.get(a);
            if (studentTemp.getId().equals(student.getId())) {
                adapterDataStudentMainActivity.updateItemAdapter(a, student);
                break;
            }
        }
    }
}
