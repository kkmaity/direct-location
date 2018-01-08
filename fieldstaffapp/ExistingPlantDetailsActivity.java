package com.fieldstaffapp.fieldstaffapp;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fieldstaffapp.fieldstaffapp.adapter.CustomSpinnerAdapter;
import com.fieldstaffapp.fieldstaffapp.model.existing_geotagging.ExistingGeoTagging;
import com.fieldstaffapp.fieldstaffapp.restservice.RestService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExistingPlantDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txtvwPlantId;
    private TextView txtvwPlantName;
    private TextView txtvwPlantAddress;
    private EditText edtxtPlantHeight;
    private RadioButton rdbtnGood;
    private RadioButton rdbtnBad;
    private EditText edtxtPlantProtection;
    private Button btnCancel;
    private Button btnContinue;
    private String plantgId;
    private String plantNo;
    private String plantHeight;
    private String plantAddress;
    private String plantCondition;
    private String plantProtection;
    private String plantationType;
    private String blockPlantation;
    private String plantAdopter;
    private String plantAdopterPhone;
    private String plantName;
    private RelativeLayout rlLine;
    private EditText edtxtPlantCondition;
  /*  private EditText edtxtBlockPlantationType;
    private EditText edtxtPlantationType;*/
    private TextView edtxtPlantName;
    private CustomSpinnerAdapter adapterService;
    private PopupWindow popupWindow;
    private ListView listViewService;
    private LinearLayout linearBlockPlantation;
   // private RelativeLayout relativeBlockPlantation;
    private TextView txtvwBlockPlantationType;
    private TextView txtvwPlantationType;
    private String geoTaggingId;
    private String plantImageId;
    private ProgressDialog progrssDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_plant_details);
        init();

    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageView backImgView = (ImageView) findViewById(R.id.backImgView);
        TextView titleTxtView = (TextView) findViewById(R.id.titleTxtView);
        titleTxtView.setText("Existing Plant Details");
        backImgView.setOnClickListener(this);
        popupWindow=new PopupWindow();
        progrssDialog=new ProgressDialog(this);
        progrssDialog.setCancelable(false);
        progrssDialog.setMessage("Loading...");
        linearBlockPlantation=(LinearLayout)findViewById(R.id.linearBlockPlantation);
       // relativeBlockPlantation=(RelativeLayout)findViewById(R.id.relativeBlockPlantation);
        txtvwBlockPlantationType=(TextView)findViewById(R.id.txtvwBlockPlantationType);
        txtvwPlantationType=(TextView)findViewById(R.id.txtvwPlantationType);
        txtvwPlantId=(TextView)findViewById(R.id.txtvwPlantId);
        txtvwPlantName=(TextView)findViewById(R.id.txtvwPlantName);
        txtvwPlantAddress=(TextView)findViewById(R.id.txtvwPlantAddress);
        edtxtPlantHeight=(EditText)findViewById(R.id.edtxtPlantHeight);
        edtxtPlantProtection=(EditText)findViewById(R.id.edtxtPlantProtection);
        rlLine=(RelativeLayout)findViewById(R.id.rlLine);
        edtxtPlantCondition=(EditText)findViewById(R.id.edtxtPlantCondition);
        /*edtxtBlockPlantationType=(EditText)findViewById(R.id.edtxtBlockPlantationType);
        edtxtPlantationType=(EditText)findViewById(R.id.edtxtPlantationType);*/
        txtvwPlantName=(TextView)findViewById(R.id.txtvwPlantName);
        edtxtPlantHeight=(EditText)findViewById(R.id.edtxtPlantHeight);
        edtxtPlantProtection=(EditText)findViewById(R.id.edtxtPlantProtection);
        btnCancel=(Button)findViewById(R.id.btnCancel);
        btnContinue=(Button)findViewById(R.id.btnContinue);
        btnCancel.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        edtxtPlantProtection.setOnClickListener(this);
        edtxtPlantCondition.setOnClickListener(this);
       // edtxtPlantationType.setOnClickListener(this);
      //  edtxtBlockPlantationType.setOnClickListener(this);
        getAndSetAllIntentValue();
    }

    private void getAndSetAllIntentValue() {
        plantImageId=getIntent().getStringExtra(FieldStaffApp.KEY_EXISTING_PLANT_IMAGE_ID);
        plantgId=getIntent().getStringExtra(FieldStaffApp.KEY_EXISTING_PLANT_ID);
        plantName =getIntent().getStringExtra(FieldStaffApp.KEY_EXISTING_PLANT_NAME);
        plantNo=getIntent().getStringExtra(FieldStaffApp.KEY_EXISTING_PLANT_NO);
        geoTaggingId=getIntent().getStringExtra(FieldStaffApp.KEY_EXISTING_GEOTAGGING_ID);
        plantAddress=getIntent().getStringExtra(FieldStaffApp.KEY_EXISTING_PLANT_STREET_ADDRESS);
        plantHeight=getIntent().getStringExtra(FieldStaffApp.KEY_EXISTING_PLANT_HEIGHT);
        plantCondition=getIntent().getStringExtra(FieldStaffApp.KEY_EXISTING_PLANT_CONDITION);
        plantProtection=getIntent().getStringExtra(FieldStaffApp.KEY_EXISTING_PLANT_PROTECTION);
        plantationType=getIntent().getStringExtra(FieldStaffApp.KEY_EXISTING_PLANTATION_TYPE);
        blockPlantation=getIntent().getStringExtra(FieldStaffApp.KEY_EXISTING_PLANTATION_BLOCK);
        plantAdopter=getIntent().getStringExtra(FieldStaffApp.KEY_EXISTING_PLANTAT_ADOPTER);
        plantAdopterPhone=getIntent().getStringExtra(FieldStaffApp.KEY_EXISTING_PLANTAT_ADOPTER_PHONE);
        if (plantgId!=null)
        txtvwPlantId.setText(plantNo);
        if (plantName!=null)
            txtvwPlantName.setText(plantName);
        if (plantAddress!=null)
        txtvwPlantAddress.setText(plantAddress);
        if (plantHeight!=null)
            edtxtPlantHeight.setText(plantHeight);
        if (plantationType!=null)
        {
            txtvwPlantationType.setText(plantationType);
            if (plantationType.equalsIgnoreCase("Block Plantation")){
                linearBlockPlantation.setVisibility(View.VISIBLE);
               // relativeBlockPlantation.setVisibility(View.VISIBLE);
                txtvwBlockPlantationType.setText(blockPlantation);
            }else {
                linearBlockPlantation.setVisibility(View.GONE);
               // relativeBlockPlantation.setVisibility(View.GONE);
            }

        }
        if (plantCondition!=null)
        edtxtPlantCondition.setText(plantCondition);
        if (plantProtection!=null)
            edtxtPlantProtection.setText(plantProtection);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnContinue:
                if (validateAllField()){
                    if (FieldStaffApp.getInstance().isConnectionPossible()) {


                        existingGeoTagging();
                    }
                   else
                        FieldStaffApp.showDialog(ExistingPlantDetailsActivity.this,getResources().getString(R.string.err_msg_internet));

                }
                break;
            case R.id.backImgView:
                finish();
                break;
            case R.id.edtxtPlantProtection:
                hideSoftKeyboard();
                commonDropdownPopupWindow(edtxtPlantProtection,FieldStaffApp.plantProtectionList);
                break;
           /* case R.id.edtxtPlantationType:
                commonDropdownPopupWindow(edtxtPlantationType,FieldStaffApp.plantationTypeList);

                break;
            case R.id.edtxtBlockPlantationType:
                commonDropdownPopupWindow(edtxtBlockPlantationType,FieldStaffApp.blockPlantationList);
                break;*/
            case R.id.edtxtPlantCondition:
                hideSoftKeyboard();
                commonDropdownPopupWindow(edtxtPlantCondition,FieldStaffApp.PlantationConditionList);
                break;
        }

    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    private void existingGeoTagging() {
        if (FieldStaffApp.getInstance().isConnectionPossible()){
            progrssDialog.show();
            Call<ExistingGeoTagging> call= RestService.getInstance().restInterface.existingGeotagging(FieldStaffApp.FROM_APP,
                    FieldStaffApp.APP_TYPE,"EXISTGEOTAGGING",plantNo,edtxtPlantHeight.getText().toString(),edtxtPlantCondition.getText().toString(),
                    edtxtPlantProtection.getText().toString(),geoTaggingId);
                call.enqueue(new Callback<ExistingGeoTagging>() {
                    @Override
                    public void onResponse(Call<ExistingGeoTagging> call, Response<ExistingGeoTagging> response) {
                    if (progrssDialog.isShowing())
                        progrssDialog.dismiss();
                        if (response.body().getIsSuccess()){
                            Intent intent=new Intent(ExistingPlantDetailsActivity.this,FormTwoActivity.class);
                            intent.putExtra(FieldStaffApp.KEY_PLANT_ID,response.body().getExistingGeotaggingInfo().getPlantId());
                            intent.putExtra(FieldStaffApp.KEY_PLANT_NO,plantNo);
                            intent.putExtra(FieldStaffApp.KEY_GEOTAGGING_ID,geoTaggingId);
                            intent.putExtra(FieldStaffApp.KEY_IS_EXISTING,FieldStaffApp.VALUE_EXISTING);
                            intent.putExtra(FieldStaffApp.KEY_EXISTING_PLANT_IMAGE_ID,plantImageId);
                            startActivity(intent);


                        }

                    }

                    @Override
                    public void onFailure(Call<ExistingGeoTagging> call, Throwable t) {
                        if (progrssDialog.isShowing())
                            progrssDialog.dismiss();
                    }
                });


        }else
            FieldStaffApp.showDialog(ExistingPlantDetailsActivity.this,getResources().getString(R.string.err_msg_internet));
    }

    void commonDropdownPopupWindow(final EditText edtview, final List<String> category) {
        try {
            Display mDisplay = ExistingPlantDetailsActivity.this.getWindowManager().getDefaultDisplay();
            final int width  = mDisplay.getWidth()-40;
            View content=View.inflate(ExistingPlantDetailsActivity.this,R.layout.common_dropdown_list,null);
            popupWindow.setContentView(content);
            listViewService=(ListView)content.findViewById(R.id.listViewService);
            adapterService=new CustomSpinnerAdapter(ExistingPlantDetailsActivity.this,R.layout.item_common_dropdown,category);
            listViewService.setAdapter(adapterService);
            popupWindow = new PopupWindow(content, width, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            popupWindow.showAsDropDown(edtview);

            listViewService.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    edtview.setText(category.get(i));
                    if (popupWindow!=null&&popupWindow.isShowing())
                        popupWindow.dismiss();
                   /* if(edtview.getText().toString().trim().equalsIgnoreCase("Block Plantation")){
                        edtxtBlockPlantationType.setVisibility(View.VISIBLE);
                        rlLine.setVisibility(View.VISIBLE);
                    }else if(edtview.getText().toString().trim().equalsIgnoreCase("Government Offices")){
                        edtxtBlockPlantationType.setVisibility(View.VISIBLE);
                        rlLine.setVisibility(View.VISIBLE);
                    }else if(edtview.getText().toString().trim().equalsIgnoreCase("Private Offices")){
                        edtxtBlockPlantationType.setVisibility(View.VISIBLE);
                        rlLine.setVisibility(View.VISIBLE);
                    }else if(edtview.getText().toString().trim().equalsIgnoreCase("Open site Plantation")){
                        edtxtBlockPlantationType.setVisibility(View.VISIBLE);
                        rlLine.setVisibility(View.VISIBLE);
                    }else if(edtview.getText().toString().trim().equalsIgnoreCase("Parks & Playgrounds")){
                        edtxtBlockPlantationType.setVisibility(View.VISIBLE);
                        rlLine.setVisibility(View.VISIBLE);
                    }else if(edtview.getText().toString().trim().equalsIgnoreCase("Government Schools")){
                        edtxtBlockPlantationType.setVisibility(View.VISIBLE);
                        rlLine.setVisibility(View.VISIBLE);
                    }else if(edtview.getText().toString().trim().equalsIgnoreCase("Private Schools")){
                        edtxtBlockPlantationType.setVisibility(View.VISIBLE);
                        rlLine.setVisibility(View.VISIBLE);
                    }else if(edtview.getText().toString().trim().equalsIgnoreCase("Burial grounds")){
                        edtxtBlockPlantationType.setVisibility(View.VISIBLE);
                        rlLine.setVisibility(View.VISIBLE);
                    }else if(edtview.getText().toString().trim().equalsIgnoreCase("Holy Places")){
                        edtxtBlockPlantationType.setVisibility(View.VISIBLE);
                        rlLine.setVisibility(View.VISIBLE);
                    }else if(edtview.getText().toString().trim().equalsIgnoreCase("Others")){
                        edtxtBlockPlantationType.setVisibility(View.VISIBLE);
                        rlLine.setVisibility(View.VISIBLE);
                    }
                    else {
                        edtxtBlockPlantationType.setText("");
                        edtxtBlockPlantationType.setVisibility(View.GONE);
                        rlLine.setVisibility(View.GONE);
                    }*/

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    boolean validateAllField(){
         if (FieldStaffApp.getInstance().isEdtTxtEmpty(edtxtPlantHeight)){
            FieldStaffApp.showDialog(ExistingPlantDetailsActivity.this,getResources().getString(R.string.empty_plant_height));
            return false;
        }else if (FieldStaffApp.getInstance().isEdtTxtEmpty(edtxtPlantCondition)){
            FieldStaffApp.showDialog(ExistingPlantDetailsActivity.this,getResources().getString(R.string.empty_plant_condition));
            return false;
        }else if (FieldStaffApp.getInstance().isEdtTxtEmpty(edtxtPlantProtection)){
            FieldStaffApp.showDialog(ExistingPlantDetailsActivity.this,getResources().getString(R.string.empty_plant_protection));
            return false;
        }
        return true;

    }
}
