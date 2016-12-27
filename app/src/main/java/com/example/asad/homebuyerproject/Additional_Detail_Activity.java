package com.example.asad.homebuyerproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class Additional_Detail_Activity extends AppCompatActivity {

    private ImageView mUploadPhotos, mUploadPhotos12;
    private ArrayList<String> propertyData=new ArrayList<>();
    private ArrayList<String> Amenities=new ArrayList<>();
    private ArrayList<String> Tenent=new ArrayList<>();
    private EditText directionFacing,additionaldetail;
    private Button Addtodatabase;
    private LinearLayout layout1,layout2,layout3,layout4;
    private DatabaseReference mDatabase;
    HashMap<String, String> PropertyMap = new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment__additional__detail);
        Bundle extras = getIntent().getExtras();
        if(extras==null){
            Toast.makeText(Additional_Detail_Activity.this, "Previous Data Not Found", Toast.LENGTH_SHORT).show();
        }else {
            propertyData = extras.getStringArrayList("data");
            Toast.makeText(this, "PropertyDataSize"+propertyData.size(), Toast.LENGTH_SHORT).show();
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Typecasting();
        PostPropertyClickEvent();
        UploadPhotosEvent();


    }

    private void UploadPhotosEvent() {

        mUploadPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GalleryFragment open
                Gallery_Activity_Fragment frag = new Gallery_Activity_Fragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                //Gone View
                LinearLayout layout=(LinearLayout)findViewById(R.id.Replace_Gallery_Image12);
                layout.setVisibility(View.GONE);


                transaction.replace(R.id.Replace_Gallery_Image, frag, "Upload Photos");
                transaction.addToBackStack(null);
                transaction.commit();
                Toast.makeText(getApplication(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void PostPropertyClickEvent() {

        Addtodatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!directionFacing.getText().toString().matches("")){
                    propertyData.add("PropertyDirection");
                    propertyData.add(directionFacing.getText().toString());
                }
                if (!additionaldetail.getText().toString().matches("")){
                    propertyData.add("PropertyAdditionDetail");
                    propertyData.add(additionaldetail.getText().toString());
                }
                int count = layout1.getChildCount();
                View checkboxview = null;
                for(int i=0; i<count; i++) {
                    checkboxview = layout1.getChildAt(i);
                    boolean checked = ((CheckBox) checkboxview).isChecked();
                    if (checked){
                        Amenities.add(((CheckBox) checkboxview).getText().toString());
                        //Toast.makeText(Additional_Detail_Activity.this,  "CheckBox id"+((CheckBox) checkboxview).getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                int count2 = layout2.getChildCount();
                View checkboxview2 = null;
                for(int i=0; i<count2; i++) {
                    checkboxview2 = layout2.getChildAt(i);
                    boolean checked = ((CheckBox) checkboxview2).isChecked();
                    if (checked){
                        Amenities.add(((CheckBox) checkboxview2).getText().toString());
                        Toast.makeText(Additional_Detail_Activity.this,  "CheckBox id"+((CheckBox) checkboxview2).getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                int count3 = layout3.getChildCount();
                View checkboxview3 = null;
                for(int i=0; i<count3; i++) {
                    checkboxview3 = layout3.getChildAt(i);
                    boolean checked = ((CheckBox) checkboxview3).isChecked();
                    if (checked){
                        Tenent.add(((CheckBox) checkboxview3).getText().toString());
                        Toast.makeText(Additional_Detail_Activity.this,  "CheckBox id"+((CheckBox) checkboxview3).getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                }int count4 = layout4.getChildCount();
                View checkboxview4 = null;
                for(int i=0; i<count4; i++) {
                    checkboxview4 = layout4.getChildAt(i);
                    boolean checked = ((CheckBox) checkboxview4).isChecked();
                    if (checked){
                        Tenent.add(((CheckBox) checkboxview4).getText().toString());
                        Toast.makeText(Additional_Detail_Activity.this,  "CheckBox id"+((CheckBox) checkboxview4).getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        if (!propertyData.isEmpty()){
            for (int i=0;i<propertyData.size();i=i+2){
                PropertyMap.put(propertyData.get(i),propertyData.get(i+1));
            }
        }
       mDatabase.child("Property").setValue(PropertyMap);
        if (!Amenities.isEmpty()){
           /* propertyData.add("Amenities");
            propertyData.addAll(Amenities);*/
            mDatabase.child("Property").child("Amentities").setValue(Amenities);

        }
        if (!Tenent.isEmpty()){
            /*propertyData.add("Tenents");
            propertyData.addAll(Tenent);*/
            mDatabase.child("Property").child("Tenent").setValue(Tenent);
        }
    }
    private void Typecasting() {

        mUploadPhotos = (ImageView) findViewById(R.id.Upload_Photos);
        directionFacing=(EditText)findViewById(R.id.DirectionFacingResult);
        additionaldetail=(EditText)findViewById(R.id.AddMoreDetailsResult);
        Addtodatabase=(Button)findViewById(R.id.Readytogo);
        layout1=(LinearLayout)findViewById(R.id.PanelOne);
        layout2=(LinearLayout)findViewById(R.id.Panel2);
        layout3=(LinearLayout)findViewById(R.id.panelthree);
        layout4=(LinearLayout)findViewById(R.id.panelfour);
    }
}
