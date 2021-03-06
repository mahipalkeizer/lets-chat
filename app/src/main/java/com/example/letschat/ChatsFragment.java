package com.example.letschat;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {



    private MyAdapter adapter;
    private DatabaseReference mRootRef;
    private DatabaseReference mChatRef;
    private DatabaseReference mConversastionRef;
    private DatabaseReference mUserRef;

    private String mCurrentUser;
    private RecyclerView mChatRecylerView;
    private ArrayList<UserProfile> mChatUserList;



    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView=inflater.inflate(R.layout.fragment_chats, container, false);

        mCurrentUser= FirebaseAuth.getInstance().getUid();
        mRootRef= FirebaseDatabase.getInstance().getReference();
        mChatRef=mRootRef.child("chats").child(mCurrentUser);

        mUserRef=mRootRef.child("users");


        mChatUserList=new ArrayList<UserProfile>();
        mChatRecylerView =mView.findViewById(R.id.chat_fregment_recycler_view);
        mChatRecylerView.setHasFixedSize(true);
        mChatRecylerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new MyAdapter(getContext(),mChatUserList,"chatFregment");
        mChatRecylerView.setAdapter(adapter);



        mChatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               String chatUser= dataSnapshot.getKey();

          /*     mUserRef.child(chatUser).addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       UserProfile p =dataSnapshot.getValue(UserProfile.class);
                       mChatUserList.add(p);
                       adapter.notifyDataSetChanged();

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });



           */

            mUserRef.child(chatUser).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserProfile p =dataSnapshot.getValue(UserProfile.class);
                    mChatUserList.add(p);
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        // Inflate the layout for this fragment
        return mView;
    }



}
