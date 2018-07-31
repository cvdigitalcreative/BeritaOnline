package com.erwin.newsapps.Boundary;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erwin.newsapps.Adapter.ListAdapterss;
import com.erwin.newsapps.Model.Model;
import com.erwin.newsapps.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFrag extends Fragment {
    RecyclerView recyclerView;
    List<Model> list = new ArrayList<>();
    ListAdapterss listAdapterss;
    Context context=this.getContext();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    String uid;

    public FavoriteFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = view.findViewById(R.id.recyclerfavorite);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVerticalScrollBarEnabled(true);
        listAdapterss = new ListAdapterss(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        myRef=firebaseDatabase.getReference();
        uid = currentuser.getUid();

        myRef.child("Newsfeed").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Model model =  new Model();
                    //String itemkeylaoder = dataSnapshot1.getKey();
                    String judul =  dataSnapshot1.child("judul").getValue().toString();
                    String tanggal =  dataSnapshot1.child("tanggal").getValue().toString();
                    String deskripsi = dataSnapshot1.child("deskripsi").getValue().toString();

                    model.setTitle(judul);
                    model.setDate(tanggal);
                    model.setDeskripsi(deskripsi);

                    System.out.println(judul);
                    list.add(model);
                }
                recyclerView.setAdapter(listAdapterss);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

}
