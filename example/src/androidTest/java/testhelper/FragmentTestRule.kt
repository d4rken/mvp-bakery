package testhelper

import androidx.fragment.app.Fragment
import androidx.test.rule.ActivityTestRule
import eu.darken.mvpbakery.example.FragmentTestActivity
import eu.darken.mvpbakery.injection.ManualInjector
import junit.framework.Assert

class FragmentTestRule<FragmentT : Fragment>(private val fragmentClass: Class<FragmentT>) : ActivityTestRule<FragmentTestActivity>(FragmentTestActivity::class.java, true, false) {
    lateinit var fragment: FragmentT
    lateinit var manualInjector: ManualInjector<Fragment>

    override fun afterActivityLaunched() {
        super.afterActivityLaunched()
        activity.setManualInjector(manualInjector)

        activity.runOnUiThread {
            try {
                //Instantiate and insert the fragment into the container layout
                val manager = activity.supportFragmentManager
                val transaction = manager.beginTransaction()
                fragment = fragmentClass.newInstance()

                transaction.replace(1, fragment)
                transaction.commitNow()
            } catch (e: InstantiationException) {
                Assert.fail(
                        String.format("%s: Could not insert %s into TestActivity: %s",
                                javaClass.simpleName,
                                fragmentClass.simpleName,
                                e.message)
                )
            } catch (e: IllegalAccessException) {
                Assert.fail(String.format("%s: Could not insert %s into TestActivity: %s", javaClass.simpleName, fragmentClass.simpleName, e.message))
            }
        }
    }
}