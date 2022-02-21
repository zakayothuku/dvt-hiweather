/*
 * Copyright (C) 2020. dvt.co.za
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
package com.dvttest.hiweather.core.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * Set image loaded from url.
 *
 * @param url Image url to download and set as drawable.
 */
@BindingAdapter("imageUrl", requireAll = false)
fun ImageView.imageUrl(url: String?) {
    Glide.with(context)
        .load(url)
        .centerCrop()
        .into(this)
}

/**
 * Set image loaded from drawable resources.
 *
 * @param name Image name to load from drawable resources.
 */
@BindingAdapter("imageName", requireAll = false)
fun ImageView.imageName(name: String?) {
    Glide.with(context)
        .load(resources.getIdentifier(name, "drawable", context.packageName))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}
