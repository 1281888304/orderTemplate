package com.example.ordertemplate.ui.topping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ordertemplate.CartProduct;
import com.example.ordertemplate.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;



public class ToppingViewActivity extends AppCompatActivity {

    private ImageView toppingImageClick;
    private TextView toppingNameClick;
    private TextView toppingPriceClick;

    //btn
    private Button toppingAddClick,toppingDropClick;
    private TextView toppingNumClick;
    private TextView toppingTitleClick;

    private DatabaseReference myRef; //use to change data

    private View toppingClickView;

    private DatabaseReference ref,cartRef;

    private String orderNumString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topping_view);

        toppingImageClick=findViewById(R.id.toppingImageClick);
        toppingNameClick=findViewById(R.id.toppingNameClick);
        toppingPriceClick=findViewById(R.id.toppingPriceClick);
        toppingAddClick=findViewById(R.id.toppingShopAddClick);
        toppingDropClick=findViewById(R.id.toppingShopDeleteClick);
        toppingNumClick=findViewById(R.id.toppingOrderNumClick);
        toppingTitleClick=findViewById(R.id.toppingTitleCLick);



        //firebase
        //show the topping data
        ref= FirebaseDatabase.getInstance().getReference().child("Product").child("Topping");
        //cart data
        cartRef=FirebaseDatabase.getInstance().getReference().child("Cart");


        // the currentTopping
        String toppingTitle =getIntent().getStringExtra("productKey");
        String currentTopping=toppingTitle;



        if(getIntent().getStringExtra("productNum").isEmpty() ||
                getIntent().getStringExtra("productNum")==null){
            orderNumString="0";
        }else{
            orderNumString=getIntent().getStringExtra("productNum");
        }
        //get the data
        getData(toppingTitle,orderNumString);

        //Add here
        toppingAddClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num=Integer.parseInt(orderNumString);
                num++;
                orderNumString=String.valueOf(num);
                getData(toppingTitle,orderNumString);
            }
        });

        toppingDropClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num=Integer.parseInt(orderNumString);
                if(num>0){
                    num--;
                }
                orderNumString=String.valueOf(num);
                getData(toppingTitle,orderNumString);
            }
        });
    }

    private void getData(String toppingTitle, String orderNumString) {
        //get data here
        ref.child(toppingTitle).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String imageUrl=dataSnapshot.child("toppingImage").getValue().toString();
                    String name=dataSnapshot.child("toppingName").getValue().toString();

                    String price=dataSnapshot.child("toppingPrice").getValue().toString();
                    String title=dataSnapshot.child("toppingTitle").getValue().toString();

                    //set value
                    Picasso.get().load(imageUrl).into(toppingImageClick);
                    toppingNameClick.setText(name);
                    toppingNumClick.setText(orderNumString);
                    toppingPriceClick.setText(price);
                    toppingTitleClick.setText(title);

                    int num=Integer.parseInt(orderNumString);
                    String numString=String.valueOf(num);
                    if(num>0){
                        //add to cart list firebase object
                        // first thing is check if it is exist
                        cartRef=FirebaseDatabase.getInstance().getReference()
                                .child("Cart").child("1");
                        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.hasChild(name)){
                                    cartRef.child(name).child("cartProductNum")
                                            .setValue(numString);
                                }else{
                                    //now add a new object into it
                                    cartRef=FirebaseDatabase.getInstance().getReference()
                                            .child("Cart");
                                    CartProduct cart=new CartProduct(name,numString,price,title);
                                    cartRef.child("1").child(title).setValue(cart);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    //delete if num<0
                    if(num==0){
                        cartRef.child("1").child(title).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}