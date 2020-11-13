package com.example.mentalhealth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//class RecyclerViewAdapter(var movieArray: Array<Movie>) :
//    RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
//        val viewItem =
//            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
//        return RecyclerViewHolder(viewItem, deleteLambda)
//    }
//
//    override fun getItemCount(): Int {
//        return movieArray.size
//    }
//
//    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
//        holder.bind(movieArray[position])
//    }
//
//    lateinit var deleteLambda: (Movie) -> Unit
//
//    class RecyclerViewHolder(val view: View, val deleteLambda: (Movie) -> Unit) :
//        RecyclerView.ViewHolder(view) {
//
//        //        val i=PosterLoader.getInstance()
//        fun bind(movie: Movie) {
//            
//            view.setOnClickListener{
//                deleteLambda(Movie())
//            }
//
//
//        }
//    }
//}