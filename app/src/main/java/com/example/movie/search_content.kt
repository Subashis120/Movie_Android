package com.example.movie

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.movie.activities.MainActivity
import com.example.movie.activities.getmovList
import com.example.movie.databinding.SearchBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search.*

class search_content:AppCompatActivity() {
    lateinit var binding: com.example.movie.databinding.SearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        close.isVisible=false

        icon.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }


        val movlis: ArrayList<String> = ArrayList()
        movlis.addAll(getmovList.keys)

        val adapter:ArrayAdapter<String> = ArrayAdapter(
            this,R.layout.listrow,R.id.text_view,
            movlis
        )

        binding.movlist.adapter=adapter

        movlist.setOnItemClickListener { parent, view, position, id ->
            //Log.d("index number","${getmovList.get(parent.getItemAtPosition(position))}")
            //Log.d("index name","${adapter.getItem(position)}")
            val intent = Intent(this,movie_description::class.java)
            intent.putExtra("movie_id",getmovList.get(parent.getItemAtPosition(position)))
            intent.putExtra("movie_name",adapter.getItem(position))
            startActivity(intent)
        }


        binding.search.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                close.isVisible=true
                Log.d("from main activity","text not changed")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {
                if (binding.search.length().equals(0)){
                    close.isVisible=false
                }

                Log.d("from main activity","text changed")
            }

        })

        close.setOnClickListener {
            binding.search.editableText.clear()
            icon.isVisible= true
            close.isVisible=false

        }
    }

}