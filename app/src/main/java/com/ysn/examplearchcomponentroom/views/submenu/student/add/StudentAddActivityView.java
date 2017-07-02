package com.ysn.examplearchcomponentroom.views.submenu.student.add;

import com.ysn.examplearchcomponentroom.db.entity.Student;
import com.ysn.examplearchcomponentroom.views.base.View;

/**
 * Created by root on 02/07/17.
 */

interface StudentAddActivityView extends View {

    void saveDataInvalid(String message);

    void saveData(Student student);

}
