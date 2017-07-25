package com.example.gallerypicker.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gallerypicker.Adapters.GalleryAdapter;
import com.example.gallerypicker.R;

import java.util.ArrayList;

import static com.example.gallerypicker.Utils.Utils.getImagesFromDevice;

public class GalleryActivity extends AppCompatActivity {

    private static final int MAX_SELECTION = 10;

    Toolbar mToolbar;
    GridView mGallery;
    Button mButton;
    ArrayList<String> mPhotos = new ArrayList<>();
    private int mSelectionCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.toolbar_title);
        setSupportActionBar(mToolbar);

        mPhotos = getImagesFromDevice(this);

        if (mPhotos.size() > 0) {
            mGallery = (GridView) findViewById(R.id.gallery);
            mGallery.setAdapter(new GalleryAdapter(this, R.layout.gallery_image_item, mPhotos));
            mGallery.setOnItemClickListener(new GalleryItemClickListener(this));
            mGallery.setVisibility(View.VISIBLE);
        } else {
            TextView message = (TextView)findViewById(R.id.message);
            message.setText(R.string.no_images_found);
            message.setVisibility(View.VISIBLE);
        }

        mButton = (Button) findViewById(R.id.button_next);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gallery_fragment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_take_photo: {
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private class GalleryItemClickListener implements AdapterView.OnItemClickListener {

        private Context mContext;

        private GalleryItemClickListener(Context context) {
            this.mContext = context;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            ImageView overlay = (ImageView) view.findViewById(R.id.overlay);

            if (overlay.getVisibility() == View.VISIBLE) {
                mSelectionCounter--;
                overlay.setVisibility(View.GONE);
            } else {
                if (mSelectionCounter < MAX_SELECTION) {
                    mSelectionCounter++;
                    overlay.setVisibility(View.VISIBLE);
                } else {
                    String message = String.format(getString(R.string.max_selection_reached), MAX_SELECTION);
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                }
            }

            if (mSelectionCounter > 0 && mSelectionCounter <= MAX_SELECTION) {
                String title = String.format(getString(R.string.toolbar_title_selected_items), mSelectionCounter);
                mToolbar.setTitle(title);
                mButton.setEnabled(true);
            } else if (mSelectionCounter == 0) {
                mToolbar.setTitle(R.string.toolbar_title);
                mButton.setEnabled(false);
            }
        }
    }
}
