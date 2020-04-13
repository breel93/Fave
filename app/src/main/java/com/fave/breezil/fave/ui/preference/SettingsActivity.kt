package com.fave.breezil.fave.ui.preference

import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.fave.breezil.fave.databinding.ActivitySettingsBinding
import com.fave.breezil.fave.R
import com.fave.breezil.fave.ui.BaseActivity
import com.fave.breezil.fave.utils.helpers.BottomNavigationHelper

import javax.inject.Inject

import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector

class SettingsActivity : BaseActivity(), HasSupportFragmentInjector {

    lateinit var binding: ActivitySettingsBinding
    var toolbar: Toolbar? = null

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<androidx.fragment.app.Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)

        toolbar = binding.root.findViewById(R.id.mainToolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Preference"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.aboutText.setOnClickListener { startActivity(Intent(this, AboutActivity::class.java)) }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if(item.itemId == android.R.id.home){
            onBackPressed()
            return true
        }
        return false
    }



    override fun supportFragmentInjector(): AndroidInjector<androidx.fragment.app.Fragment>? {
        return dispatchingAndroidInjector
    }
}
