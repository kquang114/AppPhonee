package com.example.quang.appphonee.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quang.appphonee.Model.LoaiSP;
import com.example.quang.appphonee.Model.Server;
import com.example.quang.appphonee.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by quang on 4/12/19.
 */

public class LoaispAdapter extends BaseAdapter {
    ArrayList<LoaiSP> arrayLoaiSP;
    Context context;

    public LoaispAdapter(ArrayList<LoaiSP> arrayLoaiSP, Context context) {
        this.arrayLoaiSP = arrayLoaiSP;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayLoaiSP.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayLoaiSP.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        ImageView menuImage;
        TextView menuTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.menu_listview_row, null);
            viewHolder.menuImage = convertView.findViewById(R.id.menuimages);
            viewHolder.menuTitle = convertView.findViewById(R.id.menutitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LoaiSP loaiSP = (LoaiSP) getItem(position);
        Picasso.with(context).load(Server.localhostimage + loaiSP.getHinhanhLoaiSP()).into(viewHolder.menuImage);
        viewHolder.menuTitle.setText(loaiSP.getTenLoaiSP());
        return convertView;
    }
}
