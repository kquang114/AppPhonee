package com.example.quang.appphonee.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quang.appphonee.Model.LichSuMuaHang;
import com.example.quang.appphonee.R;

import java.util.ArrayList;

/**
 * Created by quang on 5/8/19.
 */

public class LichSuMuaHangAdapter extends BaseAdapter {
    ArrayList<LichSuMuaHang> arrayList;
    Context context;

    public LichSuMuaHangAdapter(ArrayList<LichSuMuaHang> arrayList, Context context) {
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

    class ViewHoler {
        TextView txtmaDH, txtNgayMuaHang, txtTen, txtDiaChi, txtSDT, txtTinhTrang;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler viewHoler = null;
        if(viewHoler == null) {
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lichsumuahang_dong, null);
            viewHoler.txtmaDH = convertView.findViewById(R.id.tvMaDonHang);
            viewHoler.txtNgayMuaHang = convertView.findViewById(R.id.tvNgayMuaHang);
            viewHoler.txtTen = convertView.findViewById(R.id.tvHoTen);
            viewHoler.txtDiaChi = convertView.findViewById(R.id.tvDiaChi);
            viewHoler.txtSDT = convertView.findViewById(R.id.tvSDT);
            viewHoler.txtTinhTrang = convertView.findViewById(R.id.tvTinhTrang);
            convertView.setTag(viewHoler);
        }
        else {
            viewHoler = (ViewHoler) convertView.getTag();
        }

        LichSuMuaHang lichSuMuaHang = arrayList.get(position);
        viewHoler.txtmaDH.setText("Mã đơn hàng: #" + lichSuMuaHang.getMaDonHang());
        viewHoler.txtNgayMuaHang.setText("Ngày mua hàng: " + lichSuMuaHang.getNgayMuaHang());
        viewHoler.txtTen.setText("Tên người nhận: " + lichSuMuaHang.getTenNguoiNhan());
        viewHoler.txtDiaChi.setText("Địa chỉ: " + lichSuMuaHang.getDiaChi());
        viewHoler.txtSDT.setText("SĐT: " + lichSuMuaHang.getSdt());
        viewHoler.txtTinhTrang.setText("Tình trạng đơn hàng: " + lichSuMuaHang.getTinhTrang());
        return convertView;
    }
}
