package com.android.mytaxi.app_utility

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.v7.widget.RecyclerView

class AnimUtils {

    companion object {

        fun animateRecyclerviewItem(holder: RecyclerView.ViewHolder, isGoesDown:Boolean){
            val goesDown = if (isGoesDown){
                200f
            } else{
                -200f
            }
            val animatorSet = AnimatorSet()
            val animatorTranslateY:ObjectAnimator = ObjectAnimator.ofFloat(holder.itemView, "translationY", goesDown, 0f)
            animatorTranslateY.duration = 1000

            animatorSet.playTogether(animatorTranslateY)
            animatorSet.start()

        }
    }
}