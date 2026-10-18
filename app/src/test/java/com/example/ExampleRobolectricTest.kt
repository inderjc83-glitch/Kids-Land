package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.EducationalContentRegistry
import com.example.model.AgeGroup
import com.example.model.VocabCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read app name from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("KidsLand", appName)
    }

    @Test
    fun `verify educational content registry is fully populated`() {
        assertTrue("Colors should not be empty", EducationalContentRegistry.colorsList.isNotEmpty())
        assertTrue("Animals should not be empty", EducationalContentRegistry.animalsList.isNotEmpty())
        assertTrue("Birds should not be empty", EducationalContentRegistry.birdsList.isNotEmpty())
        assertEquals("Months should have 12 items", 12, EducationalContentRegistry.monthsList.size)
        assertEquals("Days of week should have 7 items", 7, EducationalContentRegistry.daysList.size)
        assertTrue("Alphabet tracing should have 26 letters", EducationalContentRegistry.alphabetTracingList.size == 26)
        assertTrue("Number tracing should have 10 numbers", EducationalContentRegistry.numberTracingList.size == 10)
        assertTrue("Animal drawings should be available", EducationalContentRegistry.animalDrawings.isNotEmpty())
        assertTrue("Stories should have comprehension quizzes", EducationalContentRegistry.storiesList.isNotEmpty())
    }

    @Test
    fun `verify age group tier configuration`() {
        val toddler = AgeGroup.TODDLER
        val preschool = AgeGroup.PRESCHOOL
        val primary = AgeGroup.PRIMARY

        assertEquals(10, toddler.countingMax)
        assertEquals(50, preschool.countingMax)
        assertEquals(100, primary.countingMax)
    }

    @Test
    fun `verify all animal names and items have phonetic hints and fun facts for tap-to-speak`() {
        EducationalContentRegistry.animalsList.forEach { animal ->
            assertTrue("Animal name should not be blank", animal.name.isNotBlank())
            assertTrue("Animal phoneticHint should not be blank", animal.phoneticHint.isNotBlank())
            assertTrue("Animal funFact should not be blank", animal.funFact.isNotBlank())
            assertTrue("Animal emoji should not be blank", animal.emoji.isNotBlank())
        }
    }

    @Test
    fun `verify TTSHelper initializes and cleans up safely`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val ttsHelper = com.example.audio.TTSHelper(context)
        ttsHelper.stop()
        ttsHelper.shutdown()
    }
}
