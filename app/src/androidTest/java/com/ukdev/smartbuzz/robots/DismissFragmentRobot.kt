package com.ukdev.smartbuzz.robots

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.ukdev.smartbuzz.R
import com.ukdev.smartbuzz.activities.TestActivity
import com.ukdev.smartbuzz.fragments.DismissFragment
import org.junit.Assert.assertTrue
import org.junit.Rule

class DismissFragmentRobot {

    @Rule
    @JvmField
    val rule = FragmentTestRule(TestActivity::class.java, DismissFragment::class.java)

    fun launchFragment(): DismissFragmentRobot {
        rule.launchActivity(Intent())
        rule.launchFragment(fragment())
        return this
    }

    fun clickOnDismiss(): DismissFragmentRobot {
        dismissButton().perform(click())
        return this
    }

    fun checkIfActivityIsDestroyed(): DismissFragmentRobot {
        Thread.sleep(500)
        assertTrue(rule.fragment.activity!!.isDestroyed)
        return this
    }

    private fun fragment(): DismissFragment {
        val fragment = DismissFragment()
        fragment.setOnFragmentInflatedListener {
            fragment.setOnClickListener { fragment.activity!!.finish() }
        }
        return fragment
    }

    private fun dismissButton(): ViewInteraction = onView(withId(R.id.btDismiss))

}