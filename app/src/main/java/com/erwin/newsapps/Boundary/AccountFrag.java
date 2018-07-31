package com.erwin.newsapps.Boundary;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.erwin.newsapps.LoginActivity;
import com.erwin.newsapps.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFrag extends Fragment {
    DatabaseReference myStatus;
    FirebaseDatabase database2;
    TextView nama, email, alamat, status;

    public AccountFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_account, container, false);
        nama =  view.findViewById(R.id.your_name);
        email =  view.findViewById(R.id.email_name);
        alamat = view.findViewById(R.id.alamat_name);
        status =  view.findViewById(R.id.status);

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();
        database2 = FirebaseDatabase.getInstance();
        myStatus = database2.getReference("Users").child(uid);
        myStatus.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String loadnama = dataSnapshot.child("nama").getValue().toString();
                String loademail = dataSnapshot.child("email").getValue().toString();
                String loadalamt = dataSnapshot.child("alamat").getValue().toString();
                String loadstatus = dataSnapshot.child("status").getValue().toString();

                nama.setText(loadnama);
                email.setText(loademail);
                alamat.setText(loadalamt);
                status.setText(loadstatus);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button btn_signout = view.findViewById(R.id.btn_logout);
        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();

                Intent in = new Intent(getActivity(), LoginActivity.class);
                startActivity(in);
            }
        });
        return view;
    }

}
