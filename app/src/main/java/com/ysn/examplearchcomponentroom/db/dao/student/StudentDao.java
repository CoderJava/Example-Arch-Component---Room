package com.ysn.examplearchcomponentroom.db.dao.student;

import android.arch.persistence.room.Query;

import com.ysn.examplearchcomponentroom.db.entity.Student;

import java.util.List;

/**
 * Created by root on 01/07/17.
 */

public interface StudentDao {

    @Query("SELECT * FROM student")
    List<Student> getAll();

    @Query("SELECT * FROM student WHERE id = :id")
    Student findStudentById(String id);

    @Query("DELETE FROM student")
    void deleteAll();

    @Query("DELETE FROM student WHERE id = :id")
    void deleteStudentById(String id);

    @Query("INSERT INTO student values (:id, :name, :birthday, :gender, :address)")
    void insertStudent(String id, String name, String birthday, String gender, String address);

}
