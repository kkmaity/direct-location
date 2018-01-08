package com.fieldstaffapp.fieldstaffapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fieldstaffapp.fieldstaffapp.R;
import com.fieldstaffapp.fieldstaffapp.model.plant_name.PlantInfo;

import java.util.List;

public class PlantNameSpinnerAdapter extends ArrayAdapter<PlantInfo> {

    private final Context _context;
    private final int resourceId;
    private final List<PlantInfo> dataList;

    public PlantNameSpinnerAdapter(Context context, int resource, List<PlantInfo> objects) {
        super(context, resource, objects);
        _context = context;
        resourceId = resource;
        dataList = objects;
    }


    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(_context).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.nameTxtView = (TextView) convertView.findViewById(R.id.tittleTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.nameTxtView.setText(dataList.get(position).getName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(_context).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.nameTxtView = (TextView) convertView.findViewById(R.id.tittleTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.nameTxtView.setText(dataList.get(position).getName());
        return convertView;
    }

    class ViewHolder {
        TextView nameTxtView;
    }
}
