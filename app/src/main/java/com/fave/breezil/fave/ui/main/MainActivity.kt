package com.fave.breezil.fave.ui.main

import android.content.Intent
import android.content.SharedPreferences
import androidx.databinding.DataBindingUtil
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View

import com.fave.breezil.fave.databinding.ActivityMainBinding
import com.fave.breezil.fave.R
import com.fave.breezil.fave.callbacks.FragmentClosedListener
import com.fave.breezil.fave.utils.Constant.Companion.ZERO
import com.fave.breezil.fave.utils.helpers.BottomNavigationHelper
import javax.inject.Inject
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import com.fave.breezil.fave.model.Sources
import com.fave.breezil.fave.ui.BaseActivity
import com.fave.breezil.fave.ui.adapter.PagerAdapter
import com.fave.breezil.fave.ui.main.bookmark.BookMarkedFragment
import com.fave.breezil.fave.ui.main.my_feeds.MyFeedsFragment
import com.fave.breezil.fave.ui.main.sources.SourceDetailFragment
import com.fave.breezil.fave.ui.main.sources.SourcesFragment
import com.fave.breezil.fave.ui.main.top_stories.CategoryArticlesFragment
import com.fave.breezil.fave.ui.main.top_stories.MainFragment
import com.fave.breezil.fave.ui.main.top_stories.SearchFragment
import com.fave.breezil.fave.ui.preference.AboutActivity
import com.fave.breezil.fave.ui.preference.SettingsActivity
import com.fave.breezil.fave.utils.helpers.FadeOutTransformation


class MainActivity : BaseActivity(), HasSupportFragmentInjector, FragmentClosedListener {


    lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences

    private var source: String = ""

    var toolbar: Toolbar? = null

    var mainViewPagerAdapter : PagerAdapter? = null

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainViewPagerAdapter = PagerAdapter(supportFragmentManager)

        setupBottomNavigation()

        toolbar = binding.root.findViewById(R.id.mainToolbar) as Toolbar
        setSupportActionBar(toolbar)


        val logging = HttpLoggingInterceptor { message -> Timber.tag(getString(R.string.okhttp)).d(message) }
        logging.redactHeader(getString(R.string.authorization))
        logging.redactHeader(getString(R.string.cookie))

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        editor.putString(getString(R.string.source), source)
        editor.apply()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    //Option menu selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if (item.itemId == R.id.preference) {
            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(intent)
        }
        if (item.itemId == R.id.search) {
            loadSearchFragment()
        }
        if(item.itemId == R.id.about){
            val intent = Intent(this@MainActivity, AboutActivity::class.java)
            startActivity(intent)
        }
        return true
    }

    private fun setupBottomNavigation() {

        mainViewPagerAdapter!!.addFragments( MainFragment())
        mainViewPagerAdapter!!.addFragments( MyFeedsFragment())
        mainViewPagerAdapter!!.addFragments( SourcesFragment())
        mainViewPagerAdapter!!.addFragments( BookMarkedFragment())
        binding.mainViewPager.adapter = mainViewPagerAdapter
        binding.mainViewPager.setPageTransformer(false, FadeOutTransformation())
        binding.mainViewPager.offscreenPageLimit = 3

        BottomNavigationHelper.disableShiftMode(binding.bottomNavViewBar)
        val menu = binding.bottomNavViewBar.menu
        val menuItem = menu.getItem(ZERO)
        menuItem.isChecked = true


        binding.bottomNavViewBar.setOnNavigationItemSelectedListener { item ->
            loadFragmentBottomNav(item)
             true
        }


    }


    private fun loadFragmentBottomNav(item: MenuItem) {
        when (item.itemId) {
            R.id.main -> {
                binding.mainViewPager.setCurrentItem(0, true)
                toolbar!!.title = toolbarTitle()
            }
            R.id.feeds -> {
                binding.mainViewPager.setCurrentItem(1, true)
                toolbar!!.title = toolbarTitle()
            }
            R.id.source ->{
                binding.mainViewPager.setCurrentItem(2, true)
                toolbar!!.title = toolbarTitle()
            }
            R.id.bookmark -> {
                binding.mainViewPager.setCurrentItem(3, true)
                toolbar!!.title = toolbarTitle()
            }
        }

    }




    fun loadCategoryFragment(category: String) {
        binding.fragmentContainer.visibility = View.VISIBLE
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar!!.title = category
        toolbar!!.setNavigationOnClickListener{
            onBackPressed()
        }
        val categoryArticlesFragment = CategoryArticlesFragment.getCategory(category)
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fragment_slide_in, R.anim.fragment_slide_out,
                        R.anim.fragment_pop_slide_in, R.anim.fragment_pop_slide_out)
                .replace(R.id.fragment_container, categoryArticlesFragment)
                .addToBackStack("fragment")
                .commit()
    }
    private fun loadSearchFragment() {
        binding.fragmentContainer.visibility = View.VISIBLE
        supportActionBar!!.hide()
        val searchFragment = SearchFragment()
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fragment_slide_in, R.anim.fragment_slide_out,
                        R.anim.fragment_pop_slide_in, R.anim.fragment_pop_slide_out)
                .replace(R.id.fragment_container, searchFragment)
                .addToBackStack("fragment")
                .commit()
    }

    fun loadSourcesDetailFragment(sources: Sources) {
        binding.fragmentContainer.visibility = View.VISIBLE
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar!!.title = sources.name
        toolbar!!.setNavigationOnClickListener{
            onBackPressed()
        }
        val sourceDetailFragment = SourceDetailFragment.getSource(sources)
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fragment_slide_in, R.anim.fragment_slide_out,
                        R.anim.fragment_pop_slide_in, R.anim.fragment_pop_slide_out)
                .replace(R.id.fragment_container, sourceDetailFragment)
                .addToBackStack("fragment")
                .commit()
    }


    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return dispatchingAndroidInjector
    }

    private fun toolbarTitle() : String{
        return when(binding.mainViewPager.currentItem){
            0 -> "Fave"
            1 -> "Feeds"
            2->"Sources"
            else -> "Bookmarks"
        }
    }
    override fun closeFragment() {
        binding.fragmentContainer.visibility = View.GONE
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.show()
        toolbar!!.title = toolbarTitle()
    }



}
