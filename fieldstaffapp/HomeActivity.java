package com.fieldstaffapp.fieldstaffapp;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fieldstaffapp.fieldstaffapp.background_service.SMessageService;
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnViewexistingPlant;
    private Button btnNewPlant;
    private String areaType;
    private String municipalityName;
    private String municipalityId;
    private String wordName;
    private String wordId;
    private TextView tv_marque;
    private String imeiId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageView backImgView = (ImageView) findViewById(R.id.backImgView);
        TextView titleTxtView = (TextView) findViewById(R.id.titleTxtView);
        backImgView.setOnClickListener(this);
        btnViewexistingPlant=(Button)findViewById(R.id.btnViewexistingPlant);
        btnNewPlant=(Button)findViewById(R.id.btnNewPlant);
        tv_marque=(TextView)findViewById(R.id.tv_marque);

        tv_marque.setText(getResources().getString(R.string.welcome_note));
        tv_marque.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tv_marque.setSingleLine(true);
        tv_marque.setMarqueeRepeatLimit(5);
        tv_marque.setSelected(true);
        btnViewexistingPlant.setOnClickListener(this);

        btnNewPlant.setOnClickListener(this);
        titleTxtView.setText("Select Geotagging");
        getAllIntentValue();

    }
    private void getAllIntentValue() {
        areaType = getIntent().getStringExtra(FieldStaffApp.KEY_AREA_TYPE);
        municipalityName= getIntent().getStringExtra(FieldStaffApp.KEY_CORP_OR_MUNICIPALITY_NAME);
        municipalityId = getIntent().getStringExtra(FieldStaffApp.KEY_CORP_OR_MUNICIPALITY_ID);
        wordName = getIntent().getStringExtra(FieldStaffApp.KEY_DIV_OR_WORD_NAME);
        wordId = getIntent().getStringExtra(FieldStaffApp.KEY_DIV_OR_WORD_ID);
        imeiId=getIntent().getStringExtra(FieldStaffApp.KEY_IMEI_ID);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNewPlant:
                Intent intent=new Intent(HomeActivity.this,FormOneActivity.class);
                intent.putExtra(FieldStaffApp.KEY_AREA_TYPE,areaType);
                    intent.putExtra(FieldStaffApp.KEY_CORP_OR_MUNICIPALITY_NAME,municipalityName);
                    intent.putExtra(FieldStaffApp.KEY_CORP_OR_MUNICIPALITY_ID,municipalityId);
                    intent.putExtra(FieldStaffApp.KEY_DIV_OR_WORD_NAME,wordName);
                    intent.putExtra(FieldStaffApp.KEY_DIV_OR_WORD_ID,wordId);
                     intent.putExtra(FieldStaffApp.KEY_IMEI_ID, imeiId);
                startActivity(intent);

                break;
            case R.id.btnViewexistingPlant:
                Intent intent1 =new Intent(HomeActivity.this,ExistingPlantActivity.class);
                intent1.putExtra(FieldStaffApp.KEY_TAGGING,"2");
                startActivity(intent1);
                break;
            case R.id.backImgView:
                showDialogActivityFinish(getResources().getString(R.string.close_application));
                break;

        }

    }
    public  void showDialogActivityFinish(String msg) {
        new AlertDialog.Builder(HomeActivity.this).setMessage(msg).setCancelable(true)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                }).show();
    }

    @Override
    public void onBackPressed() {
        showDialogActivityFinish(getResources().getString(R.string.close_application));
    }
}
