package com.ysn.examplearchcomponentroom.views.submenu.student.edit;

import android.app.DatePickerDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ysn.examplearchcomponentroom.R;
import com.ysn.examplearchcomponentroom.db.entity.Student;
import com.ysn.examplearchcomponentroom.utils.ConverterDateLibrary;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentEditActivity extends AppCompatActivity implements StudentEditActivityView {

    private final String TAG = getClass().getSimpleName();
    private StudentEditActivityPresenter studentEditActivityPresenter;

    @BindView(R.id.text_input_edit_text_student_id_activity_student_edit)
    TextInputEditText textInputEditTextStudentIdActivityStudentEdit;
    @BindView(R.id.text_input_edit_text_student_name_activity_student_edit)
    TextInputEditText textInputEditTextStudentNameActivityStudentEdit;
    @BindView(R.id.text_input_edit_text_student_birthday_activity_student_edit)
    TextInputEditText textInputEditTextStudentBirthdayActivityStudentEdit;
    @BindView(R.id.radio_group_student_gender_activity_student_edit)
    RadioGroup radioGroupStudentGenderActivityStudentEdit;
    @BindView(R.id.app_compat_radio_button_male_activity_student_edit)
    AppCompatRadioButton appCompatRadioButtonMaleActivityStudentEdit;
    @BindView(R.id.app_compat_radio_button_female_activity_student_edit)
    AppCompatRadioButton appCompatRadioButtonFemaleActivityStudentEdit;
    @BindView(R.id.text_input_edit_text_student_address_activity_student_edit)
    TextInputEditText textInputEditTextStudentAddressActivityStudentEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_edit);
        ButterKnife.bind(this);
        initPresenter();
        EventBus.getDefault().register(this);
        setTitle("Edit Data Student");
    }

    private void initPresenter() {
        studentEditActivityPresenter = new StudentEditActivityPresenter();
    }

    @Subscribe(sticky = true)
    public void onMessageEventRetrieveData(Student student) {
        textInputEditTextStudentIdActivityStudentEdit.setText(student.getId());
        textInputEditTextStudentNameActivityStudentEdit.setText(student.getName());
        textInputEditTextStudentBirthdayActivityStudentEdit.setText(student.getBirthday());
        String gender = student.getGender();
        if (gender.equalsIgnoreCase("male")) {
            appCompatRadioButtonMaleActivityStudentEdit.setChecked(true);
        } else if (gender.equalsIgnoreCase("female")) {
            appCompatRadioButtonFemaleActivityStudentEdit.setChecked(true);
        }
        textInputEditTextStudentAddressActivityStudentEdit.setText(student.getAddress());
        Log.d(TAG, "onMessageEventRetrieveData");

    }

    @OnClick({
            R.id.button_save_activity_student_edit,
            R.id.text_input_edit_text_student_birthday_activity_student_edit
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_input_edit_text_student_birthday_activity_student_edit:
                // todo: show date picker dialog
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialogBirthday = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                textInputEditTextStudentBirthdayActivityStudentEdit.setText(
                                        new ConverterDateLibrary().formatToString(year, month, dayOfMonth)
                                );
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialogBirthday.show();
                break;
            case R.id.button_save_activity_student_edit:
                // todo: on update data student
                String studentId = textInputEditTextStudentIdActivityStudentEdit.getText().toString().trim();
                String studentName = textInputEditTextStudentNameActivityStudentEdit.getText().toString().trim();
                String studentBirthday = textInputEditTextStudentBirthdayActivityStudentEdit.getText().toString().trim();
                String studentGender = "";
                if (appCompatRadioButtonMaleActivityStudentEdit.isChecked()) {
                    studentGender = appCompatRadioButtonMaleActivityStudentEdit.getText().toString().trim();
                } else if (appCompatRadioButtonFemaleActivityStudentEdit.isChecked()) {
                    studentGender = appCompatRadioButtonFemaleActivityStudentEdit.getText().toString().trim();
                }
                String studentAddress = textInputEditTextStudentAddressActivityStudentEdit.getText().toString().trim();
                studentEditActivityPresenter.onUpdateData(this, studentId, studentName, studentBirthday,
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
        studentEditActivityPresenter.onAttach(this);
    }

    @Override
    public void onDetachView() {
        studentEditActivityPresenter.onDetach();
    }

    @Override
    public void invalidUpdateData(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void updateData(Student studentUpdate) {
        Toast.makeText(this, "Your data has been updated", Toast.LENGTH_LONG)
                .show();
        EventBus.getDefault().post(studentUpdate);
        finish();
    }
}
