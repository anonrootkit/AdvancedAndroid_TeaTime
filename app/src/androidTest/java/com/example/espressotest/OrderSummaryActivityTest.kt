@file:Suppress("PrivatePropertyName")

package com.example.espressotest

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OrderSummaryActivityTest {

    private val MESSAGE = "I just ordered a delicious tea from TeaTime. Next time you are craving a tea, check them out!"

    @JvmField
    @Rule
    val activityScenarioRule =
        ActivityScenarioRule(OrderSummaryActivity::class.java)

    @Test
    fun clickSendEmail_checkMailIntentWorking() {
        onView(withId(R.id.send_email_button)).perform(click())

        intended(allOf(
            hasAction(Intent.ACTION_SENDTO),
            hasExtra(Intent.EXTRA_TEXT, MESSAGE)
        ))
    }

    @Before
    fun stubIntents(){
        Intents.init()
        intending(not(isInternal())).respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))
    }

    @After
    fun releaseIntents(){
        Intents.release()
    }
}