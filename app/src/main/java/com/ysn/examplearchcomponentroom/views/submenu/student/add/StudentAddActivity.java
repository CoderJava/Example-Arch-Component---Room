package com.ysn.examplearchcomponentroom.views.submenu.student.add;

import android.app.DatePickerDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ysn.examplearchcomponentroom.R;
import com.ysn.examplearchcomponentroom.db.entity.Student;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentAddActivity extends AppCompatActivity implements StudentAddActivityView {

    private final String TAG = getClass().getSimpleName();
    private StudentAddActivityPresenter studentAddActivityPresenter;

    @BindView(R.id.text_input_edit_text_student_id_activity_student_add)
    TextInputEditText textInputEditTextStudentIdActivityStudentAdd;
    @BindView(R.id.text_input_edit_text_student_name_activity_student_add)
    TextInputEditText textInputEditTextStudentNameActivityStudentAdd;
    @BindView(R.id.text_input_edit_text_student_birthday_activity_student_add)
    TextInputEditText textInputEditTextStudentBirthdayActivityStudentAdd;
    @BindView(R.id.radio_group_student_gender_activity_student_add)
    RadioGroup radioGroupStudentGenderActivityStudentAdd;
    @BindView(R.id.app_compat_radio_button_male_activity_student_add)
    AppCompatRadioButton appCompatRadioButtonMaleActivityStudentAdd;
    @BindView(R.id.app_compat_radio_button_female_activity_student_add)
    AppCompatRadioButton appCompatRadioButtonFemaleActivityStudentAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add);
        ButterKnife.bind(this);
        initPresenter();
    }

    private void initPresenter() {
        studentAddActivityPresenter = new StudentAddActivityPresenter();
    }

    @OnClick({
            R.id.text_input_edit_text_student_birthday_activity_student_add,
            R.id.button_save_activity_student_add
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_input_edit_text_student_birthday_activity_student_add:
                // todo: show date picker dialog
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialogBirthday = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month,
                                                  int dayOfMonth) {

                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialogBirthday.show();
                break;
            case R.id.button_save_activity_student_add:
                // todo: on save data student
                String studentId = textInputEditTextStudentIdActivityStudentAdd.getText().toString().trim();
                String studentName = textInputEditTextStudentNameActivityStudentAdd.getText().toString().trim();
                String studentBirthday = textInputEditTextStudentBirthdayActivityStudentAdd.getText().toString().trim();
                String studentGender = "";
                if (appCompatRadioButtonMaleActivityStudentAdd.isChecked()) {
                    studentGender = appCompatRadioButtonMaleActivityStudentAdd.getText().toString().trim();
                } else if (appCompatRadioButtonFemaleActivityStudentAdd.isChecked()) {
                    studentGender = appCompatRadioButtonFemaleActivityStudentAdd.getText().toString().trim();
                }
                String studentAddress = textInputEditTextStudentBirthdayActivityStudentAdd.getText().toString().trim();
                studentAddActivityPresenter.onSaveData(this, studentId, studentName, studentBirthday,
                        studentGender, studentAddress);
                break;
        }
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
    public void onAttachView() {
        studentAddActivityPresenter.onAttach(this);
    }

    @Override
    public void onDetachView() {
        studentAddActivityPresenter.onDetach();
    }

    @Override
    public void saveData(Student student) {
        Map<String, Object> mapDataStudent = new HashMap<>();
        mapDataStudent.put("student", student);
        mapDataStudent.put("action", "insert");
        EventBus.getDefault().post(mapDataStudent);
        finish();
    }

    @Override
    public void saveDataInvalid(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
                .show();
    }
}
