package com.example.todosample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val tdil:ToDoItemListener
) : ListAdapter<Todo,RecyclerAdapter.ItemViewHolder>(
        object:DiffUtil.ItemCallback<Todo>() {
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
              return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem==newItem
            }

        }
    )
{

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val tv=itemView.findViewById<TextView>(R.id.tv)
        val del=itemView.findViewById<ImageView>(R.id.delete)
    }

    interface ToDoItemListener{
        fun onDelete(todo:Todo)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        val holder=ItemViewHolder(itemView)
        holder.del.setOnClickListener {
                val position=holder.adapterPosition
            if(position!=RecyclerView.NO_POSITION){
                val itemAtIndex=currentList.get(position)
                tdil.onDelete(itemAtIndex)
            }
        }

        return holder

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val itemAtIndex=currentList.get(position)
        holder.tv.text=itemAtIndex.text
    }

    override fun getItemCount(): Int {
        return currentList.size
    }


}