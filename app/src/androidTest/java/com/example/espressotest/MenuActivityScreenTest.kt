@file:Suppress("PrivatePropertyName")

package com.example.espressotest

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.anything
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MenuActivityScreenTest {

    @JvmField
    @Rule val activityRule = ActivityScenarioRule(MenuActivity::class.java)

    private val TEA_NAME = "Oolong Tea"

    @Test
    fun clickGridViewItem_OpensOrderActivity(){

        // Find the tea item in the list with tea name : Oolong Tea
        onData(anything())
            .inAdapterView(withId(R.id.tea_grid_view))
            .atPosition(3)
            .perform(click())

        onView(withId(R.id.tea_name_text_view)).check(matches(withText(TEA_NAME)))
    }

}