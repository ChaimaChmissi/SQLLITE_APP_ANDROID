package com.example.sqllite_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class UsersAdapter(private val dataSet: ArrayList<Student>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.student_item, viewGroup, false)
        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataSet[position]

        // Set the data to the TextViews in the ViewHolder
        holder.name.text = currentItem.nom
        holder.prenom.text = currentItem.prenom
        holder.email.text = currentItem.email
        holder.tel.text = currentItem.tel
        holder.login.text = currentItem.login
        holder.password.text = currentItem.password
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    inner class ViewHolder(view: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {
        val name: TextView = view.findViewById(R.id.nom)
        val email: TextView = view.findViewById(R.id.email)
        val prenom: TextView = view.findViewById(R.id.prenom)
        val tel: TextView = view.findViewById(R.id.tel)
        val login: TextView = view.findViewById(R.id.login)
        val password: TextView = view.findViewById(R.id.password)

        init {
            view.setOnClickListener(this)
            view.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.selectItem(position)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.longClickItem(position)
                return true
            }
            return false
        }
    }

    fun updateData(newDataSet: ArrayList<Student>) {
        dataSet.clear()
        dataSet.addAll(newDataSet)
        notifyDataSetChanged()
    }


    interface OnItemClickListener {
        fun selectItem(position: Int)
        fun longClickItem(position: Int)
    }
}

