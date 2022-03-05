/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.espressotest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.espressotest.databinding.GridItemLayoutBinding
import com.example.espressotest.model.Tea

/**
 * TeaMenuAdapter is backed by an ArrayList of {@link Tea} objects which populate
 * the GridView in MenuActivity
 */

class TeaMenuAdapter(
    context: Context,
    data : ArrayList<Tea>
) : ArrayAdapter<Tea>(context, 0, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val tea = getItem(position) as Tea

        val itemBinding : GridItemLayoutBinding = if (convertView == null) {
            GridItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        }else{
            convertView.tag as GridItemLayoutBinding
        }

        itemBinding.image.setImageResource(tea.imageResourceId)
        itemBinding.teaGridName.text = tea.teaName
        itemBinding.root.tag = itemBinding

        return itemBinding.root
    }
}