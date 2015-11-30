package com.example.ramakantkushwaha.advancedcustomgallery;

/**
 * Created by ramakant.kushwaha on 10/28/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;

public class ImageFolderActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private HashMap<String,ArrayList<String>> folders;
    private GridView gridView;
    private FolderAdapter folderAdapter;
    private final int REQUEST_CODE = 1;
    public final static String IMAGES_URIS = "IMAGES_URIS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionbar();
        gridView = (GridView) findViewById(R.id.imageGrid);

    }

    @Override
    protected void onStart() {
        super.onStart();
        folders = ImageUtils.getImagesFromExternal(this);
        folderAdapter = new FolderAdapter(this,folders);
        gridView.setAdapter(folderAdapter);
        gridView.setOnItemClickListener(this);
    }

    protected void initActionbar() {
/*        ActionBar action = getActionBar();
        action.setDisplayShowCustomEnabled(true);
        action.setDisplayShowHomeEnabled(false);
        action.setDisplayShowTitleEnabled(false);
        LayoutInflater li = LayoutInflater.from(this);
        View customView = li.inflate(R.layout.gallery_actionbar, null);
        View v = customView.findViewById(R.id.back_arrow);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        View torchV = customView.findViewById(R.id.ivMenuRight);
        torchV.setVisibility(View.GONE);
        View ok = customView.findViewById(R.id.submit_images);
        ok.setVisibility(View.GONE);
        View call = customView.findViewById(R.id.menu_bar);
        call.setVisibility(View.GONE);
       *//* View torchV = customView.findViewById(R.id.ivMenuRight);
        torchV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlashUtil.changeTorch();
            }
        });*//*

        TextView mTitle = (TextView) customView.findViewById(R.id.tvTitle);
        mTitle.setText("Select Images");
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        action.setCustomView(customView);*/
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String folder = folderAdapter.getItem(position);
        if(folder!=null && folders.containsKey(folder)){
            ArrayList<String> imageUri = folders.get(folder);
            Intent i = new Intent(ImageFolderActivity.this,MainActivity.class);
            i.putStringArrayListExtra(IMAGES_URIS,imageUri);
            i.putExtra(AppConstants.ATTACHED_PHOTO,getIntent().getIntExtra(AppConstants.ATTACHED_PHOTO,0));
            startActivityForResult(i, REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        }
    }
}
