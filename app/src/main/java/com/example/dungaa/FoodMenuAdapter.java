package com.example.dungaa;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodMenuAdapter extends RecyclerView.Adapter<FoodMenuAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Category>listmenu;

    public FoodMenuAdapter(Context mContext, ArrayList<Category> listmenu) {
        this.mContext = mContext;
        this.listmenu = listmenu;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.menu_item,viewGroup,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.mName.setText(listmenu.get(i).getNames());
        Picasso.get().load(listmenu.get(i).getImage()).into(myViewHolder.mImage);
    }

    @Override
    public int getItemCount() {
        return listmenu.size();
    }

  public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView mImage;
        TextView mName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
          mImage=(ImageView) itemView.findViewById(R.id.menu_image);
          mName=(TextView) itemView.findViewById(R.id.menu_name);

        }
    }
}
