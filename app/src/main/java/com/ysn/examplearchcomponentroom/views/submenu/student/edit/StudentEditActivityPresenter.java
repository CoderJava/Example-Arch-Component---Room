package com.ysn.examplearchcomponentroom.views.submenu.student.edit;

import android.content.Context;

import com.ysn.examplearchcomponentroom.db.AppDatabase;
import com.ysn.examplearchcomponentroom.db.entity.Student;
import com.ysn.examplearchcomponentroom.views.base.Presenter;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by root on 02/07/17.
 */

class StudentEditActivityPresenter implements Presenter<StudentEditActivityView> {

    private final String TAG = getClass().getSimpleName();
    private StudentEditActivityView studentEditActivityView;
    private Context context;

    @Override
    public void onAttach(StudentEditActivityView view) {
        studentEditActivityView = view;
    }

    @Override
    public void onDetach() {
        studentEditActivityView = null;
    }

    void onUpdateData(final Context context, String studentId,
                      String studentName, String studentBirthday, String studentGender,
                      String studentAddress) {
        this.context = context;
        if (studentId.isEmpty()) {
            studentEditActivityView.invalidUpdateData("Student ID is empty!");
        } else if (studentName.isEmpty()) {
            studentEditActivityView.invalidUpdateData("Student Name is empty!");
        } else if (studentBirthday.isEmpty()) {
            studentEditActivityView.invalidUpdateData("Student Birthday is empty!");
        } else if (studentGender.isEmpty()) {
            studentEditActivityView.invalidUpdateData("Choose Student Gender!");
        } else if (studentAddress.isEmpty()) {
            studentEditActivityView.invalidUpdateData("Student Address is empty!");
        } else {
            final Student studentUpdate = new Student(studentId, studentName, studentBirthday, studentGender, studentAddress);
            Single
                    .fromCallable(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            AppDatabase.getInstance(context).studentDao().updateStudent(studentUpdate);
                            return true;
                        }
                    })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(@NonNull Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                studentEditActivityView.updateData(studentUpdate);
                            }
                        }
                    });
        }
    }
}
