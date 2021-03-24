package com.example.ordertemplate.ui.powder;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.example.ordertemplate.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PowderFragment extends Fragment {

    private PowderViewModel mViewModel;

    //field
     EditText powderSearch;
    RecyclerView powderRecylerView;

    //firebase ui
    FirebaseRecyclerOptions<Powders> powderOption;
    FirebaseRecyclerAdapter<Powders,myViewHolder>adapter;

    DatabaseReference powderDataRef;




    public static PowderFragment newInstance() {
        return new PowderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.powder_fragment, container, false);

        powderDataRef= FirebaseDatabase.getInstance().getReference().child("Powder");

        //find id first
        powderSearch=view.findViewById(R.id.inputSearchPowder);
        powderRecylerView=view.findViewById(R.id.powderRecylerView);
        powderRecylerView.setLayoutManager(new LinearLayoutManager(getContext()));
        powderRecylerView.setHasFixedSize(true);

        getPowderData();


        return view;
    }

    private void getPowderData() {
        powderOption=new FirebaseRecyclerOptions.Builder<Powders>()
                .setQuery(powderDataRef,Powders.class).build();

        adapter=new FirebaseRecyclerAdapter<Powders, myViewHolder>(powderOption) {
            @Override
            protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Powders model) {
                //holder here
                holder.powderName.setText(model.getPowderName());
                Picasso.get().load(model.getPowderImage()).into(holder.powderImage);

                // add click function
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),powderClickViewActivity.class);
                        intent.putExtra("powderKey",getRef(position).getKey());



                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v=LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.singlepowder_view,parent,false);

                return new myViewHolder(v);
            }
        };

        adapter.startListening();
        powderRecylerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PowderViewModel.class);
        // TODO: Use the ViewModel
    }

}