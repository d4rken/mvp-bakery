package eu.darken.mvpbakery.example.screens

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import butterknife.ButterKnife
import eu.darken.mvpbakery.base.MVPBakery
import eu.darken.mvpbakery.base.StateForwarder
import eu.darken.mvpbakery.base.ViewModelRetainer
import eu.darken.mvpbakery.example.R
import eu.darken.mvpbakery.injection.ComponentSource
import eu.darken.mvpbakery.injection.InjectedPresenter
import eu.darken.mvpbakery.injection.ManualInjector
import eu.darken.mvpbakery.injection.PresenterInjectionCallback
import eu.darken.mvpbakery.injection.fragment.HasManualFragmentInjector
import java.util.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainPresenter.View, HasManualFragmentInjector {

    @Inject
    lateinit var componentSource: ComponentSource<Fragment>
    @Inject
    lateinit var presenter: MainPresenter
    @BindView(R.id.container)
    lateinit var viewPager: ViewPager
    @BindView(R.id.bindcounter)
    lateinit var bindCounter: TextView
    @BindView(R.id.pageinfo)
    lateinit var pageInfo: TextView

    private val stateForwarder = StateForwarder()
    private var pagerAdapter: PagerAdapter? = null

    override fun supportFragmentInjector(): ManualInjector<Fragment> = componentSource

    override fun onCreate(si: Bundle?) {
        super.onCreate(si)
        stateForwarder.onCreate(si)
        MVPBakery.builder<MainPresenter.View, MainPresenter>()
                .stateForwarder(stateForwarder)
                .presenterFactory(InjectedPresenter(this))
                .presenterRetainer(ViewModelRetainer(this))
                .addPresenterCallback(PresenterInjectionCallback(this))
                .attach(this)

        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        pagerAdapter = PagerAdapter(this, supportFragmentManager)
        viewPager.offscreenPageLimit = pagerAdapter!!.count
        viewPager.adapter = pagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

            override fun onPageSelected(position: Int) {
                val fragment = pagerAdapter!!.getItem(position)
                pageInfo.text = fragment.toString() + "\n"
                pageInfo.append((position + 1).toString() + "/" + pagerAdapter!!.count)
            }

            override fun onPageScrollStateChanged(state: Int) = Unit
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("pages", pagerAdapter!!.count)
        stateForwarder.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun addFragment(fragmentClass: Class<out Fragment>) {
        pagerAdapter!!.addPage(fragmentClass, true)
        viewPager.offscreenPageLimit = pagerAdapter!!.count
        viewPager.currentItem = pagerAdapter!!.count - 1
    }

    override fun showBinderCounter(count: Int) {
        bindCounter.text = String.format(Locale.US, "Activity Rotation Count: %d", count)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_fragment -> {
                presenter.addNewFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
