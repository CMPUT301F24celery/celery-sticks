package com.example.celery_sticks.ui.browseimages;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.celery_sticks.R;

import java.util.ArrayList;

/**
 * ArrayAdapter for displaying images in the BrowseImages page for admins only
 */
public class ImageArrayAdapter extends ArrayAdapter<String[]> {

    /**
     * Constructor to setup ArrayAdapter
     * @param context
     * @param data
     */
    public ImageArrayAdapter(Context context, ArrayList<String[]> data) {
        super (context, 0, data);
    }


    /**
     * Get the view at a given position
     * @param position
     * @param convertView
     * @param parent
     * @return view at the given position
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.admin_browse_images_content, parent, false);
        } else {
            view = convertView;
        }
        // string[] type has the following info in indices 0-3: type, title, id, imageData
        String[] instance = getItem(position);

        TextView type = view.findViewById(R.id.image_type);
        TextView title = view.findViewById(R.id.image_title);
        ImageView image = view.findViewById(R.id.admin_browse_image_view);

        type.setText(instance[0]);
        title.setText(instance[1]);


        // set color here
        if (position % 5 == 1) {
            view.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.list_blue));
        } else if(position % 5 == 2) {
            view.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.list_orange));
        } else if(position % 5 == 3) {
            view.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.list_green));
        } else if(position % 5 == 4) {
            view.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.list_red));
        }
        else {
            view.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.list_purple));
        }

        // set image to imageview here
        if (instance[3] != null) {
            if (!instance[3].equals("")) {

                byte[] decodedImage = Base64.decode(instance[3], Base64.DEFAULT);

                Bitmap qrBitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
                // set qrImage to decoded bitmap
                image.setImageBitmap(qrBitmap);
            }
        }

        return view;
    }
}
