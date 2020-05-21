package com.fave.breezil.fave.ui.preference

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.fave.breezil.fave.R
import com.fave.breezil.fave.databinding.FragmentSettingsBinding

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment() {
  lateinit var binding: FragmentSettingsBinding
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
    goBack()
    return binding.root
  }
  fun goBack(){
    binding.backPressed.setOnClickListener{
      fragmentManager!!.popBackStack();
    }
  }
}
