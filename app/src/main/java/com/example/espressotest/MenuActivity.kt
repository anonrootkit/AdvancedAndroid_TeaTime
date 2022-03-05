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

@file:Suppress("PrivatePropertyName")
package com.example.espressotest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.espressotest.databinding.ActivityMenuBinding
import com.example.espressotest.model.Tea


class MenuActivity : AppCompatActivity() {
    private val EXTRA_TEA_NAME = "com.example.android.teatime.EXTRA_TEA_NAME"
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.menuToolbar)
        supportActionBar?.title = getString(R.string.menu_title)

        val teas = ArrayList<Tea>()
        teas.add(Tea(getString(R.string.black_tea_name), R.drawable.black_tea))
        teas.add(Tea(getString(R.string.green_tea_name), R.drawable.green_tea))
        teas.add(Tea(getString(R.string.white_tea_name), R.drawable.white_tea))
        teas.add(Tea(getString(R.string.oolong_tea_name), R.drawable.oolong_tea))
        teas.add(Tea(getString(R.string.honey_lemon_tea_name), R.drawable.honey_lemon_tea))
        teas.add(Tea(getString(R.string.chamomile_tea_name), R.drawable.chamomile_tea))

        val adapter = TeaMenuAdapter(this, teas)
        binding.teaGridView.adapter = adapter

        binding.teaGridView.setOnItemClickListener { adapterView, _, pos, _ ->
            val tea = adapterView.getItemAtPosition(pos) as Tea
            startActivity(Intent(this, OrderActivity::class.java).apply {
                putExtra(EXTRA_TEA_NAME, tea.teaName)
            })
        }
    }

}
