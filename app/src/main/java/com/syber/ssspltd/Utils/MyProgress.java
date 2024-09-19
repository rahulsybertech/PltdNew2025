package com.syber.ssspltd.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.mrtyvz.archedimageprogress.ArchedImageProgressBar;
import com.syber.ssspltd.R;

/**
 * Created by Punit Chaurasiya on 25-12-2018.
 */

public class MyProgress extends Dialog {
    ImageView imageView;
    Animation pulse;

    public MyProgress(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        setContentView(R.layout.progress_layout);
        ArchedImageProgressBar customTextArcProgress = findViewById(R.id.ct1_progressBar);
        customTextArcProgress.setProgressText(new String[]{"Loading"}, "#FFFFFF");
        customTextArcProgress.setProgressTextSize(13.0f);
        customTextArcProgress.setCircleSize(35.0f);
        customTextArcProgress.setArchSize(40.0f);
        customTextArcProgress.setCircleColor(Color.parseColor("#3949ab"));
        customTextArcProgress.setArchColor(Color.parseColor("#fb8c00"));
        customTextArcProgress.setArchLength(120);
        customTextArcProgress.setArchStroke(9.0f);
        customTextArcProgress.setArchSpeed(3);
        //imageView = (ImageView) findViewById(R.id.image);
        //pulse = AnimationUtils.loadAnimation(context, R.anim.pulse);
        super.setCancelable(false);
    }

    @Override
    public void show() {
        super.show();
        //imageView.startAnimation(pulse);
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}


