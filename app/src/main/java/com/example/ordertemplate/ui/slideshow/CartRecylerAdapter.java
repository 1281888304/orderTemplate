package com.example.ordertemplate.ui.slideshow;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ordertemplate.CartProduct;
import com.example.ordertemplate.R;
import com.example.ordertemplate.ui.topping.ToppingViewActivity;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class CartRecylerAdapter extends RecyclerView.Adapter<CartRecylerAdapter.ViewHolder> {

    //field
    private static final String tag = "RecylerView";
    private Context mContext;
    private ArrayList<CartProduct> cartList;
    private DatabaseReference myRef;

    public CartRecylerAdapter(Context mContext, ArrayList<CartProduct> cartList) {
        this.mContext = mContext;
        this.cartList = cartList;
    }



    @NonNull
    @Override
    public CartRecylerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_cart_item,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        holder.toppingName.setText(toppingList.get(position).getToppingName());
        holder.cartProductName.setText("Item Name: "+cartList.get(position).getCartProductName());
        holder.cartProductPrice.setText("Item Price: "+cartList.get(position).getCartProductPrice());
        holder.cartProductNum.setText("Order Number: "+cartList.get(position).getCartProductNum());

        String numHelper=cartList.get(position).getCartProductNum();

        String cureentTitle=cartList.get(position).getCartProductTitle();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cureentTitle.startsWith("t") || cureentTitle.startsWith("T")){
                    Intent intent=new Intent(v.getContext(), ToppingViewActivity.class);
                    intent.putExtra("productKey",cureentTitle);
                    intent.putExtra("productNum",numHelper);
                    v.getContext().startActivities(new Intent[]{intent});
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //widget

        TextView cartProductName;
        TextView cartProductNum;
        TextView cartProductPrice;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cartProductName=itemView.findViewById(R.id.cart_ProductName);
            cartProductNum=itemView.findViewById(R.id.cart_Productnum);
            cartProductPrice=itemView.findViewById(R.id.cart_productPrice);

        }
    }
}
