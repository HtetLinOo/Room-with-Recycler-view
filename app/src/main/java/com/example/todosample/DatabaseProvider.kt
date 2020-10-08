package com.example.todosample

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    private var database:TodoDatabase?=null

    fun instance(context: Context):TodoDatabase{
        if(database==null){
            database= Room.databaseBuilder(context,TodoDatabase::class.java,"todo.db").build()
        }
        return database!!
    }
}