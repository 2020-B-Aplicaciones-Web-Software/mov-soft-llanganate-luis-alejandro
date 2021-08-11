package com.example.scrima.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scrima.R
import com.example.scrima.entities.Usuario
import com.example.scrima.general.RecyclerViewItem
import com.example.scrima.general.UserItem
import com.example.scrima.ui.HomeActivity


const val VIEW_TYPE_USER_DESCRIPTION = 1
const val VIEW_TYPE_USER_OPTIONS = 2



class PerfilUsuarioAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = listOf<RecyclerViewItem>()


    inner class UserHolder(view: View) : RecyclerView.ViewHolder(view){
        val nombreUsuarioTextView: TextView
        val correoUsuarioTextView: TextView
        init {
            nombreUsuarioTextView = view.findViewById(R.id.tv_user_name_profile)
            correoUsuarioTextView = view.findViewById(R.id.tv_user_email_profile)
        }
    }

    inner class OptionsHolder(view: View) : RecyclerView.ViewHolder(view){
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val idView : Int
        val itemView : View
        if (viewType == VIEW_TYPE_USER_DESCRIPTION) {
            idView = R.layout.profile_user_description
            itemView = LayoutInflater
                .from(parent.context)
                .inflate(
                    idView,
                    parent,
                    false,
                )
            return UserHolder(itemView)
        }else{
            idView = R.layout.profile_user_opciones
            itemView = LayoutInflater
                .from(parent.context)
                .inflate(
                    idView,
                    parent,
                    false,
                )
            return OptionsHolder(itemView)
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        if (holder is UserHolder && item is UserItem) {
            holder.nombreUsuarioTextView.text = item.user.name
            holder.correoUsuarioTextView.text = item.user.email
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        if (data[position] is UserItem) {
            return VIEW_TYPE_USER_DESCRIPTION
        }
        return VIEW_TYPE_USER_OPTIONS
    }

}
