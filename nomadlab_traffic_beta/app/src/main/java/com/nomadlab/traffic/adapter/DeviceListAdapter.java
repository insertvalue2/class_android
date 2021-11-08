package com.nomadlab.traffic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nomadlab.traffic.R;
import com.nomadlab.traffic.models.DeviceInfo;

import java.util.ArrayList;

import utils.OnItemClickListener;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ListViewHolder> {

    private ArrayList<DeviceInfo> deviceInfoArrayList;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView deviceName;
        TextView macAddress;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceName = itemView.findViewById(R.id.device_name);
            macAddress = itemView.findViewById(R.id.mac_address);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(deviceName.getText().toString(),
                            macAddress.getText().toString());
                }
            });
        }

        public void setListData(DeviceInfo data) {
            deviceName.setText(data.getName());
            macAddress.setText(data.getMacAddress());
        }
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_device, parent, false);
        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        DeviceInfo deviceInfo = deviceInfoArrayList.get(position);
        holder.setListData(deviceInfo);
    }

    @Override
    public int getItemCount() {
        return deviceInfoArrayList.size();
    }

    public void setData(ArrayList<DeviceInfo> list) {
        deviceInfoArrayList = list;
    }

}
