package com.example.quang.appphonee.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quang.appphonee.Model.GioHang;
import com.example.quang.appphonee.Model.Server;
import com.example.quang.appphonee.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by quang on 5/6/19.
 */

public class ChiTietLichSuAdapter extends BaseAdapter {
    ArrayList<GioHang> arrayList;
    Context context;

    public ChiTietLichSuAdapter(ArrayList<GioHang> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        ImageView imgCTLSMH;
        TextView txtTenSP, txtGiaSP, txtSoLuong, txtTongGiaSP;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chitietlichsumuahang_dong, null);
            viewHolder.imgCTLSMH = convertView.findViewById(R.id.imgCTLSMH);
            viewHolder.txtTenSP = convertView.findViewById(R.id.tvTenCTLSMH);
            viewHolder.txtGiaSP = convertView.findViewById(R.id.tvGiaCTLSMH);
            viewHolder.txtSoLuong = convertView.findViewById(R.id.tvSoLuongCTLSMH);
            viewHolder.txtTongGiaSP = convertView.findViewById(R.id.tvTongGiaCTLSMH);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GioHang gioHang = arrayList.get(position);
        Picasso.with(context).load(Server.localhostimage + gioHang.getHinhAnhSP()).into(viewHolder.imgCTLSMH);
        viewHolder.txtTenSP.setText(gioHang.getTenSP());
        DecimalFormat df = new DecimalFormat("###,###,###");
        viewHolder.txtGiaSP.setText("Giá: " + df.format(gioHang.getGiaSP()) + " VNĐ/1");
        viewHolder.txtSoLuong.setText("Số lượng: " + gioHang.getSoLuong());
        viewHolder.txtTongGiaSP.setText("Tổng giá: " + df.format(gioHang.getTongGiaSP()) + " VNĐ");
        return convertView;
    }
}
