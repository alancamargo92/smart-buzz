package com.ukdev.smartbuzz.robots

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.ukdev.smartbuzz.R
import com.ukdev.smartbuzz.activities.MainActivity
import com.ukdev.smartbuzz.activities.SetupActivity
import org.junit.Rule

class SetupActivityRobot {

    companion object {
        val INITIAL_TOUCH_MODE = false
        val LAUNCH_ACTIVITY = false
    }

    @Rule
    @JvmField
    val rule = IntentsTestRule<SetupActivity>(SetupActivity::class.java, INITIAL_TOUCH_MODE,
                                              LAUNCH_ACTIVITY)

    fun launchActivity(): SetupActivityRobot {
        val context = InstrumentationRegistry.getTargetContext()
        val intent = SetupActivity.getIntent(context)
        rule.launchActivity(intent)
        return this
    }

    fun launchActivity(alarmId: Int): SetupActivityRobot {
        val context = InstrumentationRegistry.getTargetContext()
        val intent = SetupActivity.getIntent(context, alarmId)
        rule.launchActivity(intent)
        return this
    }

    fun clickOnSave(): SetupActivityRobot {
        onView(withId(R.id.fab_setup)).perform(click())
        return this
    }

    fun checkIfLeadsToMainActivity(): SetupActivityRobot {
        intended(hasComponent(MainActivity::class.java.name))
        return this
    }

}