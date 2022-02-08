package com.example.sltreport_app;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class ReportVH extends  RecyclerView.ViewHolder {

    public TextView txt_name,txt_position,txt_option,txt_assigns;
    public ReportVH(@NonNull View itemView)
    {
        super(itemView);
        txt_name = itemView.findViewById(R.id.txt_name);
        txt_position = itemView.findViewById(R.id.txt_position);
        txt_assigns=itemView.findViewById(R.id.txt_assigns);
        txt_option = itemView.findViewById(R.id.txt_option);
    }



}
