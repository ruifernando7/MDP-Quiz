package com.example.rui.mdpquiz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private ArrayList<Order> data;
    private static RVClickListener myListener;

    public OrderAdapter(ArrayList<Order> data,RVClickListener rvcl) {
        myListener = rvcl;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.row_item_order, viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvQtyType.setText(data.get(i).getQty()+" "+data.get(i).getType());
        if(data.get(i).getToppings().isEmpty()) viewHolder.tvTopping.setText("with Toppings: -");
        else viewHolder.tvTopping.setText("with Toppings: "+ data.get(i).getToppings().toString().substring(1,data.get(i).getToppings().toString().length()-1));
        viewHolder.tvSubtotal.setText("Rp. "+String.valueOf(data.get(i).getSubtotal()));
    }

    @Override
    public int getItemCount() {
        return (data!=null)?data.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQtyType,tvTopping,tvSubtotal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQtyType = itemView.findViewById(R.id.textView_qty_type);
            tvTopping = itemView.findViewById(R.id.textView_toppings);
            tvSubtotal = itemView.findViewById(R.id.textView_subtotal);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myListener.recyclerViewClick(v,ViewHolder.this.getLayoutPosition());
                }
            });
        }
    }
}
