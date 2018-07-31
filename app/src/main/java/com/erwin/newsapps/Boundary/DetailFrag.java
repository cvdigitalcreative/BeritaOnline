package com.erwin.newsapps.Boundary;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.erwin.newsapps.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFrag extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    String uid, judul,tanggal, deskripsi;

    MenuItem fav;
    public DetailFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_frag_detail, container, false);
        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        judul = bundle.getString("judul").trim();
        tanggal = bundle.getString("tanggal");
        deskripsi = bundle.getString("deskripsi");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
//        System.out.println(firebaseAuth.getCurrentUser().getUid());
        uid = currentuser.getUid();
      //  ActionBar actionBar = getActivity().getActionBar();

        Button favorite = view.findViewById(R.id.btn_favorite);
        TextView judultv = view.findViewById(R.id.titledisini);
        TextView tanggaltv = view.findViewById(R.id.tanggaldisini);
        JustifiedTextView deskripsitv = view.findViewById(R.id.deskcripsidisini);

        judultv.setText(judul);
        tanggaltv.setText(tanggal);
        deskripsitv.setText(deskripsi);

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Insertdata();
            }
        });
        return view;
    }

    private void Insertdata() {
//        FirebaseFuncz funcz =  new FirebaseFuncz();
////        boolean doit = funcz.checkUIDlvl2(uid, "Newsfeed");
////        System.out.println("coy");
////        System.out.println(doit);
        myRef = firebaseDatabase.getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Newsfeed")){
                    myRef.child("Newsfeed").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            boolean check = false;
                            for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                System.out.println("coyo");
                                System.out.println(dataSnapshot1.child("judul").getValue().toString());
                                System.out.println(judul);
                                System.out.println(dataSnapshot1.child("judul").getValue().toString().trim().toLowerCase().equals(judul));
                                if (judul.equals(dataSnapshot1.child("judul").getValue().toString().trim())){
                                    check = true;
                                } else{
                                    check = false;
                                }
                            }
                            if (!check){
                                String itemkey = myRef.child("Newsfeed").child(uid).push().getKey();
                                myRef.child("Newsfeed").child(uid).child(itemkey).child("judul").setValue(judul);
                                myRef.child("Newsfeed").child(uid).child(itemkey).child("tanggal").setValue(tanggal);
                                myRef.child("Newsfeed").child(uid).child(itemkey).child("deskripsi").setValue(deskripsi);
                                Toast.makeText(getActivity(), "Berita Berhasil Tersimpan", Toast.LENGTH_LONG).show();
                                redirectScreen();
                            } else{
                                redirectScreen();
                                Toast.makeText(getActivity(), "Berita Sudah Pernah Tersimpan", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    String itemkeys = myRef.child("Newsfeed").child(uid).push().getKey();
                    DatabaseReference going = firebaseDatabase.getReference().child("Newsfeed").child(uid).child(itemkeys);
                    going.child("judul").setValue(judul);
                    going.child("tanggal").setValue(tanggal);
                    going.child("deskripsi").setValue(deskripsi);
                    System.out.println("coy");
                    System.out.println("klo root ga da");
                    redirectScreen();
                    }
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        if (!doit){
//            String key = firebaseDatabase.getReference("Newsfeed").push().getKey();
//            myRef = firebaseDatabase.getReference().child("Newsfeed").child(uid).child(key);
//
//            myRef.child("judul").setValue(judul);
//            myRef.child("tanggal").setValue(tanggal);
//            myRef.child("deskripsi").setValue(deskripsi);
//            myRef.child("status").setValue("simpan");
//            redirectScreen();
//        } else {
//            Toast.makeText(getActivity(), "root sudah ada", Toast.LENGTH_LONG).show();
//        }
    }

    private void redirectScreen(){
        Log.d(TAG, "Redirecting to login screen.");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new FavoriteFrag())
                .addToBackStack(null).commit();
    }

//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
//        inflater.inflate(R.menu.favorite, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        switch (item.getItemId()){
//            case R.id.favorite:
//                Insertdata();
//                Toast.makeText(getActivity(),"test", Toast.LENGTH_LONG).show();
//            return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}

