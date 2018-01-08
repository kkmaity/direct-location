package com.fieldstaffapp.fieldstaffapp.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fieldstaffapp.fieldstaffapp.ExistingPlantDetailsActivity;
import com.fieldstaffapp.fieldstaffapp.FieldStaffApp;
import com.fieldstaffapp.fieldstaffapp.R;
import com.fieldstaffapp.fieldstaffapp.model.existing_plant.GeotaggingDetailsInfo;
import com.fieldstaffapp.fieldstaffapp.model.plant_name.PlantNameMain;
import java.util.List;

public class ExistingPlantRecyclerAdapter extends RecyclerView.Adapter<ExistingPlantRecyclerAdapter.MessageViewHolder> {

    private List<GeotaggingDetailsInfo> dataList;
    private Context _context;
    // private ClickHandler _interface;

    public ExistingPlantRecyclerAdapter(Context context, List<GeotaggingDetailsInfo> objects){
        _context = context;
        dataList = objects;
        // _interface = clickHandler;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_existing_plant_list, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        final int position1=position;
        holder.txtvwPlantId.setText(dataList.get(position1).getPlantNo().toString());
        holder.txtvwPlantName.setText(dataList.get(position1).getPlantName());
        //  holder.bookingIdTxtView.setText(dataList.get(position).getJobId());
        holder.rootLayout.setTag(position1);
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object obj=v.getTag();

                Intent intent=new Intent(_context, ExistingPlantDetailsActivity.class);
                intent.putExtra(FieldStaffApp.KEY_EXISTING_PLANT_IMAGE_ID,dataList.get(position1).getTreeImageId());
                intent.putExtra(FieldStaffApp.KEY_EXISTING_PLANT_ID,dataList.get(position1).getPlantId());
                intent.putExtra(FieldStaffApp.KEY_EXISTING_PLANT_NAME,dataList.get(position1).getPlantName());
                intent.putExtra(FieldStaffApp.KEY_EXISTING_PLANT_NO,dataList.get(position1).getPlantNo());
                intent.putExtra(FieldStaffApp.KEY_EXISTING_GEOTAGGING_ID,dataList.get(position1).getGeotaggingId());
                intent.putExtra(FieldStaffApp.KEY_EXISTING_PLANT_STREET_ADDRESS,dataList.get(position1).getPlantStreetAddress());
                intent.putExtra(FieldStaffApp.KEY_EXISTING_PLANT_HEIGHT,dataList.get(position1).getPlantHeight());
                intent.putExtra(FieldStaffApp.KEY_EXISTING_PLANT_CONDITION,dataList.get(position1).getPlantCondition());
                intent.putExtra(FieldStaffApp.KEY_EXISTING_PLANT_PROTECTION,dataList.get(position1).getPlantProtection());
                intent.putExtra(FieldStaffApp.KEY_EXISTING_PLANTATION_TYPE,dataList.get(position1).getPlantationType());
                intent.putExtra(FieldStaffApp.KEY_EXISTING_PLANTATION_BLOCK,dataList.get(position1).getBlockPlantation());
                intent.putExtra(FieldStaffApp.KEY_EXISTING_PLANTAT_ADOPTER,dataList.get(position1).getPlantAdopter());
                intent.putExtra(FieldStaffApp.KEY_EXISTING_PLANTAT_ADOPTER_PHONE,dataList.get(position1).getAdopterPhone());
                _context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder{

        private final TextView txtvwPlantId;
        private final TextView txtvwPlantName;
        private final LinearLayout rootLayout;

        public MessageViewHolder(View itemView) {
            super(itemView);
            txtvwPlantId = (TextView) itemView.findViewById(R.id.txtvwPlantId);
            txtvwPlantName = (TextView) itemView.findViewById(R.id.txtvwPlantName);
            rootLayout=(LinearLayout)itemView.findViewById(R.id.rootLayout);

        }
    }
}
