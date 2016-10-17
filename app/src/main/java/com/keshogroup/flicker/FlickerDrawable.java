package com.keshogroup.flicker;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.Gravity;

/**
 * Created by ckesho on 10/13/2016.
 * FlickerDrawable takes a drawable resource and applies a
 * flicker animation when the public flick() method is called
 * on the FlickerDrawable.
 * Helpful Suggestions:
 * Works best on images of words
 * Works best on solid images and background colors
 * Performs poorly on pattern (non solid) backgrounds
 *
 * Kesho Group LLC
 * 770-852-5049
 * info@thekeshogroup.com
 * https://thekeshogroup.com/
 *
 *
 */
public class FlickerDrawable extends LayerDrawable {

    private LayerDrawable layerDrawable;
    private Drawable baseDrawable;
    private ClipDrawable rebaseDrawable;
    private ClipDrawable flickerDrawable;
    private ClipDrawable backgroundDrawable;

    //easy index numbers
    private int botword = 0;
    private int flick = 1;
    private int bcolor = 2;
    private int topword = 3;
    private int flickerDuration=450;
    private int inset = 4;

    /**
     * @param res             Resource needed to access components
     * @param drawableId      Drawable id expected as an R.drawable reference used to create the drawable
     * @param backgroundcolor int Color reference used to smooth the image into the background
     */
    public FlickerDrawable(Resources res, int drawableId, int backgroundcolor) {
        //Must use depreciated method other wise this will only work for api 21 and above
        super(new Drawable[]{
                res.getDrawable(drawableId).mutate(),
                new ClipDrawable(res.getDrawable(drawableId).mutate(), Gravity.LEFT, ClipDrawable.HORIZONTAL),
                new ClipDrawable(res.getDrawable(drawableId).mutate(), Gravity.LEFT, ClipDrawable.HORIZONTAL),
                new ClipDrawable(res.getDrawable(drawableId).mutate(), Gravity.LEFT, ClipDrawable.HORIZONTAL)}
        );


        //Standard inset  taking into account dpi
        inset = (int) (0.0414 *super.getIntrinsicHeight()* res.getDisplayMetrics().density);

        //Set id to index
        super.setId(botword, botword);
        super.setId(flick, flick);
        super.setId(bcolor, bcolor);
        super.setId(topword, topword);

        //set the insets
        super.setLayerInset(topword, inset, inset, inset, inset);
        super.setLayerInset(botword, inset, inset, inset, inset);

        //Add filter to blend background and make flicker white
        super.getDrawable(bcolor).setColorFilter(backgroundcolor, PorterDuff.Mode.SRC_IN);
        super.getDrawable(flick).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        flickerDuration= (int)(1.5*super.getDrawable(botword).getIntrinsicWidth());

    }

    public void flick() {

        //Method inside flickerdrawable

        //reset
        super.getDrawable(flick).setLevel(0);
        super.getDrawable(bcolor).setLevel(0);
        super.getDrawable(topword).setLevel(0);

        super.getDrawable(flick).setAlpha(255);
        super.getDrawable(bcolor).setAlpha(255);
        super.getDrawable(topword).setAlpha(255);

        //Define animations
        ObjectAnimator clipAnimator1 = ObjectAnimator.ofInt(super.getDrawable(1), "level", 0, 10000);
        clipAnimator1.setDuration(flickerDuration);
        clipAnimator1.setStartDelay(200);

        ObjectAnimator clipAnimator2 = ObjectAnimator.ofInt(super.getDrawable(2), "level", 0, 10000);
        clipAnimator2.setDuration(flickerDuration);
        clipAnimator2.setStartDelay(225);
        clipAnimator2.setRepeatCount(0);

        ObjectAnimator clipAnimator3 = ObjectAnimator.ofInt(super.getDrawable(3), "level", 0, 10000);
        clipAnimator3.setDuration(flickerDuration);
        clipAnimator3.setStartDelay(225);
        clipAnimator3.setRepeatCount(0);

        ObjectAnimator alphaAnimator1 = ObjectAnimator.ofInt(super.getDrawable(1), "alpha", 255, 0);
        alphaAnimator1.setDuration(400);
        alphaAnimator1.setStartDelay(flickerDuration+200);

        ObjectAnimator alphaAnimator2 = ObjectAnimator.ofInt(super.getDrawable(2), "alpha", 255, 0);
        alphaAnimator2.setDuration(400);
        alphaAnimator2.setStartDelay(flickerDuration+200);

        clipAnimator1.start();
        clipAnimator2.start();
        clipAnimator3.start();
        alphaAnimator1.start();
        alphaAnimator2.start();

    }
}


