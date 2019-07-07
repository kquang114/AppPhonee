package com.example.quang.appphonee.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quang.appphonee.Model.ItemDiscountFragment;
import com.example.quang.appphonee.Model.Sanpham;
import com.example.quang.appphonee.Model.Server;
import com.example.quang.appphonee.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by quang on 5/9/19.
 */

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.ViewHolder> {

    Context context;
    public List<ItemDiscountFragment> itemDiscountFragments;

    public SaleAdapter(Context context, List<ItemDiscountFragment> itemDiscountFragments) {
        this.context = context;
        this.itemDiscountFragments = itemDiscountFragments;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_discount, null);
        SaleAdapter.ViewHolder itemHolder = new SaleAdapter.ViewHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {

        ItemDiscountFragment item = itemDiscountFragments.get(i);
        Picasso.with(context).load(Server.localhostimage + item.getImgBack()).into(holder.imgBackground);
        //DecimalFormat df = new DecimalFormat("###,###,###");


    }

    @Override
    public int getItemCount() {
        return itemDiscountFragments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        Button btnXem;
        ImageView imgBackground,imgProfile;

        public ViewHolder(View itemView) {
            super(itemView);

            btnXem = itemView.findViewById(R.id.btn_discount);
            imgBackground = itemView.findViewById(R.id.image_view_backSale);
            imgProfile = itemView.findViewById(R.id.image_view_profile);
        }
    }

}
