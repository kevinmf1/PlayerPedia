package com.vinz.playerpedia.activity.login

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.vinz.playerpedia.activity.home.MainActivity
import org.junit.After
import org.junit.Test
import com.vinz.playerpedia.R

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {
    private lateinit var activityScenario: ActivityScenario<LoginActivity>

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

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
    fun loginAndLogout(){
        // Type email and password
        onView(withId(R.id.etEmail)).perform(typeText("user@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.etPassword)).perform(typeText("123456"), closeSoftKeyboard())

        // Click on the login button
        onView(withId(R.id.btnLogin)).perform(click())

        // redirect to HomePageActivity
        ActivityScenario.launch(MainActivity::class.java)

        // Verify that we have entered the MainActivity
        intended(hasComponent(MainActivity::class.java.name))

        // Click on the logout button
        onView(withId(R.id.btn_exit_app)).perform(click())

        // Click on the "yes" button in the logout dialog
        onView(withId(android.R.id.button1)).perform(click())

        // Verify that we have returned to the LoginActivity
        intended(hasComponent(LoginActivity::class.java.name))
    }
}