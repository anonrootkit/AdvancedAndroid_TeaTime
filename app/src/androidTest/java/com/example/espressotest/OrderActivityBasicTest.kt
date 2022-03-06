package com.example.espressotest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OrderActivityBasicTest {

    @JvmField
    @Rule
    val activityOrderRule = ActivityScenarioRule(OrderActivity::class.java)

    @Test
    fun clickIncButton_changesQuantityAndCost() {
        onView(withId(R.id.quantity_text_view)).check(matches(withText("0")))
        onView(withId(R.id.increment_button)).perform(click())

        onView(withId(R.id.quantity_text_view)).check(matches(withText("1")))
        onView(withId(R.id.cost_text_view)).check(matches(withText("$5.00")))
    }

    @Test
    fun clickDecButton_changesQuantityAndCost() {
        onView(withId(R.id.quantity_text_view)).check(matches(withText("0")))
        onView(withId(R.id.decrement_button)).perform(click())

        onView(withId(R.id.quantity_text_view)).check(matches(withText("0")))
        onView(withId(R.id.cost_text_view)).check(matches(withText("$0.00")))
    }
}