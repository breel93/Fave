package com.fave.breezil.fave.ui.adapter

import android.util.SparseArray
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private var fragmentList = SparseArray<Fragment>()
    private var mainFragmentList = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment {
        return mainFragmentList[position]
    }

    override fun getCount(): Int {
        return mainFragmentList.size
    }


    fun addFragments(fragment: Fragment){
        mainFragmentList.add(fragment)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        fragmentList.put(position, fragment)
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        fragmentList.remove(position)
        super.destroyItem(container, position, `object`)
    }




}
