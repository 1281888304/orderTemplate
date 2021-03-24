package com.example.ordertemplate.ui.topping;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ordertemplate.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecylerAdapter extends RecyclerView.Adapter<RecylerAdapter.ViewHolder> {

    private static final String tag = "RecylerView";
    private Context mContext;
    private ArrayList<Toppings> toppingList;
    private DatabaseReference myRef;

    private Context getmContext;

    public RecylerAdapter(Context mContext, ArrayList<Toppings> toppingList) {
        this.mContext = mContext;
        this.toppingList = toppingList;
    }



    @NonNull
    @Override
    public RecylerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //hold the view and put it on this template
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topping_item,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            //here is comment to click change layout and database

        String currentTopping=toppingList.get(position).getToppingTitle();
//
//
//
//        holder.toppingAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String numString=toppingList.get(position).getToppingNum();
//                int num=Integer.parseInt(numString);
//                num++;
//                numString=String.valueOf(num);
////                holder.toppingNum.setText(numString);
//
//                //now edit firebase
//                myRef=FirebaseDatabase.getInstance().getReference()
//                        .child("Product").child("Topping").child(currentTopping).child("toppingNum");
//                myRef.setValue(numString);
//            }
//        });
//
//
//
//        holder.toppingDrop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String numString=toppingList.get(position).getToppingNum();
//                int num=Integer.parseInt(numString);
//                num--;
//                numString=String.valueOf(num);
////                holder.toppingNum.setText(numString);
//
//                //now edit firebase
//                myRef=FirebaseDatabase.getInstance().getReference()
//                        .child("Product").child("Topping").child(currentTopping).child("toppingNum");
//                myRef.setValue(numString);
//            }
//        });

        //show the values
        holder.toppingName.setText(toppingList.get(position).getToppingName());
        holder.toppingPrice.setText(toppingList.get(position).getToppingPrice());
        holder.toppingNum.setText(toppingList.get(position).getToppingNum());
        holder.toppingTitle.setText(toppingList.get(position).getToppingTitle());


        //image:Glide Library
        Glide.with(mContext)
                .load(toppingList.get(position).getToppingImage())
                .into(holder.toppingImage);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),ToppingViewActivity.class);
                intent.putExtra("toppingKey",currentTopping);
                v.getContext().startActivities(new Intent[]{intent});

            }
        });


    }

    @Override
    public int getItemCount() {
        return toppingList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        //widget
        ImageView toppingImage;
        TextView toppingName;
        TextView toppingPrice;

        //btn
        private Button toppingAdd,toppingDrop;
        TextView toppingNum;
        TextView toppingTitle;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            toppingImage=itemView.findViewById(R.id.toppingImage);
            toppingName=itemView.findViewById(R.id.toppingName);
            toppingPrice=itemView.findViewById(R.id.toppingPrice);

            toppingNum=itemView.findViewById(R.id.toppingOrderNum);
            toppingAdd=itemView.findViewById(R.id.toppingShopAdd);
            toppingDrop=itemView.findViewById(R.id.toppingShopDelete);

            toppingTitle=itemView.findViewById(R.id.toppingTitle);

        }
    }

}
