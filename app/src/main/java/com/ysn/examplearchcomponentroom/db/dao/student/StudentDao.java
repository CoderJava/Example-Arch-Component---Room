package com.ysn.examplearchcomponentroom.db.dao.student;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ysn.examplearchcomponentroom.db.entity.Student;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by root on 01/07/17.
 */

@Dao
public interface StudentDao {

    @Query("SELECT * FROM student")
    Flowable<List<Student>> getAll();

    @Query("SELECT * FROM student WHERE id = :id")
    Flowable<Student> findStudentById(String id);

    @Update
    void updateStudent(Student... students);

    @Query("DELETE FROM student")
    void deleteAll();

    @Delete
    void deleteStudentById(Student... students);

    @Insert
    void insertStudent(Student... students);

}
