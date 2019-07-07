package com.example.quang.appphonee.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quang.appphonee.ChiTietSanPhamActivity;
import com.example.quang.appphonee.Model.Sanpham;
import com.example.quang.appphonee.Model.Server;
import com.example.quang.appphonee.Model.sanphamtimkiem;
import com.example.quang.appphonee.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

/**
 * Created by quang on 5/13/19.
 */

public class FindAdpater extends RecyclerView.Adapter<FindAdpater.ItemHolder> {
    ArrayList<sanphamtimkiem> sanPhamArrayList;
    Context context;

    public FindAdpater(ArrayList<sanphamtimkiem> sanPhamArrayList, Context context) {
        this.sanPhamArrayList = sanPhamArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sanphamtimkiem_dong, viewGroup,false);

        return new ItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        sanphamtimkiem sanPham = sanPhamArrayList.get(i);
        Picasso.with(context).load(Server.localhostimage + sanPham.getHinhanhSP()).into(itemHolder.imgSPmoinhat);

        itemHolder.txtTenSP.setText(sanPham.getTenSP());
        DecimalFormat df = new DecimalFormat("###,###,###");
        itemHolder.txtTenSP.setMaxLines(1);
        itemHolder.txtTenSP.setEllipsize(TextUtils.TruncateAt.END);
        itemHolder.txtGiaSP.setText(df.format(sanPham.getGiaSP()) + " VNĐ");

        //itemHolder.imgSPmoinhat.setText(sanPham.getHinhanhSP());
    }

    @Override
    public int getItemCount() {
        return sanPhamArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        ImageView imgSPmoinhat;
        TextView txtTenSP, txtGiaSP;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgSPmoinhat = itemView.findViewById(R.id.imagetimkiem);
            txtTenSP = itemView.findViewById(R.id.tvtentimkiem);
            txtGiaSP = itemView.findViewById(R.id.tvgiatimkiem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    intent.putExtra("ChiTiet", sanPhamArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
