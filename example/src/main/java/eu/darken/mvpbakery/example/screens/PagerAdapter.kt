package eu.darken.mvpbakery.example.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.util.*

/**
 * Created by darken(darken@darken.eu) on 29.04.2018.
 */
class PagerAdapter(private val context: Context, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val pages = ArrayList<Page>()

    override fun getItem(position: Int): Fragment = createFragment(position)

    private fun createFragment(position: Int): Fragment {
        val page = pages[position]
        val fragment = Fragment.instantiate(context, page.clazz.name)
        val bundle = Bundle()
        bundle.putString("identifier", page.identifier)
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int = pages.size

    fun addPage(clazz: Class<out Fragment>, notify: Boolean) {
        pages.add(Page(clazz, pages.size.toString()))
        if (notify) notifyDataSetChanged()
    }

    internal class Page(val clazz: Class<out Fragment>, val identifier: String)
}
