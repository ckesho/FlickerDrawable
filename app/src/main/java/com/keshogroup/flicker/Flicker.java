package com.keshogroup.flicker;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Flicker extends AppCompatActivity {

    FlickerDrawable flickerDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flicker);

        ImageView keshogroup_logo = (ImageView) findViewById(R.id.keshogroup_logo);
        flickerDrawable = new FlickerDrawable(getResources(), R.drawable.keshogroup_logo_white, getResources().getColor(R.color.gray));
        keshogroup_logo.setImageDrawable(flickerDrawable);
        keshogroup_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flickerDrawable.flick();
            }
        });

        flickerDrawable.flick();


    }
}
