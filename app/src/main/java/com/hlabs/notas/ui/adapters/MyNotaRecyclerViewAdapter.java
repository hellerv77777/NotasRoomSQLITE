package com.hlabs.notas.ui.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hlabs.notas.db.entity.NotaEntity;
import com.hlabs.notas.R;

import java.util.List;


public class MyNotaRecyclerViewAdapter extends RecyclerView.Adapter<MyNotaRecyclerViewAdapter.ViewHolder> {

    private List<NotaEntity> mValues;
    private final Context ctx;

    public MyNotaRecyclerViewAdapter(List<NotaEntity> items, Context ctx) {
        mValues = items;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_nota, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mItem = mValues.get(position);
        holder.TextViewTitulo.setText(mValues.get(position).getTitulo());

        holder.TextViewNota.setText(mValues.get(position).getContenido());

        if(holder.mItem.isFavorita())
        {
            holder.imageViewFavotita.setImageResource(R.drawable.ic_star_black_24dp);
        }else{
            holder.imageViewFavotita.setImageResource(R.drawable.ic_star_border_black_24dp);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    public int getItemCount() {

        if ((mValues.size()!=0))
        {
            return mValues.size();
        }
        return 0;

    }

    public void setNuevasNotas(List<NotaEntity> nuevasNotas){

        this.mValues = nuevasNotas;
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView TextViewTitulo;
        public final TextView TextViewNota;
        public final ImageView imageViewFavotita;
        public NotaEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            TextViewTitulo = (TextView) view.findViewById(R.id.textViewTitulo);
            TextViewNota = (TextView) view.findViewById(R.id.textViewNota);
            imageViewFavotita = (ImageView) view.findViewById(R.id.imageViewFav);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + TextViewTitulo.getText() + "'";
        }
    }
}
