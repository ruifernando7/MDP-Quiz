package com.example.rui.mdpquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter<Order> {
    private final Context context;
    private final ArrayList<Order> data;

    public OrderAdapter(Context context, ArrayList<Order> data) {
        super(context, -1, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_item_order, parent, false);
        TextView tvQtyType = rowView.findViewById(R.id.textView_qty_type);
        TextView tvTopping = rowView.findViewById(R.id.textView_toppings);
        TextView tvSubtotal = rowView.findViewById(R.id.textView_subtotal);
        tvQtyType.setText(data.get(position).getQty()+" "+data.get(position).getType());
        tvTopping.setText(data.get(position).getToppings().toString());
        tvSubtotal.setText(String.valueOf(data.get(position).getSubtotal()));
        return rowView;
    }
}
