package com.example.rui.mdpquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText edName;
    private RadioGroup rgType;
    private RadioButton rbTea,rbCoffee,rbSmoothies;
    private CheckBox cbPearl,cbPudding,cbReadBean,cbCoconut;
    private Button btnMinus,btnPlus,btnAdd,btnEdit,btnDelete,btnReset;
    private TextView txtQty, txtTotal, txtName;
    private RecyclerView rvOrder;
    private OrderAdapter adapter;
    private ArrayList<Order> arrOrder = new ArrayList<>();
    private long total = 0;
    private int index = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edName = findViewById(R.id.editText_name);
        rgType = findViewById(R.id.radioGroup_type);
        rbTea = findViewById(R.id.radioButton_tea);
        rbCoffee = findViewById(R.id.radioButton_coffee);
        rbSmoothies = findViewById(R.id.radioButton_smoothies);
        cbPearl = findViewById(R.id.cbPearl);
        cbPudding = findViewById(R.id.cbPudding);
        cbReadBean = findViewById(R.id.cbRedBean);
        cbCoconut = findViewById(R.id.cbCoconut);
        btnMinus = findViewById(R.id.button_minus);
        btnPlus = findViewById(R.id.button_plus);
        txtQty = findViewById(R.id.textView_qty);
        btnAdd = findViewById(R.id.button_add);
        btnDelete = findViewById(R.id.button_delete);
        btnReset = findViewById(R.id.button_reset);
        rvOrder = findViewById(R.id.recyclerview_order);
        txtName = findViewById(R.id.textView_name);
        txtTotal = findViewById(R.id.textView_total);
        adapter = new OrderAdapter(arrOrder, new RVClickListener() {
            @Override
            public void recyclerViewClick(View v, int posisi) {
                index = posisi;
                cbCoconut.setChecked(false);
                cbReadBean.setChecked(false);
                cbPudding.setChecked(false);
                cbPearl.setChecked(false);
                //radio button
                if(arrOrder.get(posisi).getType().equals("Tea")) rbTea.setChecked(true);
                else if(arrOrder.get(posisi).getType().equals("Coffee")) rbCoffee.setChecked(true);
                else rbSmoothies.setChecked(true);
                //checkbox
                ArrayList<String> temp  = arrOrder.get(posisi).getToppings();
                for(int i=0;i<temp.size();i++){
                    if(temp.get(i).equals("Pearl")) cbPearl.setChecked(true);
                    else if(temp.get(i).equals("Pudding")) cbPudding.setChecked(true);
                    else if(temp.get(i).equals("Pudding")) cbPudding.setChecked(true);
                    else if(temp.get(i).equals("Red Bean")) cbReadBean.setChecked(true);
                    else if(temp.get(i).equals("Coconut Jelly")) cbCoconut.setChecked(true);
                }
            }
        });
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rvOrder.setLayoutManager(lm);
        rvOrder.setAdapter(adapter);
        total = 0;

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = Integer.parseInt(txtQty.getText().toString()) - 1;
                if(temp >= 1) txtQty.setText(String.valueOf(temp));
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp =  Integer.parseInt(txtQty.getText().toString()) + 1;
                txtQty.setText(String.valueOf(temp));
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edName.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Field Name cannot be empty",Toast.LENGTH_LONG).show();
                }else{
                    txtName.setText(edName.getText().toString());
                    int subtotal = 0;
                    String tipe = "";
                    ArrayList<String> arrTopping = new ArrayList<>();
                    if(rbTea.isChecked()){
                        subtotal += 23000;
                        tipe = "Tea";
                    }else if(rbCoffee.isChecked()){
                        subtotal += 25000;
                        tipe = "Coffee";
                    }else{
                        subtotal += 30000;
                        tipe = "Smoothies";
                    }
                    if(cbPearl.isChecked()){
                        subtotal += 3000;
                        arrTopping.add("Pearl");
                    }
                    if(cbPudding.isChecked()){
                        subtotal += 3000;
                        arrTopping.add("Pudding");
                    }
                    if(cbReadBean.isChecked()){
                        subtotal += 4000;
                        arrTopping.add("Red Bean");
                    }
                    if(cbCoconut.isChecked()){
                        subtotal += 4000;
                        arrTopping.add("Coconut Jelly");
                    }
                    int qty = Integer.parseInt(txtQty.getText().toString());
                    subtotal *= qty;
                    arrOrder.add(new Order(tipe,arrTopping,qty,subtotal));
                    adapter.notifyDataSetChanged();
                    total += subtotal;
                    txtTotal.setText(String.valueOf(total));
                    //Clear
                    rbTea.setChecked(true);
                    cbCoconut.setChecked(false);
                    cbReadBean.setChecked(false);
                    cbPudding.setChecked(false);
                    cbPearl.setChecked(false);
                    txtQty.setText("1");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index != -1){
                    total -= arrOrder.get(index).getSubtotal();
                    txtTotal.setText(String.valueOf(total));
                    arrOrder.remove(index);
                    adapter.notifyDataSetChanged();
                    index = -1;
                    //Clear
                    rbTea.setChecked(true);
                    cbCoconut.setChecked(false);
                    cbReadBean.setChecked(false);
                    cbPudding.setChecked(false);
                    cbPearl.setChecked(false);
                    txtQty.setText("1");
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edName.setText("");
                rbTea.setChecked(true);
                cbCoconut.setChecked(false);
                cbReadBean.setChecked(false);
                cbPudding.setChecked(false);
                cbPearl.setChecked(false);
                txtQty.setText("1");
                arrOrder.clear();
                adapter.notifyDataSetChanged();
                txtName.setText("Cust");
                total = 0;
                txtTotal.setText("0");
            }
        });

    }
}
