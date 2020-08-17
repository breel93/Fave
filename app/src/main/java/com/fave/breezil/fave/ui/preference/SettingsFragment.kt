package com.fave.breezil.fave.ui.preference

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.fave.breezil.fave.R
import com.fave.breezil.fave.databinding.FragmentSettingsBinding
import com.fave.breezil.fave.ui.callbacks.FragmentOpenedListener
import dagger.android.support.DaggerFragment

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : DaggerFragment() {
  lateinit var binding: FragmentSettingsBinding
  private lateinit var openedListener: FragmentOpenedListener
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container,
      false)
    openedListener.isOpened(true)
    goBack()
    return binding.root
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    openedListener = try {
      requireActivity() as FragmentOpenedListener
    } catch (e: ClassCastException) {
      throw ClassCastException(
        context.toString()
            + " must implement FragmentOpenedListener "
      )
    }
  }
  private fun goBack(){
    binding.backPressed.setOnClickListener{
      requireActivity().supportFragmentManager.popBackStack()
    }
  }
  override fun onDestroy() {
    super.onDestroy()
    openedListener.isOpened(false)
  }
}
