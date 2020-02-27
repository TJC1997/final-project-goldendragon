package com.example.getsumgame;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class DetailActivity extends AppCompatActivity {

    private ImageView streamPreview = null;


    // One-Time manipulation of the DOM to create image object.
    private void addStreamPreview(){
        if (streamPreview == null) {

            this.streamPreview = new ImageView(this);
            this.streamPreview.setImageDrawable(getDrawable(R.drawable.apex_legends));

            FrameLayout frame = this.findViewById(R.id.detail_preview_frame);
            frame.addView(this.streamPreview);
            // Now StreamPreview will always be referenced but it will be set in the dom.
            // This should take away from coupling.
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // One-Time function call to add the stream preview object to the DOM
        // Picture can be changed after this.
        addStreamPreview();
    }
}
