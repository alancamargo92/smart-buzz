package com.ukdev.smartbuzz.activities

import android.support.test.runner.AndroidJUnit4
import com.ukdev.smartbuzz.robots.SetupActivityRobot
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SetupActivityTest {

    private lateinit var robot: SetupActivityRobot

    @Before
    fun init() {
        robot = SetupActivityRobot()
    }

    @Test
    fun shouldLaunchMainActivityWhenClickingOnSave() {
        robot.launchActivity()
             .clickOnSave()
             .checkIfLeadsToMainActivity()
    }

}