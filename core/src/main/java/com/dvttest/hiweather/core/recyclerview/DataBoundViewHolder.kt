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
package com.dvttest.hiweather.core.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * A generic ViewHolder that wraps a generated ViewDataBinding class.
 *
 * @param <T> The type of the ViewDataBinding class
</T> */
class DataBoundViewHolder<T : ViewDataBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        /**
         * Creates a new ViewHolder for the given layout file.
         *
         * The provided layout must be using data binding.
         *
         * @param parent The RecyclerView
         * @param layoutId The layout id that should be inflated. Must use data binding
         * @param <T>      The type of the Binding class that will be generated for the `layoutId`.
         * @return A new ViewHolder that has a reference to the binding class
         */
        fun <T : ViewDataBinding> create(parent: ViewGroup, @LayoutRes layoutId: Int): DataBoundViewHolder<T> {
            val binding = DataBindingUtil.inflate<T>(
                LayoutInflater.from(parent.context),
                layoutId, parent, false
            )
            return DataBoundViewHolder(binding)
        }
    }
}
