package com.ysn.examplearchcomponentroom.views.submenu.student.edit;

import com.ysn.examplearchcomponentroom.db.entity.Student;
import com.ysn.examplearchcomponentroom.views.base.View;

/**
 * Created by root on 02/07/17.
 */

interface StudentEditActivityView extends View {

    void invalidUpdateData(String s);

    void updateData(Student studentUpdate);

}
