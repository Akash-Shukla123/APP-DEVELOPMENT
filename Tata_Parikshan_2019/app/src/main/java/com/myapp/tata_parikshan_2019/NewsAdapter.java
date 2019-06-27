package com.myapp.tata_parikshan_2019;


import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    Context mContext;
    List<NewsItem> mData;


    public NewsAdapter(Context mContext, List<NewsItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_detail,parent,false);
        return new NewsViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, int position) {

        //bind here

        //animation part
        holder.img_user.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));
        holder.item_action_container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));


        holder.tv_title.setText(mData.get(position).getTitle());
        holder.tv_content.setText(mData.get(position).getContent());
        holder.tv_date.setText(mData.get(position).getDate());
        holder.img_user.setImageResource(mData.get(position).getUserphoto());

        holder.item_action_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info="";
                String tripId = holder.tv_date.getText().toString();
                char arr[] = new char[30];
                tripId.getChars(0,4,arr,0);

                for (int i=0; i<4; i++){
                    info=info+arr[i];
                }


                if (info.equals("TRIP")){
                    Bundle bundle = new Bundle();
                    bundle.putString("TripID",tripId);
                    QrCodeGenerator qrCodeGenerator = new QrCodeGenerator();
                    qrCodeGenerator.setArguments(bundle);
                    AdminActivity.fragmentManager.beginTransaction().replace(R.id.admin_fragment,
                            qrCodeGenerator).addToBackStack(null).commit();
                }
                else{}
            }
        });

        holder.ClickMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mContext,holder.item_action_container);
                popupMenu.inflate(R.menu.recycler_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()){

                            case R.id.menu_item_upd:

                                String info1="";
                                String getId = holder.tv_date.getText().toString();
                                char arr[] = new char[30];
                                getId.getChars(0,4,arr,0);

                                for (int i=0; i<4; i++){
                                    info1=info1+arr[i];
                                }


                                if (info1.equals("TRIP")){

                                    Bundle bundle1 = new Bundle();
                                    bundle1.putString("IDOutput1",getId);
                                    AddTrip addTrip = new AddTrip();
                                    addTrip.setArguments(bundle1);
                                    AdminActivity.fragmentManager.beginTransaction().replace(R.id.admin_fragment,
                                            addTrip,"TripRecycler").addToBackStack(null).commit();

                                }
                                if (info1.equals("VECH")){

                                    Bundle bundle2 = new Bundle();
                                    bundle2.putString("IDOutput2",getId);
                                    AddVechile addVechile = new AddVechile();
                                    addVechile.setArguments(bundle2);
                                    AdminActivity.fragmentManager.beginTransaction().replace(R.id.admin_fragment,
                                            addVechile,"VechileRecycler").addToBackStack(null).commit();
                                }
                                if (info1.equals("TRNP")){

                                    Bundle bundle3 = new Bundle();
                                    bundle3.putString("IDOutput3",getId);
                                    AddTransporters addTransporters = new AddTransporters();
                                    addTransporters.setArguments(bundle3);
                                    AdminActivity.fragmentManager.beginTransaction().replace(R.id.admin_fragment,
                                            addTransporters,"TransporterRecycler").addToBackStack(null).commit();

                                }

                                if (info1.equals("SCAN")){
                                    Toast.makeText(mContext,"Cannot be updated",Toast.LENGTH_SHORT).show();
                                }

                                break;

                            case R.id.menu_item_delete:
                                Bundle bundle = new Bundle();
                                String id = holder.tv_date.getText().toString();
                                bundle.putString("DeleteId",id);
                                RecyclerDeleteDialog recyclerDeleteDialog = new RecyclerDeleteDialog();
                                recyclerDeleteDialog.setArguments(bundle);
                                recyclerDeleteDialog.show(AdminActivity.fragmentManager,"RecyclerDelete");
                                break;

                            default:
                                break;
                        }

                        return false;
                    }
                });

                popupMenu.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{


        TextView tv_title,tv_content,tv_date,ClickMenu;
        ImageView img_user;

        RelativeLayout item_action_container;


    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);

        item_action_container = itemView.findViewById(R.id.item_action_container);
        tv_title = itemView.findViewById(R.id.tv_title);
        tv_content = itemView.findViewById(R.id.tv_description);
        tv_date = itemView.findViewById(R.id.tv_date);
        img_user = itemView.findViewById(R.id.img_user);
        ClickMenu = itemView.findViewById(R.id.clickmenu);

    }
}

public void updateList(List<NewsItem> newList){

        mData = new ArrayList<>();
        mData.addAll(newList);
        notifyDataSetChanged();

}

}
