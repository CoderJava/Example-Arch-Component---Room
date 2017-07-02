package com.ysn.examplearchcomponentroom.views.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ysn.examplearchcomponentroom.R;
import com.ysn.examplearchcomponentroom.db.entity.Student;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by root on 02/07/17.
 */

public class AdapterDataStudentMainActivity
        extends RecyclerView.Adapter<AdapterDataStudentMainActivity.ViewHolderItemDataStudent> {

    private final String TAG = getClass().getSimpleName();
    private List<Student> listDataStudent;
    private ListenerAdapterDataStudentMainActivity listenerAdapterDataStudentMainActivity;

    public AdapterDataStudentMainActivity(List<Student> listDataStudent,
                                          ListenerAdapterDataStudentMainActivity listenerAdapterDataStudentMainActivity) {
        this.listDataStudent = listDataStudent;
        this.listenerAdapterDataStudentMainActivity = listenerAdapterDataStudentMainActivity;
    }

    @Override
    public ViewHolderItemDataStudent onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_student, null);
        return new ViewHolderItemDataStudent(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderItemDataStudent holder, int position) {
        Student student = listDataStudent.get(position);
        holder.textViewStudentIdItemDataStudent.setText(student.getId());
        holder.textViewStudentNameItemDataStudent.setText(student.getName());
        holder.textViewStudentAddressItemDataStudent.setText(student.getAddress());
    }

    @Override
    public int getItemCount() {
        return listDataStudent.size();
    }

    public void addNewItemAdapter(Student student) {
        this.listDataStudent.add(student);
        notifyItemRangeInserted(0, this.listDataStudent.size());
    }

    public void updateItemAdapter(int indexChanged, Student student) {
        this.listDataStudent.add(indexChanged, student);
        notifyItemChanged(indexChanged);
    }

    class ViewHolderItemDataStudent extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view_student_id_item_data_student)
        TextView textViewStudentIdItemDataStudent;
        @BindView(R.id.text_view_student_name_item_data_student)
        TextView textViewStudentNameItemDataStudent;
        @BindView(R.id.text_view_student_address_item_data_student)
        TextView textViewStudentAddressItemDataStudent;

        public ViewHolderItemDataStudent(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({
                R.id.button_delete_student_item_data_student,
                R.id.button_edit_student_item_data_student
        })
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_delete_student_item_data_student:
                    // todo: button delete student item data student clicked
                    listenerAdapterDataStudentMainActivity.
                            onClickDelete(listDataStudent.get(getAdapterPosition()));
                    break;
                case R.id.button_edit_student_item_data_student:
                    // todo: button edit student item data student clicked
                    listenerAdapterDataStudentMainActivity
                            .onClickEdit(listDataStudent.get(getAdapterPosition()));
                    break;
            }
        }
    }

    public interface ListenerAdapterDataStudentMainActivity {

        void onClickDelete(Student student);

        void onClickEdit(Student student);

    }

}
