package com.example.memories_atlas

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep


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
    fun selectCollectionTest() {
        ActivityScenario.launch(StarterActivity::class.java)

        onView(withId(R.id.browse_button)).perform(click())

        for (i in 1..5) {
            onView(withId(R.id.setsRecyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    i,
                    click()
                )
            )
            sleep(1500)
            pressBack()
        }
    }

    @Test
    fun addCollectionTest() {
        ActivityScenario.launch(StarterActivity::class.java)

        onView(withId(R.id.browse_button)).perform(click())
        onView(withId(R.id.addSetButton)).check(matches(isDisplayed()))
        onView(withId(R.id.addSetButton)).perform(click())

        onView(withId(R.id.setName)).perform(typeText("testing 1"), closeSoftKeyboard())

        onView(withId(R.id.placeName)).perform(typeText("landmark 1"), closeSoftKeyboard())

        onView(withId(R.id.description)).perform(typeText("description 1"), closeSoftKeyboard())

        onView(withId(R.id.set_confirm_changes)).perform(click())

        onView(withId(R.id.setsRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
    }
}
