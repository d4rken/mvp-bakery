package eu.darken.mvpbakery.example.screens


import androidx.fragment.app.Fragment
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test


class MainPresenterTest {
    @MockK
    lateinit var view: MainPresenter.View

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun testCount() {
        val mainPresenter = MainPresenter(Fragment::class.java)
        verify(exactly = 0) { view.showBinderCounter(any()) }
        mainPresenter.onBindChange(view)
        verify(exactly = 1) { view.showBinderCounter(any()) }
        mainPresenter.onBindChange(null)
        mainPresenter.onBindChange(view)
        verify(exactly = 2) { view.showBinderCounter(any()) }
    }
}
