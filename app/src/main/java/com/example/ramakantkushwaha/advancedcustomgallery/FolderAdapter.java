package com.example.ramakantkushwaha.advancedcustomgallery;

/**
 * Created by ramakant.kushwaha on 10/28/2015.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FolderAdapter extends BaseAdapter {

    private HashMap<String, ArrayList<String>> folders;
    LayoutInflater layoutInflater;
    private DisplayImageOptions options;

    public FolderAdapter(final Context context, HashMap<String, ArrayList<String>> folders) {
        this.folders = folders;
        layoutInflater = LayoutInflater.from(context);
        options = ImageUtils.universalImageConfiguration(context.getResources().getDimensionPixelSize(R.dimen.gallery_image_size),context.getResources().getDimensionPixelSize(R.dimen.gallery_image_size));
    }

    @Override
    public int getCount() {
        return folders.size();
    }

    @Override
    public String getItem(int position) {

        Iterator it = folders.entrySet().iterator();
        int index = 0;
        String result = null;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            if (index == position) {
                result = (String) pair.getKey();
                break;
            }
            index++;
        }
        return result;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String key = getItem(position);
        View view = convertView;
        ViewHolder viewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.image_folder_item, null, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.folderName);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.folderImage);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textView.setText(key);

        ImageLoader.getInstance().displayImage("file://" + folders.get(key).get(0), viewHolder.imageView,options);


        return view;
    }

    class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}