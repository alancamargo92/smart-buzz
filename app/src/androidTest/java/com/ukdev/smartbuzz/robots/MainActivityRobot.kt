package com.ukdev.smartbuzz.robots

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.ukdev.smartbuzz.R
import com.ukdev.smartbuzz.activities.MainActivity
import com.ukdev.smartbuzz.activities.SetupActivity
import com.ukdev.smartbuzz.adapters.AlarmHolder
import com.ukdev.smartbuzz.common.BaseActivityRobot
import com.ukdev.smartbuzz.misc.Extra
import org.junit.Rule

class MainActivityRobot : BaseActivityRobot() {

    @Rule
    @JvmField
    val rule = IntentsTestRule<MainActivity>(MainActivity::class.java,
                                              initialTouchMode, launchActivity)

    fun checkIfIntentHasAlarmId(alarmId: Int): MainActivityRobot {
        intended(hasExtra(Extra.ID, alarmId))
        return this
    }

    fun checkIfLeadsToSetupActivity(): MainActivityRobot {
        intended(IntentMatchers.hasComponent(SetupActivity::class.java.name))
        return this
    }

    fun clickOnAdd(): MainActivityRobot {
        onView(withId(R.id.fab_main)).perform(click())
        return this
    }

    fun clickOnRecyclerViewItemAtPosition(position: Int): MainActivityRobot {
        onView(withId(R.id.recycler_view_main)).perform(actionOnItemAtPosition<AlarmHolder>(position,
                                                                                            click()))
        return this
    }

    fun launchActivity(): MainActivityRobot {
        rule.launchActivity(Intent())
        return this
    }

}