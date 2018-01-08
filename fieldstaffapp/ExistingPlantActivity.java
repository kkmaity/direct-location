package com.fieldstaffapp.fieldstaffapp;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fieldstaffapp.fieldstaffapp.adapter.ExistingPlantRecyclerAdapter;
import com.fieldstaffapp.fieldstaffapp.model.existing_plant.ExistingPlantMain;
import com.fieldstaffapp.fieldstaffapp.model.existing_plant.GeotaggingDetailsInfo;
import com.fieldstaffapp.fieldstaffapp.preference.CustomPrefs;
import com.fieldstaffapp.fieldstaffapp.restservice.RestService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExistingPlantActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerExistingPlant;
    private ExistingPlantRecyclerAdapter adapter;
    private ProgressDialog progressDialog;
    private List<GeotaggingDetailsInfo> existingPlantList=new ArrayList<>();
    private BroadcastReceiver mReceiver;
    private TextView txtvwNoItemFound;
    private String imeiNo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_plant);
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
        titleTxtView.setText("Existing Plant Geotagging");
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerExistingPlant=(RecyclerView)findViewById(R.id.recyclerExistingPlant);
        recyclerExistingPlant.setLayoutManager(layoutManager);
        txtvwNoItemFound=(TextView)findViewById(R.id.txtvwNoItemFound);
        imeiNo= CustomPrefs.readString(ExistingPlantActivity.this,FieldStaffApp.USER_IMEI_NO,"");
        progressDialog.show();

    }

    private void getExistingPlantList(final String lat,final String longi) {


        Call<ExistingPlantMain> call= RestService.getInstance().restInterface.getExistingPlantList(FieldStaffApp.FROM_APP,FieldStaffApp.APP_TYPE,"GEOTAGGINGLIST",imeiNo,lat,longi);
        call.enqueue(new Callback<ExistingPlantMain>() {
            @Override
            public void onResponse(Call<ExistingPlantMain> call, Response<ExistingPlantMain> response) {
                if (progressDialog!=null&&progressDialog.isShowing())
                    progressDialog.dismiss();

                if (response.body().getIsSuccess()){
                    existingPlantList=response.body().getGeotaggingDetailsInfo();
                    if (existingPlantList.size()>0){
                        txtvwNoItemFound.setVisibility(View.GONE);
                    }
                    adapter=new ExistingPlantRecyclerAdapter(ExistingPlantActivity.this,existingPlantList);
                    recyclerExistingPlant.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    if (existingPlantList.size()==0){
                        txtvwNoItemFound.setVisibility(View.VISIBLE);
                        txtvwNoItemFound.setText(getResources().getString(R.string.no_item_found));
                        txtvwNoItemFound.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                        txtvwNoItemFound.setSingleLine(true);
                        txtvwNoItemFound.setMarqueeRepeatLimit(5);
                        txtvwNoItemFound.setSelected(true);
                    }else
                    {
                        txtvwNoItemFound.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ExistingPlantMain> call, Throwable t) {
                if (progressDialog!=null&&progressDialog.isShowing())
                    progressDialog.dismiss();

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backImgView:
                finish();
                break;

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(
                "android.intent.action.MATCH_LOCATION");

        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                //extract our message from intent
                String msg_for_me = intent.getStringExtra("some_msg");
                String lat=intent.getStringExtra("latitude");
                String longi=intent.getStringExtra("longitude");
                //log our message value
                Log.i("Kamal Kanta..Receiver", msg_for_me);
             /*   ====================================================CHANGE WILL BE HERE====================================*/
                if (msg_for_me.equalsIgnoreCase("Plant found")){
                    if (FieldStaffApp.getInstance().isConnectionPossible()){
                        getExistingPlantList(lat,longi);

                    }else
                        FieldStaffApp.showDialog(ExistingPlantActivity.this,getResources().getString(R.string.err_msg_internet));
                }

            }
        };
        //registering our receiver
        this.registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(this.mReceiver);
    }


}
