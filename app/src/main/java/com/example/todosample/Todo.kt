package com.example.todosample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "text") val text:String
)