package com.example.ramakantkushwaha.advancedcustomgallery;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private GridView gridView;
    private ImageSelectorAdapter imageSelectorAdapter;
    public final static String SELECTED_IMAGES_URI = "SELECTED_IMAGES_URI";
    private int imgTaken = 0;
    private int selected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> imageUri = getIntent().getStringArrayListExtra(ImageFolderActivity.IMAGES_URIS);
        imgTaken = getIntent().getIntExtra(AppConstants.ATTACHED_PHOTO,0);
        ArrayList<ImageModel> imageModelsList = new ArrayList<>();
        for(int i=0;i<imageUri.size();i++){
            ImageModel imageModel = new ImageModel();
            imageModel.setIsSelected(false);
            imageModel.setUri(imageUri.get(i));
            imageModelsList.add(imageModel);
        }
        initActionbar();
        gridView = (GridView) findViewById(R.id.imageGrid);
        imageSelectorAdapter = new ImageSelectorAdapter(this,imageModelsList);
        gridView.setAdapter(imageSelectorAdapter);
        gridView.setOnItemClickListener(this);
    }

    protected void initActionbar() {
        //ActionBar action = getActionBar();
        //action.setDisplayShowCustomEnabled(true);
        //action.setDisplayShowHomeEnabled(false);
        //action.setDisplayShowTitleEnabled(false);
        //LayoutInflater li = LayoutInflater.from(this);
       // View customView = li.inflate(R.layout.gallery_actionbar, null);
        //View v = customView.findViewById(R.id.back_arrow);
        //if(!FlashUtil.checkPhoneFlash(ImageGridActivity.this)){
          //  customView.findViewById(R.id.ivMenuRight).setVisibility(View.INVISIBLE);
        //}
        //ImageView imageView = (ImageView) customView.findViewById(R.id.menu_bar);
        //((TextView) customView.findViewById(R.id.submit_images)).setVisibility(View.VISIBLE);

/*
        ((TextView) customView.findViewById(R.id.submit_images)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageSelectorAdapter != null) {
                    ArrayList<String> selectedImageUri = imageSelectorAdapter.getAllSelectedImages();
                    if (selectedImageUri.size() == 0) {
                        Toast.makeText(getBaseContext(), "Please Select Images", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent intent = new Intent();
                    intent.putStringArrayListExtra(SELECTED_IMAGES_URI, selectedImageUri);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
*/

/*        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources resources = getBaseContext().getResources();
                String helpline_no = resources.getString(R.string.helpline_no);
                UiUtils.callPhone(ImageGridActivity.this, helpline_no);
            }
        });*/
/*        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/
/*        View torchV = customView.findViewById(R.id.ivMenuRight);
        torchV.setVisibility(View.GONE);
        View torchV = customView.findViewById(R.id.ivMenuRight);
        torchV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlashUtil.changeTorch();
            }
        });*/

/*        TextView mTitle = (TextView) customView.findViewById(R.id.tvTitle);
        mTitle.setText("Select Images");
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        action.setCustomView(customView);*/
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ImageModel imageModel = imageSelectorAdapter.getItem(position);
        if (imageModel.isSelected()) {
            imageModel.setIsSelected(false);
            ((ImageView)view.findViewById(R.id.greenTick)).setVisibility(View.GONE);
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                ((ImageView)view.findViewById(R.id.folderImage)).setAlpha(255);
            } else {
                ((ImageView)view.findViewById(R.id.folderImage)).clearColorFilter();
            }
            selected--;

        } else {
            if (imgTaken + selected < AppConstants.ALLOWED_SCREEN_SHOTS) {//Added into app constant
                imageModel.setIsSelected(true);
                ((ImageView) view.findViewById(R.id.greenTick)).setVisibility(View.VISIBLE);
                int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    ((ImageView) view.findViewById(R.id.folderImage)).setAlpha(100);
                } else {
                    ((ImageView) view.findViewById(R.id.folderImage)).setColorFilter(Color.argb(180, 53, 53, 53), PorterDuff.Mode.SRC_ATOP);
                }
                selected++;
            }
            else{
                Toast.makeText(this,"Only "+AppConstants.ALLOWED_SCREEN_SHOTS +" Screenshots are Allowed", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.upload) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
