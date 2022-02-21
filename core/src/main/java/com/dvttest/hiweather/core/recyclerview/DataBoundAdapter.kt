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

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.dvttest.hiweather.core.base.BaseDataBoundAdapter

/**
 * An Adapter implementation that works with a [DataBoundViewHolder].
 *
 * Although this version enforces a single item type, it can easily be extended to support multiple
 * view types.
 *
 * @param <T> The type of the binding class
</T> */
abstract class DataBoundAdapter<T : ViewDataBinding>
/**
 * Creates a DataBoundAdapter with the given item layout
 *
 * @param mLayoutId The layout to be used for items. It must use body binding.
 */
    (@param:LayoutRes @field:LayoutRes private val mLayoutId: Int) : BaseDataBoundAdapter<T>() {

    override fun getItemLayoutId(position: Int): Int {
        return mLayoutId
    }
}
