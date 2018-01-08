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
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fieldstaffapp.fieldstaffapp.adapter.CustomSpinnerAdapter;
import com.fieldstaffapp.fieldstaffapp.adapter.PlantNameSpinnerAdapter;
import com.fieldstaffapp.fieldstaffapp.model.new_tagging.NewTaggingMamin;
import com.fieldstaffapp.fieldstaffapp.model.plant_name.PlantInfo;
import com.fieldstaffapp.fieldstaffapp.model.plant_name.PlantNameMain;
import com.fieldstaffapp.fieldstaffapp.preference.CustomPrefs;
import com.fieldstaffapp.fieldstaffapp.restservice.RestService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormOneActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edtxtCorporationMunicipality,edtxtDivisionWard,edtxtPlantName,
            edtxtPlantHeight,edtxtPlantProtection,edtxtPlantStreetAddress,
            edtxtPlantAdapter,edtxtAdpaterPhoneNumber;
    private Button btnSaveNext;
    private TextView txtvwCorporationID;
    private EditText edtxtPlantationType;
    private EditText edtxtBlockPlantationType;
    private PopupWindow popupWindow;
    private ListView listViewService;
    private CustomSpinnerAdapter adapterService;
    private PlantNameSpinnerAdapter adapterPlant;
    private ProgressDialog progressDialog;
    private List<PlantInfo> plantInfo;
    private EditText edtxtPlantCondition;
    private RelativeLayout rlLine;
    private String areaType;
    private String municipalityName;
    private String municipalityId;
    private String wordName;
    private String wordId;
    private String wordOrDivision="W";
    private String plantId;
    private Button btnCancel;
    private String imeiNo;
    private String imeiId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_one);
        initView();

    }

    private void initView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        imeiNo= CustomPrefs.readString(FormOneActivity.this,FieldStaffApp.USER_IMEI_NO,"");
        ImageView backImgView = (ImageView) findViewById(R.id.backImgView);
        TextView titleTxtView = (TextView) findViewById(R.id.titleTxtView);
        titleTxtView.setText("New Plant Geotagging");
        backImgView.setOnClickListener(this);
        popupWindow=new PopupWindow();
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        getPlantinfo();
        txtvwCorporationID=(TextView)findViewById(R.id.txtvwCorporationID);
        edtxtDivisionWard=(EditText)findViewById(R.id.edtxtDivisionWard);
        rlLine=(RelativeLayout)findViewById(R.id.rlLine);
        edtxtPlantCondition=(EditText)findViewById(R.id.edtxtPlantCondition);
        edtxtBlockPlantationType=(EditText)findViewById(R.id.edtxtBlockPlantationType);
        edtxtPlantationType=(EditText)findViewById(R.id.edtxtPlantationType);
        edtxtPlantName=(EditText)findViewById(R.id.edtxtPlantName);
        edtxtPlantHeight=(EditText)findViewById(R.id.edtxtPlantHeight);
        edtxtPlantProtection=(EditText)findViewById(R.id.edtxtPlantProtection);
        edtxtPlantStreetAddress=(EditText)findViewById(R.id.edtxtPlantStreetAddress);
        edtxtPlantAdapter=(EditText)findViewById(R.id.edtxtPlantAdapter);
        edtxtAdpaterPhoneNumber=(EditText)findViewById(R.id.edtxtAdpaterPhoneNumber);
        btnSaveNext=(Button)findViewById(R.id.btnSaveNext);
        btnCancel=(Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        edtxtPlantName.setOnClickListener(this);
        edtxtPlantProtection.setOnClickListener(this);
        edtxtPlantCondition.setOnClickListener(this);
        btnSaveNext.setOnClickListener(this);
        edtxtPlantationType.setOnClickListener(this);
        edtxtBlockPlantationType.setOnClickListener(this);
        getAllIntentValue();



    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backImgView:
                finish();
                break;
            case R.id.edtxtPlantName:
                if (plantInfo.size()>0)
                plantnameDropDown(edtxtPlantName,plantInfo);
                break;
            case R.id.edtxtPlantProtection:
                hideSoftKeyboard();
                commonDropdownPopupWindow(edtxtPlantProtection,FieldStaffApp.plantProtectionList);
                break;
            case R.id.edtxtPlantationType:
                hideSoftKeyboard();
                commonDropdownPopupWindow(edtxtPlantationType,FieldStaffApp.plantationTypeList);

                break;
            case R.id.edtxtBlockPlantationType:
                hideSoftKeyboard();
                commonDropdownPopupWindow(edtxtBlockPlantationType,FieldStaffApp.blockPlantationList);
                break;
            case R.id.btnSaveNext:
                if (validateAllField()){
                    sendInfoToServer();
                }
                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.edtxtPlantCondition:
                hideSoftKeyboard();
                commonDropdownPopupWindow(edtxtPlantCondition,FieldStaffApp.PlantationConditionList);
                break;
        }

    }

    private void sendInfoToServer() {
        if (FieldStaffApp.getInstance().isConnectionPossible()){
            progressDialog.show();
            Call<NewTaggingMamin> call=RestService.getInstance().restInterface.postNewTaggingOne(FieldStaffApp.FROM_APP,
                    FieldStaffApp.APP_TYPE,"NEWGEOTAGGING",imeiId,municipalityId,wordId,plantId,edtxtPlantHeight.getText().toString(),
                    edtxtPlantCondition.getText().toString(),edtxtPlantProtection.getText().toString(),
                    edtxtPlantationType.getText().toString(),edtxtPlantStreetAddress.getText().toString(),
                    edtxtPlantAdapter.getText().toString(),edtxtAdpaterPhoneNumber.getText().toString(),
                    areaType,wordOrDivision,edtxtBlockPlantationType.getText().toString());
            call.enqueue(new Callback<NewTaggingMamin>() {
                @Override
                public void onResponse(Call<NewTaggingMamin> call, Response<NewTaggingMamin> response) {
                    if (response.body().getIsSuccess()){
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                        Intent intent=new Intent(FormOneActivity.this,FormTwoActivity.class);
                        intent.putExtra(FieldStaffApp.KEY_PLANT_NO,response.body().getNewGeotaggingInfo().getPlantNo());
                        intent.putExtra(FieldStaffApp.KEY_PLANT_ID,response.body().getNewGeotaggingInfo().getPlantId());
                        intent.putExtra(FieldStaffApp.KEY_GEOTAGGING_ID,response.body().getNewGeotaggingInfo().getGeotaggingId());
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<NewTaggingMamin> call, Throwable t) {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            });



        }else
            FieldStaffApp.showDialog(FormOneActivity.this,getResources().getString(R.string.err_msg_internet));

    }

    private void getAllIntentValue() {
        areaType = getIntent().getStringExtra(FieldStaffApp.KEY_AREA_TYPE);
        if (areaType!=null&&areaType.equalsIgnoreCase("C")){
            wordOrDivision="D";
        }
        municipalityName= getIntent().getStringExtra(FieldStaffApp.KEY_CORP_OR_MUNICIPALITY_NAME);
        municipalityId = getIntent().getStringExtra(FieldStaffApp.KEY_CORP_OR_MUNICIPALITY_ID);
        wordName = getIntent().getStringExtra(FieldStaffApp.KEY_DIV_OR_WORD_NAME);
        wordId = getIntent().getStringExtra(FieldStaffApp.KEY_DIV_OR_WORD_ID);
        imeiId=getIntent().getStringExtra(FieldStaffApp.KEY_IMEI_ID);
        txtvwCorporationID.setText(municipalityName);
        edtxtDivisionWard.setText(wordName);
    }

    void commonDropdownPopupWindow(final EditText edtview, final List<String> category) {
        try {
            Display mDisplay = FormOneActivity.this.getWindowManager().getDefaultDisplay();
            final int width  = mDisplay.getWidth()-40;
            View content=View.inflate(FormOneActivity.this,R.layout.common_dropdown_list,null);
            popupWindow.setContentView(content);
            listViewService=(ListView)content.findViewById(R.id.listViewService);
            adapterService=new CustomSpinnerAdapter(FormOneActivity.this,R.layout.item_common_dropdown,category);
            listViewService.setAdapter(adapterService);
            popupWindow = new PopupWindow(content, width, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            popupWindow.showAsDropDown(edtview);

            listViewService.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    edtview.setText(category.get(i));
                    if (popupWindow!=null&&popupWindow.isShowing())
                        popupWindow.dismiss();
                    if(edtview.getText().toString().trim().equalsIgnoreCase("Block Plantation")){
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
                    }

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void plantnameDropDown(final EditText edtview, final List<PlantInfo> plantInfos) {
        try {
            Display mDisplay = FormOneActivity.this.getWindowManager().getDefaultDisplay();
            final int width  = mDisplay.getWidth()-40;
            View content=View.inflate(FormOneActivity.this,R.layout.common_dropdown_list,null);
            popupWindow.setContentView(content);
            listViewService=(ListView)content.findViewById(R.id.listViewService);
            adapterPlant=new PlantNameSpinnerAdapter(FormOneActivity.this,R.layout.item_common_dropdown,plantInfos);
            listViewService.setAdapter(adapterPlant);
            popupWindow = new PopupWindow(content, width, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            popupWindow.showAsDropDown(edtview);

            listViewService.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    edtview.setText(plantInfos.get(i).getName());
                    plantId=plantInfos.get(i).getId();
                    if (popupWindow!=null&&popupWindow.isShowing())
                        popupWindow.dismiss();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    void getPlantinfo(){
        if (FieldStaffApp.getInstance().isConnectionPossible()){
            progressDialog.show();
            Call<PlantNameMain> call= RestService.getInstance().restInterface.getPlantName(FieldStaffApp.FROM_APP,FieldStaffApp.APP_TYPE,"PLANT");
            call.enqueue(new Callback<PlantNameMain>() {
                @Override
                public void onResponse(Call<PlantNameMain> call, Response<PlantNameMain> response) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    if (response.body().getIsSuccess()){
                        plantInfo=response.body().getPlantInfo();
                    }else
                        FieldStaffApp.showDialog(FormOneActivity.this,response.body().getMessage());
                }

                @Override
                public void onFailure(Call<PlantNameMain> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            });
        }else
            FieldStaffApp.showDialog(FormOneActivity.this,getResources().getString(R.string.err_msg_internet));
    }
boolean validateAllField(){
    if (FieldStaffApp.getInstance().isEdtTxtEmpty(edtxtPlantName)){
        FieldStaffApp.showDialog(FormOneActivity.this,getResources().getString(R.string.empty_name));
        return false;
    }else if (FieldStaffApp.getInstance().isEdtTxtEmpty(edtxtPlantHeight)){
        FieldStaffApp.showDialog(FormOneActivity.this,getResources().getString(R.string.empty_plant_height));
        return false;
    }else if (FieldStaffApp.getInstance().isEdtTxtEmpty(edtxtPlantCondition)){
        FieldStaffApp.showDialog(FormOneActivity.this,getResources().getString(R.string.empty_plant_condition));
        return false;
    }else if (FieldStaffApp.getInstance().isEdtTxtEmpty(edtxtPlantProtection)){
        FieldStaffApp.showDialog(FormOneActivity.this,getResources().getString(R.string.empty_plant_protection));
        return false;
    }else if (FieldStaffApp.getInstance().isEdtTxtEmpty(edtxtPlantationType)){
        FieldStaffApp.showDialog(FormOneActivity.this,getResources().getString(R.string.empty_plantation_type));
        return false;
    }else if (FieldStaffApp.getInstance().isEdtTxtEmpty(edtxtPlantStreetAddress)){
        FieldStaffApp.showDialog(FormOneActivity.this,getResources().getString(R.string.empty_plant_street_add));
        return false;
    }else if (FieldStaffApp.getInstance().isEdtTxtEmpty(edtxtPlantAdapter)){
        FieldStaffApp.showDialog(FormOneActivity.this,getResources().getString(R.string.empty_adopter_name));
        return false;
    }else if (FieldStaffApp.getInstance().isEdtTxtEmpty(edtxtAdpaterPhoneNumber)){
        FieldStaffApp.showDialog(FormOneActivity.this,getResources().getString(R.string.empty_phone_number));
        return false;
    }else if (edtxtAdpaterPhoneNumber.length()<=7){
        FieldStaffApp.showDialog(FormOneActivity.this,getResources().getString(R.string.phone_number_character));
        return false;
    }
    return true;

}
}
