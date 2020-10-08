package com.example.todosample

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface TodoDao{

    @Query("Select * from todo")
    fun selectAll():List<Todo>

    @Insert
    fun insert(todo: Todo)

    @Delete
    fun delete(todo: Todo)
}