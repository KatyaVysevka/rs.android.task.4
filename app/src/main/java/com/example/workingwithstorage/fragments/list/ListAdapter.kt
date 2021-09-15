package com.example.workingwithstorage.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.workingwithstorage.R
import com.example.workingwithstorage.databinding.ItemBinding
import com.example.workingwithstorage.model.Film

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var filmList = emptyList<Film>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemBinding.bind(itemView)
        fun bind(film: Film) = with(binding) {
            textTitle.text = film.title
            textCountry.text = film.country
            textYear.text = film.year.toString()

            itemViewFilm.setOnClickListener {
                val action =
                    ListFragmentDirections.actionListFragmentToUpdateFragment(film)//тут на всякий случай пометка, если ломается то это здесь.
                itemViewFilm.findNavController().navigate(action)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(filmList[position])
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    fun setData(film: List<Film>) {
        this.filmList = film
        notifyDataSetChanged()
    }
}


