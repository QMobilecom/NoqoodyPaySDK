package com.qmobileme.noqoodypay.network.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qmobileme.noqoodypay.R;
import com.qmobileme.noqoodypay.network.model.PaymentChannel;

import java.util.List;

/*
 * Created by saneebsalam
 * on 30/09/2020.
 */
public class Adapter_PaymentList extends RecyclerView.Adapter<Adapter_PaymentList.ViewHolder> {

    private final Context mContext;
    ClickListener clickListener;
    int position = -1;
    private List<PaymentChannel> item_List;

    public Adapter_PaymentList(Context context, List<PaymentChannel> item_List) {
        this.item_List = item_List;
        this.mContext = context;
    }

    public void setOnClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        if (position == this.position) {
            holder.tick.setVisibility(View.VISIBLE);
            holder.rv_image.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.NoqoodyPaycolorPrimary));
        } else {
            holder.tick.setVisibility(View.INVISIBLE);
            holder.rv_image.setBackgroundTintList(null);
        }

        holder.name.setText(Html.fromHtml(item_List.get(position).getChannelName()));
        Glide.with(mContext)
                .load(item_List.get(position).getImageLocation())
                .apply(RequestOptions.placeholderOf(R.drawable.noqoodypay_logo).error(R.drawable.noqoodypay_logo))
                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return item_List.size();
    }

    public void setResults(List<PaymentChannel> item_List) {
        this.item_List = item_List;
        notifyDataSetChanged();
    }

    public interface ClickListener {
        void onClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image, tick;
        RelativeLayout rv_image;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.noqoodypay_item_paymentlist, parent, false));
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
            tick = itemView.findViewById(R.id.tick);
            rv_image = itemView.findViewById(R.id.rv_image);

            itemView.setOnClickListener(v -> {
                clickListener.onClick(getAdapterPosition());
                position = getAdapterPosition();
                notifyDataSetChanged();

            });
        }
    }
}
