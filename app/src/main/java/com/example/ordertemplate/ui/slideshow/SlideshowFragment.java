package com.example.ordertemplate.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ordertemplate.CartProduct;
import com.example.ordertemplate.R;
import com.example.ordertemplate.ui.topping.ToppingRecylerAdapter;
import com.example.ordertemplate.ui.topping.Toppings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class SlideshowFragment extends Fragment {

    //basic field
    private SlideshowViewModel slideshowViewModel;
    ArrayList<CartProduct> cartList;
    private DatabaseReference myRef,priceRef;

    //widget
    private RecyclerView cartRecyclerView;
    private TextView totalPrice;
    private Button contract;

    //adapter
    private CartRecylerAdapter cartRecylerAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);

        //add buttom here
        cartRecyclerView=view.findViewById(R.id.cartRecylerView);
        totalPrice=view.findViewById(R.id.cartTotalPrice);
        contract=view.findViewById(R.id.cart_BtnContract);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        cartRecyclerView.setLayoutManager(layoutManager);
        cartRecyclerView.setHasFixedSize(true);

        //firebase
        myRef= FirebaseDatabase.getInstance().getReference();

        clearAll();

        getData();

        countPrice();

        contract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private void countPrice() {

        priceRef=FirebaseDatabase.getInstance().getReference().child("Cart").child("1");
        priceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int sum=0;
                for (DataSnapshot ds : snapshot.getChildren()){
                    Map<String,Object> map=(Map<String, Object>) ds.getValue();
                    Object price =map.get("cartProductPrice");
                    //split the string
                    String s=String.valueOf(price);
                    String[] arrayString=s.split("/");
                    int priceValue=Integer.parseInt(String.valueOf(arrayString[0]));

                    //get the ordernumber
                    Map<String,Object> map2=(Map<String, Object>) ds.getValue();
                    Object num=map.get("cartProductNum");
                    String str=String.valueOf(num);
                    int orderNum=0;
                    orderNum=Integer.parseInt(str);



                    sum=sum+(priceValue*orderNum);
                }
                totalPrice.setText("Total Price is $"+String.valueOf(String.valueOf(sum)));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getData() {
        Query query=myRef.child("Cart").child("1");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clearAll();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    CartProduct cartProduct=new CartProduct();

                    cartProduct.setCartProductName(snapshot.child("cartProductName").getValue().toString());
                    cartProduct.setCartProductNum(snapshot.child("cartProductNum").getValue().toString());
                    cartProduct.setCartProductPrice(snapshot.child("cartProductPrice").getValue().toString());
                    cartProduct.setCartProductTitle(snapshot.child("cartProductTitle").getValue().toString());

                    cartList.add(cartProduct);
                }
                cartRecylerAdapter=new CartRecylerAdapter(getContext(),cartList);
                cartRecyclerView.setAdapter(cartRecylerAdapter);
                cartRecylerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearAll() {
        if(cartList!=null){
            cartList.clear();

            if(cartRecylerAdapter !=null){
                cartRecylerAdapter.notifyDataSetChanged();
            }

        }
        cartList= new ArrayList<>();
    }
}