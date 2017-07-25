package com.example.gallerypicker.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.gallerypicker.R;

import java.util.ArrayList;

public class GalleryAdapter extends BaseAdapter {

    private Context mContext;
    private int mResourceId;
    private ArrayList<String> mImagesPath = new ArrayList<>();

    public GalleryAdapter(Context context, int resourceId, ArrayList<String> imagesPath) {
        this.mContext = context;
        this.mResourceId = resourceId;
        this.mImagesPath = imagesPath;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mImagesPath.size();
    }

    @Override
    public Object getItem(int position) {
        return mImagesPath.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View item = convertView;
        ViewHolder viewHolder;

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();

        if (item == null) {

            item = inflater.inflate(mResourceId, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) item.findViewById(R.id.image);
            viewHolder.overlay = (ImageView) item.findViewById(R.id.overlay);

            item.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) item.getTag();
        }

        Glide.with(mContext)
                .load(mImagesPath.get(position))
                .error(R.drawable.image_item_placeholder)
                .into(viewHolder.image);

        return item;
    }

    private static class ViewHolder {
        ImageView image;
        ImageView overlay;
    }
}