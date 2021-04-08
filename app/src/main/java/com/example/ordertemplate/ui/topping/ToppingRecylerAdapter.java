package com.example.ordertemplate.ui.topping;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ordertemplate.CartProduct;
import com.example.ordertemplate.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ToppingRecylerAdapter extends RecyclerView.Adapter<ToppingRecylerAdapter.ViewHolder> {

    private static final String tag = "RecylerView";
    private Context mContext;
    private ArrayList<Toppings> toppingList;
    private DatabaseReference myRef, numRef;

    private Context getmContext;

    //number of current string
//    private String numhelper;

    public ToppingRecylerAdapter(Context mContext, ArrayList<Toppings> toppingList) {
        this.mContext = mContext;
        this.toppingList = toppingList;
    }


    @NonNull
    @Override
    public ToppingRecylerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //hold the view and put it on this template
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topping_item, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //here is comment to click change layout and database

        String currentTopping = toppingList.get(position).getToppingTitle();
        final String[] numhelper = {""};

        //show the values
        holder.toppingName.setText(toppingList.get(position).getToppingName());
        holder.toppingPrice.setText(toppingList.get(position).getToppingPrice());
//        holder.toppingNum.setText(toppingList.get(position).getToppingNum());
        holder.toppingTitle.setText(toppingList.get(position).getToppingTitle());

        String checkNum=toppingList.get(position).getToppingNum();
        if(checkNum.equals("0")){
            holder.toppingNum.setText("We are preparing");
        }else{
            holder.toppingNum.setText("Ready to order");
        }




        //image:Glide Library
        Glide.with(mContext)
                .load(toppingList.get(position).getToppingImage())
                .into(holder.toppingImage);

        //change to the child("username")
        numRef = FirebaseDatabase.getInstance().getReference()
                .child("Cart").child("1");
        numRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild(currentTopping)) {
                    numhelper[0] ="0";
                    return;
                }else{
//                    numRef.child(currentTopping).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            Map<String,Object> map=(Map<String, Object>) snapshot.getValue();
//                            Object number=map.get("cartProductNum");
//                            String num=String.valueOf(number);
//                            numhelper=num;
//                            return;
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
                                        //new method
                    for(DataSnapshot ds: snapshot.getChildren()){
                        Map<String,Object> map=(Map<String, Object>) ds.getValue();
                        Object title=map.get("cartProductTitle");
                        String currentTitle=String.valueOf(title);
                        if(currentTitle.equals(currentTopping)){
                            Object number=map.get("cartProductNum");
                            String num=String.valueOf(number);
                            numhelper[0] =num;
                            //stop the for loop
                            break;
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //bug on numberHelper //itemview.set
        if(!checkNum.equals("0")) {
            holder.toppingImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get intent
                    Intent intent = new Intent(v.getContext(), ToppingViewActivity.class);
                    intent.putExtra("productKey", currentTopping);
                    intent.putExtra("productNum", numhelper[0]);
                    v.getContext().startActivities(new Intent[]{intent});
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return toppingList.size();
    }

    public void filteredList(ArrayList<Toppings> filterList) {
        toppingList=filterList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        //widget
        ImageView toppingImage;
        TextView toppingName;
        TextView toppingPrice;

        //btn
        private Button toppingAdd, toppingDrop;
        TextView toppingNum;
        TextView toppingTitle;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            toppingImage = itemView.findViewById(R.id.toppingImage);
            toppingName = itemView.findViewById(R.id.toppingName);
            toppingPrice = itemView.findViewById(R.id.toppingPrice);

            toppingNum = itemView.findViewById(R.id.toppingOrderNum);
//            toppingAdd = itemView.findViewById(R.id.toppingShopAdd);
//            toppingDrop = itemView.findViewById(R.id.toppingShopDelete);

            toppingTitle = itemView.findViewById(R.id.toppingTitle);

        }
    }

}
