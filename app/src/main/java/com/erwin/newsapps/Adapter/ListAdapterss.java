package com.erwin.newsapps.Adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.erwin.newsapps.Boundary.DetailFrag;
import com.erwin.newsapps.Model.Model;
import com.erwin.newsapps.R;

import java.util.List;

public class ListAdapterss extends RecyclerView.Adapter<ListAdapterss.ViewHolder> {
    List<Model> list;

    public ListAdapterss(List<Model> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ListAdapterss.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterss.ViewHolder holder, final int position) {
        Model model =  list.get(position);
        final String deskripsi = model.getDeskripsi().replaceAll("\\&\\#\\d+", "").replaceAll("<.*?>","").replaceAll(";", "");
        final String judul =  model.getTitle();
        final String tanggal =  model.getDate();

        holder.title.setText(judul);
        holder.date.setText(tanggal);
        holder.description.setText(deskripsi);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                DetailFrag nextfragment =  new DetailFrag();
                Bundle bundle = new Bundle();
                bundle.putString("judul", judul);
                bundle.putString("tanggal", tanggal);
                bundle.putString("deskripsi", deskripsi);

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

            //status = itemView.findViewById(R.id.statuss);
            title = itemView.findViewById(R.id.txtTitle);
            date = itemView.findViewById(R.id.txtPubDate);
            description = itemView.findViewById(R.id.txtContent);
        }
    }
}
