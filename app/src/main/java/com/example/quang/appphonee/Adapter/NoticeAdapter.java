package com.example.quang.appphonee.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quang.appphonee.Model.HopThu;
import com.example.quang.appphonee.Model.Server;
import com.example.quang.appphonee.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by quang on 5/11/19.
 */

public class NoticeAdapter extends BaseAdapter {

    ArrayList<HopThu> arrayList;
    Context context;

    public NoticeAdapter(ArrayList<HopThu> arrayList, Context context) {
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
        TextView txtMota, txtNgaygio;
        ImageView imgpro;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.noticefragment_dong, null);
            viewHolder.txtMota = convertView.findViewById(R.id.mota);
            viewHolder.txtNgaygio = convertView.findViewById(R.id.ngaygio);
            viewHolder.imgpro = convertView.findViewById(R.id.profile_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HopThu hopThu = arrayList.get(position);
        viewHolder.txtMota.setMaxLines(2);
        viewHolder.txtMota.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMota.setText(hopThu.getMoTa());
        viewHolder.txtNgaygio.setText(hopThu.getNgayGio());
        Picasso.with(context).load(Server.localhostimage + hopThu.getHinhAnh()).into(viewHolder.imgpro);

        return convertView;
    }
}
