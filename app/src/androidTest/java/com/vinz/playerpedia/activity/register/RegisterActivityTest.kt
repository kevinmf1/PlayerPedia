package com.vinz.playerpedia.activity.register

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vinz.playerpedia.R
import com.vinz.playerpedia.activity.home.MainActivity
import com.vinz.playerpedia.activity.login.LoginActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RegisterActivityTest {
    private lateinit var activityScenario: ActivityScenario<RegisterActivity>

    @get:Rule
    val activityRule = ActivityScenarioRule(RegisterActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
        activityScenario = activityRule.scenario
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun registerAndLogout(){
        // Type name
        onView(ViewMatchers.withId(R.id.et_name))
            .perform(ViewActions.typeText("kevin"), closeSoftKeyboard())

        // Type email
        onView(ViewMatchers.withId(R.id.et_email))
            .perform(ViewActions.typeText("user2@gmail.com"), closeSoftKeyboard())

        // Type email
        onView(ViewMatchers.withId(R.id.et_username))
            .perform(ViewActions.typeText("user234"), closeSoftKeyboard())

        // Type password
        onView(ViewMatchers.withId(R.id.et_password))
            .perform(ViewActions.typeText("123456"), closeSoftKeyboard())

        // Type password confirmation
        onView(ViewMatchers.withId(R.id.et_retype_password))
            .perform(ViewActions.typeText("123456"), closeSoftKeyboard())

        // Type phone number
        onView(ViewMatchers.withId(R.id.et_phone_number))
            .perform(ViewActions.typeText("0812345678"), closeSoftKeyboard())

        // Click on the login button
        onView(ViewMatchers.withId(R.id.btn_register)).perform(ViewActions.click())

        // Verify that we have entered the MainActivity
        Intents.intended(IntentMatchers.hasComponent(MainActivity::class.java.name))

        // Click on the logout button
        onView(ViewMatchers.withId(R.id.btn_exit_app)).perform(ViewActions.click())

        // Click on the "yes" button in the logout dialog
        onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())

        // Verify that we have returned to the LoginActivity
        Intents.intended(IntentMatchers.hasComponent(LoginActivity::class.java.name))
    }
}