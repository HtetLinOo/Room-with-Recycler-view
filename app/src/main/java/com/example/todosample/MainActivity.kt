package com.example.todosample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity(), RecyclerAdapter.ToDoItemListener {

    private val adapter by lazy {
        RecyclerAdapter(this)
    }

    private val executor= Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv=findViewById<RecyclerView>(R.id.rv)
        rv.adapter=adapter
        rv.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        queryDatabase()

    }

    fun queryDatabase(){
        executor.execute {
            val database = DatabaseProvider.instance(this)
            val todoDao = database.tdDao()
            val tdList = todoDao.selectAll()
            runOnUiThread {
                adapter.submitList(tdList)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.actionAdd){
            Log.i("OK","Adding")
            val editText=EditText(this)
            editText.isSingleLine=true

            val dialog=AlertDialog.Builder(this)
                .setTitle("Type Your New ToDo")
                .setView(editText)
                .setPositiveButton("OK"){dialog,_ ->
                    val toDoText=editText.text.toString()
                    if(toDoText.isNotEmpty()){
                        val td=Todo(
                            id=0,
                            text=toDoText
                        )
                        executor.execute {
                            DatabaseProvider.instance(this).tdDao().insert(td)
                            queryDatabase()
                        }
                    }
                    dialog.dismiss()
                }.create()
            dialog.show()

            return  true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDelete(todo: Todo) {
        executor.execute {
            DatabaseProvider.instance(this).tdDao().delete(todo)
            queryDatabase()
        }
    }
}