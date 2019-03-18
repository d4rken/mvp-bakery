package eu.darken.mvpbakery.example.screens

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TestTest {
    @Test
    fun testLoad() {
        assertThat(true, `is`(true))
    }
}
