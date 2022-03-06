/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License")
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

@file:Suppress("unused", "PrivatePropertyName", "UNUSED_PARAMETER")

package com.example.espressotest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.espressotest.databinding.ActivityOrderBinding
import java.text.NumberFormat


class OrderActivity : AppCompatActivity() {
    private var mQuantity = 0
    private var mTotalPrice = 0

    private val SMALL_PRICE = 5
    private val MEDIUM_PRICE = 6
    private val LARGE_PRICE = 7

    private val TEA_SIZE_SMALL = "Small ($5/cup)"
    private val TEA_SIZE_MEDIUM = "Medium ($6/cup)"
    private val TEA_SIZE_LARGE = "Large ($7/cup)"

    private var mMilkType: String = ""
    private var mSugarType: String = ""
    private var mTeaName: String = ""

    private lateinit var mSize: String

    companion object {
        const val EXTRA_TOTAL_PRICE = "com.example.android.teatime.EXTRA_TOTAL_PRICE"
        const val EXTRA_TEA_NAME = "com.example.android.teatime.EXTRA_TEA_NAME"
        const val EXTRA_SIZE = "com.example.android.teatime.EXTRA_SIZE"
        const val EXTRA_MILK_TYPE = "com.example.android.teatime.EXTRA_MILK_TYPE"
        const val EXTRA_SUGAR_TYPE = "com.example.android.teatime.EXTRA_SUGAR_TYPE"
        const val EXTRA_QUANTITY = "com.example.android.teatime.EXTRA_QUANTITY"
    }

    private lateinit var binding: ActivityOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.orderToolbar)
        supportActionBar?.title = getString(R.string.order_title)

        mTeaName = intent?.getStringExtra(EXTRA_TEA_NAME) ?: ""

        binding.teaNameTextView.text = mTeaName
        binding.costTextView.text = getString(R.string.initial_cost)

        binding.brewTeaButton.setOnClickListener { brewTea() }

        setupSizeSpinner()
        setupMilkSpinner()
        setupSugarSpinner()
    }

    /**
     * Sets up the dropdown spinner for user to select tea mSize
     */
    private fun setupSizeSpinner() {

        // Create an ArrayAdapter using the string array and a default mSizeSpinner layout
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.tea_size_array, android.R.layout.simple_spinner_item
        )

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the mSizeSpinner
        binding.teaSizeSpinner.adapter = adapter

        binding.teaSizeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    when (p2) {
                        0 -> mSize = getString(R.string.tea_size_small)
                        1 -> mSize = getString(R.string.tea_size_medium)
                        2 -> mSize = getString(R.string.tea_size_large)
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    mSize = getString(R.string.tea_size_small)
                }

            }
    }

    /**
     * Sets up the dropdown spinner for user to select milk type
     */
    private fun setupMilkSpinner() {

        // Create an ArrayAdapter using the string array and a default mSizeSpinner layout
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.milk_array, android.R.layout.simple_spinner_item
        )
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the mSizeSpinner
        binding.milkSpinner.adapter = adapter

        binding.milkSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2) {
                    0 -> mMilkType = getString(R.string.milk_type_none)
                    1 -> mMilkType = getString(R.string.milk_type_nonfat)
                    2 -> mMilkType = getString(R.string.milk_type_1_percent)
                    3 -> mMilkType = getString(R.string.milk_type_2_percent)
                    4 -> mMilkType = getString(R.string.milk_type_whole)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                mMilkType = getString(R.string.milk_type_none)
            }

        }
    }


    /**
     * Setup the dropdown spinner for user to select amount of sugar
     */
    private fun setupSugarSpinner() {

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.sugar_array, android.R.layout.simple_spinner_item
        )

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the mSizeSpinner
        binding.sugarSpinner.adapter = adapter

        binding.sugarSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2) {
                    0 -> mSugarType = getString(R.string.sweet_type_0)
                    1 -> mSugarType = getString(R.string.sweet_type_25)
                    2 -> mSugarType = getString(R.string.sweet_type_50)
                    3 -> mSugarType = getString(R.string.sweet_type_75)
                    4 -> mSugarType = getString(R.string.sweet_type_100)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                mSugarType = getString(R.string.sweet_type_100)
            }

        }
    }

    /**
     * Increments the quantity and recalculates the price
     */
    fun increment(view: View) {
        mQuantity += 1
        displayQuantity(mQuantity)
        mTotalPrice = calculatePrice()
        displayCost(mTotalPrice)
    }

    /**
     * Decrements the quantity and recalculates the price
     */
    fun decrement(view: View) {
        if (mQuantity > 0) {
            mQuantity -= 1
            displayQuantity(mQuantity)
            mTotalPrice = calculatePrice()
            displayCost(mTotalPrice)
        }
    }


    /**
     * Calculates the TotalPrice of the order.
     *
     * @return total mTotalPrice
     */
    private fun calculatePrice(): Int {

        // Calculate the total order mTotalPrice by multiplying by the mQuantity
        when (mSize) {
            TEA_SIZE_SMALL -> mTotalPrice = mQuantity * SMALL_PRICE
            TEA_SIZE_MEDIUM -> mTotalPrice = mQuantity * MEDIUM_PRICE
            TEA_SIZE_LARGE -> mTotalPrice = mQuantity * LARGE_PRICE

        }
        return mTotalPrice
    }

    /**
     * Displays the given mQuantity value on the screen then
     * calculates and displays the cost
     */
    private fun displayQuantity(numberOfTeas: Int) {
        binding.quantityTextView.text = "$numberOfTeas"
    }

    private fun displayCost(totalPrice: Int) {
        val convertPrice = NumberFormat.getCurrencyInstance().format(totalPrice)
        binding.costTextView.text = convertPrice
    }

    /**
     * This method is called when the "Brew Tea" button is clicked
     * and a new intent opens the the {@link com.example.android.teatime.OrderSummaryActivity}
     */
    private fun brewTea() {
        startActivity(Intent(this, OrderSummaryActivity::class.java).apply {
            putExtra(EXTRA_TOTAL_PRICE, mTotalPrice)
            putExtra(EXTRA_TEA_NAME, mTeaName)
            putExtra(EXTRA_SIZE, mSize)
            putExtra(EXTRA_MILK_TYPE, mMilkType)
            putExtra(EXTRA_SUGAR_TYPE, mSugarType)
            putExtra(EXTRA_QUANTITY, mQuantity)
        })
    }
}