package com.fave.breezil.fave.utils.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import android.view.MotionEvent



class NonSwipeableViewPager (context: Context, attrs: AttributeSet?): ViewPager (context, attrs) {
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean = false
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean = false
}