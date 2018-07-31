package com.erwin.newsapps.Boundary;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.erwin.newsapps.BaseActivity;
import com.erwin.newsapps.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginForm extends Fragment {
    FirebaseAuth firebaseAuth;
    private EditText email;
    private EditText pass;
    private DatabaseReference mDatabase;
    String userid;
    Context context;

    public LoginForm() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_form, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        email = view.findViewById(R.id.mail_user);
        pass =  view.findViewById(R.id.pass_user);

        firebaseAuth = FirebaseAuth.getInstance();

        //Button Login
        final Button btn_login = view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameII = email.getText().toString().trim();
//                Intent in = new Intent(getActivity(), BaseActivity.class);
//                //in.putExtra("keylogin", finalStatus_user);
//                startActivity(in);
                //Checklogin(); //<-- Login With Email
                CheckLoginbyUsername(usernameII); // <-- Login With Username
            }
        });
        return view;
    }

    private void CheckLoginbyUsername(final String username){

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                boolean check = false;
                String email = null;
                int status_user = 0;
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    if ((dataSnapshot1.child("username").getValue().equals(username)) && (dataSnapshot1.child("status").getValue().equals("Subscribe"))){
                        check = true;
                        email = dataSnapshot1.child("email").getValue().toString();
                       // status_user = Integer.valueOf(dataSnapshot1.child("status").getValue().toString());
                        break;
                    }
                }

                if (check==true){
                    final String emailuser = email;
                   // final int finalStatus_user = status_user;
                   // System.out.println(finalStatus_user);
                    firebaseAuth.signInWithEmailAndPassword(emailuser, pass.getText().toString()).addOnCompleteListener(getActivity(),
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Intent in = new Intent(getActivity(), BaseActivity.class);
                                    //in.putExtra("keylogin", finalStatus_user);
                                    startActivity(in);
                                }
                            });
                } else {
                    Toast.makeText(getActivity(), "User Password Anda Salah atau Anda Belum Berlangganan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

