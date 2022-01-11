package com.example.sltreport_app;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View
public class personAdapter extends FirebaseRecyclerAdapter<

        person, personAdapter.personsViewholder> {
    DatabaseReference db;
    person userob;
    public personAdapter(
            @NonNull FirebaseRecyclerOptions<person> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull personsViewholder holder,
                     int position, @NonNull person model)
    {

        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.town.setText(model.getTown());

        // Add lastname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.vilage.setText(model.getVilage());

        // Add age from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.description.setText(model.getDescription());


    }

    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public personsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person, parent, false);
        return new personAdapter.personsViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class personsViewholder
            extends RecyclerView.ViewHolder {
        TextView town, vilage, description,txt_option;
        public personsViewholder(@NonNull View itemView)
        {
            super(itemView);

            town = itemView.findViewById(R.id.town);
            vilage = itemView.findViewById(R.id.vilage);
            description = itemView.findViewById(R.id.description);
            txt_option=itemView.findViewById(R.id.txt_option);

            txt_option.setOnClickListener(v->
            {
                PopupMenu popupMenu=new PopupMenu(itemView.getContext(),txt_option);
                popupMenu.inflate(R.menu.option_menu);
                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId())
                    {
                        case R.id.menu_edit:
                            Intent intent=new Intent(itemView.getContext(),Reportbreakdown.class);
                            DatabaseReference upRef = FirebaseDatabase.getInstance().getReference().child("Report");
                            upRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.hasChild(town.getText().toString().trim())){

                                        userob.setVilage(vilage.getText().toString().trim());
                                        userob.setDescription(description.getText().toString().trim());

                                        String name = town.getText().toString().trim();

                                        db = FirebaseDatabase.getInstance().getReference().child("Report").child(name);
                                        db.setValue(userob);




                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            break;
                        case R.id.menu_remove:

                            break;
                    }

                    return false;
                });

                popupMenu.show();
            });
        }
    }


}