package com.example.memories_atlas

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class EspressoTestScenario1 {

    @Test
    fun openMainActivityTest() {
        ActivityScenario.launch(StarterActivity::class.java)

        for (i in 1..10) {
            onView(withId(R.id.browse_button)).perform(click())
            onView(withId(R.id.mainLayout)).check(matches(isDisplayed()))
            pressBack()
        }
    }

    @Test
    fun sweepListTest() {
        ActivityScenario.launch(StarterActivity::class.java)

        onView(withId(R.id.browse_button)).perform(click())
        for (i in 1..100) {
            swipeDown()
            swipeUp()
        }
    }

    @Test
    fun addCollectionTest() {
        ActivityScenario.launch(StarterActivity::class.java)

        onView(withId(R.id.browse_button)).perform(click())
        onView(withId(R.id.addSetButton)).perform(click())

        onView(withId(R.id.setName)).perform(click())
        typeText("grupa testowa")

        onView(withId(R.id.placeName)).perform(click())
        typeText("landmark 1")

        onView(withId(R.id.description)).perform(click())
        typeText("description 1")

        onView(withId(R.id.set_confirm_changes)).perform(click())


    }

    @Test
    fun selectCollectionTest() {
        ActivityScenario.launch(StarterActivity::class.java)

        onView(withId(R.id.browse_button)).perform(click())

        for (i in 1..10) {
            onView(withId(R.id.setsRecyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            );
        }
    }
}
