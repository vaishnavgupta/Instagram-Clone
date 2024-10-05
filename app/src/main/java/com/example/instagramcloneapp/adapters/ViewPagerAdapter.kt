package com.example.instagramcloneapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm:FragmentManager):FragmentPagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    val fragmentList= mutableListOf<Fragment>()
    val titleList= mutableListOf<String>()

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titleList[position]
    }

    //making a FUNCTION to add fragment and title to Fragment List and Fragment title
    fun addFragment(frag:Fragment,title:String){
        fragmentList.add(frag)
        titleList.add(title)
    }
}