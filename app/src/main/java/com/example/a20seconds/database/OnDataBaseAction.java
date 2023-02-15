package com.example.a20seconds.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.a20seconds.model.Task;

import java.util.List;

@Dao
public interface OnDataBaseAction {

//    default void updateAnExistingRow(int taskId, String taskTitle, String taskDate, String taskTime,
//                                     String taskEvent) {
//
//    }

    @Query("SELECT * FROM Task")
    List<Task> getAllTasksList();

    @Query("DELETE FROM Task")
    void truncateTheList();

    @Insert
    void insertDataIntoTaskList(Task task);

    @Query("DELETE FROM Task WHERE taskId = :taskId")
    void deleteTaskFromId(int taskId);

    @Query("SELECT * FROM Task WHERE taskId = :taskId")
    Task selectDataFromAnId(int taskId);

    @Query("UPDATE Task SET taskTitle = :taskTitle, date = :taskDate, " +
            "lastAlarm = :taskTime, event = :taskEvent WHERE taskId = :taskId")
    void updateAnExistingRow(int taskId, String taskTitle, String taskDate, String taskTime, String taskEvent);
//    void updateAnExistingRow(int taskId, String taskTitle, String taskDate, String taskTime,
//                            String taskEvent);

}
