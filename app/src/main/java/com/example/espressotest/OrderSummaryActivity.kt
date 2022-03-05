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
@file:Suppress("UNUSED_PARAMETER")

package com.example.espressotest


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.espressotest.databinding.ActivityOrderSummaryBinding
import java.text.NumberFormat

class OrderSummaryActivity : AppCompatActivity() {
    
    private lateinit var binding : ActivityOrderSummaryBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    
        binding = ActivityOrderSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.orderSummaryToolbar)
        supportActionBar?.title = getString(R.string.order_summary_title)

        displayOrderSummary()
    }

    private fun displayOrderSummary() {
        val teaName = intent.getStringExtra(OrderActivity.EXTRA_TEA_NAME)
        val price = intent.getIntExtra(OrderActivity.EXTRA_TOTAL_PRICE, 0)
        val size = intent.getStringExtra(OrderActivity.EXTRA_SIZE)
        val milkType = intent.getStringExtra(OrderActivity.EXTRA_MILK_TYPE)
        val sugarType = intent.getStringExtra(OrderActivity.EXTRA_SUGAR_TYPE)
        val quantity = intent.getIntExtra(OrderActivity.EXTRA_QUANTITY, 0)


        val convertedPrice = NumberFormat.getCurrencyInstance().format(price)
        
        binding.summaryTeaName.text = teaName
        binding.summaryTotalPrice.text = convertedPrice
        binding.summaryTeaSize.text = size
        binding.summaryMilkType.text = milkType
        binding.summarySugarAmount.text = sugarType
        binding.summaryQuantity.text = quantity.toString()
    }

    fun sendEmail(view : View) {

        val emailMessage = getString(R.string.email_message)

        Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.order_summary_email_subject))
            putExtra(Intent.EXTRA_TEXT, emailMessage)

            if (packageManager.resolveActivity(this, 0) != null) {
                startActivity(this)
            }else{
                Toast.makeText(this@OrderSummaryActivity, "No app found for mailing", Toast.LENGTH_SHORT).show()
            }
        }
        
    }
}