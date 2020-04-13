package com.fave.breezil.fave.utils.helpers

import android.view.View

import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

class FadeOutTransformation : ViewPager.PageTransformer {
    override fun transformPage(page: View, position: Float) {
//        page.translationX = -position * page.width
//        page.alpha = 1 - abs(position)
        if(position <= -1.0F || position >= 1.0F) {
            page.translationX = page.width * position
            page.alpha = 0.0F
        } else if( position == 0.0F ) {
            page.translationX = page.width * position
            page.alpha = 1.0F
        } else {
            // position is between -1.0F & 0.0F OR 0.0F & 1.0F
            page.translationX = page.width * -position
            page.alpha = 1.0F - abs(position)
        }
    }
}