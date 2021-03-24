package com.example.ordertemplate.ui.powder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ordertemplate.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class powderClickViewActivity extends AppCompatActivity {

    private ImageView PowderImageClickView;
    private TextView powderNameClickView;
    private Button powderDelete;

    //database
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_powder_click_view);

        PowderImageClickView=findViewById(R.id.powder_image_singleViewClick);
        powderNameClickView=findViewById(R.id.powderNameSingleViewClick);
        powderDelete=findViewById(R.id.powderDelete);

        //make powderKey
        ref= FirebaseDatabase.getInstance().getReference().child("Powder");
        String Powderkey=getIntent().getStringExtra("powderKey");

        ref.child(Powderkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String powderName=snapshot.child("powderName").getValue().toString();
                    String powderImage=snapshot.child("powderImage").getValue().toString();

                    Picasso.get().load(powderImage).into(PowderImageClickView);
                    powderNameClickView.setText(powderName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}