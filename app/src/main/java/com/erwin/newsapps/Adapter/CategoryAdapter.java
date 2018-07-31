package com.erwin.newsapps.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.erwin.newsapps.Boundary.CategoryNewsFrag;
import com.erwin.newsapps.Model.Model;
import com.erwin.newsapps.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    List<Model> list;

    public CategoryAdapter(List<Model> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardgrid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryAdapter.ViewHolder holder, final int position) {
        final Model model =  list.get(position);
        final String getpos = model.getPos();
        final String kategori = model.getKategori();
                holder.title.setText(kategori);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println(getpos);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                CategoryNewsFrag nextfragment =  new CategoryNewsFrag();
                Bundle bundle =  new Bundle();
                bundle.putString("kategori", kategori);
                System.out.println("kategoreng");
                System.out.println(kategori);
                nextfragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, nextfragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
         return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, description;
        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.txtTitle);
        }
    }
}
