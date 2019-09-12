package com.example.letschat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{


    Context context;
    ArrayList<UserProfile> userList;


    public MyAdapter(Context context, ArrayList<UserProfile> userList) {      ///constructor to get contex and user list
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.single_user,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.name.setText(userList.get(position).getUser_name());
        holder.status.setText(userList.get(position).getStatus());
        Picasso.with(context).load(userList.get(position).getThumb_image()).placeholder(R.drawable.images).into(holder.profileImage);



        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Log.e("i should ","not be here");

                String id =userList.get(position).getUid();

                Intent profileIntent = new Intent(context,UserProfilePage.class);
                profileIntent.putExtra("userId",id);
                context.startActivity(profileIntent);
                //In adapter class you don't know the context hence you use context.startActivity
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();

    }

    class MyViewHolder extends RecyclerView.ViewHolder{


        TextView name ;
        TextView status;
        CircleImageView profileImage;
        ConstraintLayout parentLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name =(TextView)itemView.findViewById(R.id.singleUserDisplayName);
            status =(TextView)itemView.findViewById(R.id.singleUserStatus);
            profileImage =(CircleImageView)itemView.findViewById(R.id.singleUserDisplaImage);

            parentLayout = (ConstraintLayout) itemView.findViewById(R.id.parent_layout);

        }
    }
}