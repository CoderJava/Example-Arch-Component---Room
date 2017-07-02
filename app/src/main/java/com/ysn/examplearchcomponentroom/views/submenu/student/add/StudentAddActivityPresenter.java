package com.ysn.examplearchcomponentroom.views.submenu.student.add;

import android.content.Context;
import android.text.TextUtils;

import com.ysn.examplearchcomponentroom.db.AppDatabase;
import com.ysn.examplearchcomponentroom.db.entity.Student;
import com.ysn.examplearchcomponentroom.views.base.Presenter;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.operators.flowable.FlowableFromCallable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by root on 02/07/17.
 */

class StudentAddActivityPresenter implements Presenter<StudentAddActivityView> {

    private final String TAG = getClass().getSimpleName();
    private StudentAddActivityView studentAddActivityView;

    @Override
    public void onAttach(StudentAddActivityView view) {
        studentAddActivityView = view;
    }

    @Override
    public void onDetach() {
        studentAddActivityView = null;
    }

    void onSaveData(final Context context, String studentId, String studentName, String studentBirthday,
                    String studentGender, String studentAddress) {
        if (TextUtils.isEmpty(studentId)) {
            studentAddActivityView.saveDataInvalid("Student ID is empty!");
        } else if (TextUtils.isEmpty(studentName)) {
            studentAddActivityView.saveDataInvalid("Student Name is empty!");
        } else if (TextUtils.isEmpty(studentBirthday)) {
            studentAddActivityView.saveDataInvalid("Student Birthday is empty!");
        } else if (TextUtils.isEmpty(studentGender)) {
            studentAddActivityView.saveDataInvalid("Choose your gender!");
        } else if (TextUtils.isEmpty(studentAddress)) {
            studentAddActivityView.saveDataInvalid("Student Address is empty!");
        } else {
            // Insert it to Database
            final Student student = new Student(studentId, studentName, studentBirthday, studentGender, studentAddress);
            Single
                    .fromCallable(new Callable<Boolean>() {
                                      @Override
                                      public Boolean call() throws Exception {
                                          AppDatabase.getInstance(context)
                                                  .studentDao()
                                                  .insertStudent(student);
                                          studentAddActivityView.saveData(student);
                                          return true;
                                      }
                                  }
                    )
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }
    }
}
