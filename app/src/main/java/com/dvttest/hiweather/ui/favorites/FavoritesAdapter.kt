/*
 * Copyright (C) 2022. dvt.co.za
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dvttest.hiweather.ui.favorites

import android.annotation.SuppressLint
import com.dvttest.hiweather.R
import com.dvttest.hiweather.core.recyclerview.DataBoundAdapter
import com.dvttest.hiweather.core.recyclerview.DataBoundViewHolder
import com.dvttest.hiweather.core.recyclerview.RecyclerViewClickListener
import com.dvttest.hiweather.data.db.entities.Favorite
import com.dvttest.hiweather.databinding.ItemFavoriteBinding

class FavoritesAdapter(
    private val clickListener: RecyclerViewClickListener
) : DataBoundAdapter<ItemFavoriteBinding>(R.layout.item_favorite) {

    private var mFavorites = arrayListOf<Favorite>()

    override fun getItemCount(): Int = mFavorites.size

    override fun bindItem(holder: DataBoundViewHolder<ItemFavoriteBinding>?, position: Int, payloads: MutableList<Any>?) {
        val binding = holder?.binding!!
        val favorite = mFavorites[position]
        binding.favorite = favorite
        binding.root.setOnClickListener {
            clickListener.onClick(favorite)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFavorites(favorites: ArrayList<Favorite>) {
        mFavorites = favorites
        notifyDataSetChanged()
    }

    fun updateFavoriteWeather(index: Int, updatedFavorite: Favorite) {
        mFavorites[index] = updatedFavorite
        notifyItemChanged(index)
    }
}
