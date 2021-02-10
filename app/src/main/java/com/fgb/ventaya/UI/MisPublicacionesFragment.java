package com.fgb.ventaya.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fgb.ventaya.Adapters.RecyclerAdapterPublicaciones;
import com.fgb.ventaya.Entity.Publicacion;
import com.fgb.ventaya.R;
import com.firebase.ui.database.FirebaseArray;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MisPublicacionesFragment extends Fragment {

    RecyclerView recycler;
    RecyclerAdapterPublicaciones  recyclerAdapterPublicaciones;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pantalla_home, container, false);
        recycler= (RecyclerView) view.findViewById(R.id.recyclerPublicacioness);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String idUsuario = user.getUid().toString();







        FirebaseRecyclerOptions<Publicacion> options =
                new FirebaseRecyclerOptions.Builder<Publicacion>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Publicacion").orderByChild("idUsuario").equalTo(idUsuario),Publicacion.class)
                        .build();



        recyclerAdapterPublicaciones = new RecyclerAdapterPublicaciones(options);
        recycler.setAdapter(recyclerAdapterPublicaciones);


       /*queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Publicacion> publicaciones = new ArrayList<Publicacion>();


                for (DataSnapshot publicacionSnapshot : dataSnapshot.getChildren()) {
                    Publicacion publicacion = publicacionSnapshot.getValue(Publicacion.class);
                    if (publicationId.contain(publicacion.)) {
                        publicacion.add(video);
                    }
                }

                // publicacion is now populated with the videos for the users

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
              });*/

                                                 /*  */




        // Inflate the layout for this fragment
        return view;
    }

  /*  private ArrayList<String> collectIdPublication(DataSnapshot dataSnapshot) {
        ArrayList<String> id = new ArrayList<>();


        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
            id.add(String.valueOf(dsp.getValue())); //add result into array list

        }
        //iterate through each user, ignoring their UID
       /* for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            phoneNumbers.add((String) singleUser.get());
        }

        return id;

    }*/

    @Override
    public void onStart() {
        super.onStart();
        recyclerAdapterPublicaciones.startListening();
    }
    @Override
    public  void onStop() {
        super.onStop();
        recyclerAdapterPublicaciones.stopListening();
    }




}
