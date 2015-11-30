package com.example.ramakantkushwaha.advancedcustomgallery;

/**
 * Created by ramakant.kushwaha on 10/28/2015.
 */
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;


public class ImageSelectorAdapter extends BaseAdapter {
    List<ImageModel> imageList;
    LayoutInflater layoutInflater;
    final int THUMBSIZE = 150;
    private DisplayImageOptions options;



    public ImageSelectorAdapter(final Context context,List<ImageModel> imageList){
        this.imageList = imageList;
        layoutInflater = LayoutInflater.from(context);
        options = ImageUtils.universalImageConfiguration(context.getResources().getDimensionPixelSize(R.dimen.gallery_image_size),context.getResources().getDimensionPixelSize(R.dimen.gallery_image_size));
    }

    public ArrayList<String> getAllSelectedImages(){
        ArrayList<String> selectedList = new ArrayList<>();
        for(int i=0;i<getCount();i++){
            ImageModel imageModel = getItem(i);
            if(imageModel.isSelected()){
                selectedList.add(imageModel.getUri());
            }
        }
        return selectedList;
    }

    public ArrayList<String> getAllImagesUri() {
        ArrayList<String> selectedList = new ArrayList<>();
        for (int i = 0; i < getCount(); i++) {
            ImageModel imageModel = getItem(i);
            selectedList.add(imageModel.getUri());
        }
        return selectedList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public ImageModel getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageModel imageUri = getItem(position);
        View view = convertView;
        ViewHolder viewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.image_folder_item, null, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.folderName);
            viewHolder.textView.setVisibility(View.GONE);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.folderImage);
            viewHolder.greenTick = (ImageView) view.findViewById(R.id.greenTick);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        int sdk = android.os.Build.VERSION.SDK_INT;
        if(imageUri.isSelected()){
            viewHolder.greenTick.setVisibility(View.VISIBLE);
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                ((ImageView)view.findViewById(R.id.folderImage)).setAlpha(100);
            } else {
                ((ImageView)view.findViewById(R.id.folderImage)).setImageAlpha(100);
            }
        }else{
            viewHolder.greenTick.setVisibility(View.GONE);
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                ((ImageView)view.findViewById(R.id.folderImage)).setAlpha(255);
            } else {
                ((ImageView)view.findViewById(R.id.folderImage)).setImageAlpha(255);
            }
        }


        ImageLoader.getInstance().displayImage("file://" + imageUri.getUri(), viewHolder.imageView, options);

        return view;
    }

    public void removeImage(int position) {
        if(imageList!=null && position>-1 && position<imageList.size()){
            imageList.remove(position);
        }
    }

    class ViewHolder {
        TextView textView;
        ImageView imageView;
        ImageView greenTick;
    }
}
