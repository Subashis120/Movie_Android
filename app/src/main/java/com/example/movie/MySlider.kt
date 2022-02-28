package com.example.movie

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.movie.MySlider.VH
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import java.util.*
var x = ""

class MySlider(): SliderViewAdapter<VH>() {

    private var mSliderItems = ArrayList<String>()
    fun renewItems(sliderItems: ArrayList<String>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun addItem(sliderItem: String) {
        mSliderItems.add(sliderItem)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return mSliderItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): VH {
        val inflate: View = LayoutInflater.from(parent?.context).inflate(R.layout.image_holder, null)
        return VH(inflate)
    }

    override fun onBindViewHolder(viewHolder: VH?, position: Int) {
        x= mSliderItems[position].split("static/")[1][0].toString()
        when(x.toInt()){
            1->viewHolder?.imageView?.setOnClickListener {
                val intent = Intent(viewHolder.imageView.context,movie_description::class.java)
                intent.putExtra("movie_id",2)
                intent.putExtra("movie_name","Spiderman: No Way Home")
                viewHolder.imageView.context.startActivity(intent)
            }
            2->viewHolder?.imageView?.setOnClickListener {
                val intent = Intent(viewHolder.imageView.context,movie_description::class.java)
                intent.putExtra("movie_id",13)
                intent.putExtra("movie_name","Avator")
                viewHolder.imageView.context.startActivity(intent)
            }
            3->viewHolder?.imageView?.setOnClickListener {
                val intent = Intent(viewHolder.imageView.context,movie_description::class.java)
                intent.putExtra("movie_id",12)
                intent.putExtra("movie_name","Don't Look Up")
                viewHolder.imageView.context.startActivity(intent)
            }
            4->viewHolder?.imageView?.setOnClickListener {
                val intent = Intent(viewHolder.imageView.context,movie_description::class.java)
                intent.putExtra("movie_id",5)
                intent.putExtra("movie_name","The Shawshank Redemption")
                viewHolder.imageView.context.startActivity(intent)
            }
            5->viewHolder?.imageView?.setOnClickListener {
                val intent = Intent(viewHolder.imageView.context,movie_description::class.java)
                intent.putExtra("movie_id",3)
                intent.putExtra("movie_name","Inception")
                viewHolder.imageView.context.startActivity(intent)
            }
            else->{
                Log.d("from slider","no images found")
            }
        }

        Picasso.get().load(mSliderItems[position]).fit().into(viewHolder?.imageView)
    }
    inner class VH(itemView: View) : ViewHolder(itemView){
        var imageView: ImageView = itemView.findViewById(R.id.imageSlider)
    }
}