package com.example.ordertemplate.ui.topping;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ordertemplate.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ToppingFragment extends Fragment {

    private ToppingViewModel mViewModel;
    ArrayList<Toppings> toppingsList;
    private DatabaseReference myRef;
    private RecyclerView recyclerView;

    private Button toppingAdd,toppingDrop;



    //adapter
    private RecylerAdapter recylerAdapter;

    private Context mcontext;


    public static ToppingFragment newInstance() {
        return new ToppingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.topping_fragment, container, false);

//        Button toppingBtn =view.findViewById(R.id.toppingBtn);
//        toppingBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
//            }
//        });




        recyclerView=view.findViewById(R.id.recylerView);



         //variables


        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //firebase
        myRef= FirebaseDatabase.getInstance().getReference();


        //clear data
        clearAll();

        //get data model

        GetDataFromFirebase();

        //try to change data here
        toppingAdd= view.findViewById(R.id.toppingShopAdd);




        return view;
    }

    private void GetDataFromFirebase() {

        Query query=myRef.child("Product").child("Topping");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clearAll();
                for(DataSnapshot snapshot :dataSnapshot.getChildren()){
                    Toppings toppings=new Toppings();

                    toppings.setToppingImage(snapshot.child("toppingImage").getValue().toString());
                    toppings.setToppingName(snapshot.child("toppingName").getValue().toString());
                    toppings.setToppingPrice(snapshot.child("toppingPrice").getValue().toString());
                    toppings.setToppingNum(snapshot.child("toppingNum").getValue().toString());
                    toppings.setToppingTitle(snapshot.child("toppingTitle").getValue().toString());

                    //add to list
                    toppingsList.add(toppings);
                }

                recylerAdapter = new RecylerAdapter(getContext(),toppingsList);
                recyclerView.setAdapter(recylerAdapter);
                recylerAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void clearAll(){
        if(toppingsList!=null){
            toppingsList.clear();

            if(recylerAdapter!=null){
                recylerAdapter.notifyDataSetChanged();
            }

        }
        toppingsList= new ArrayList<>();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ToppingViewModel.class);

        // TODO: Use the ViewModel
    }

}