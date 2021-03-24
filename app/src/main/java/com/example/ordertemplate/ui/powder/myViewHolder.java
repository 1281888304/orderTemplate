package com.example.ordertemplate.ui.powder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ordertemplate.R;

public class myViewHolder extends RecyclerView.ViewHolder {
    ImageView powderImage;
    TextView powderName;
 View v;


    public myViewHolder(@NonNull View itemView) {
        super(itemView);

       powderImage=itemView.findViewById(R.id.powder_image_singleView);
       powderName=itemView.findViewById(R.id.powderNameSingleView);
        v=itemView;

    }
}
