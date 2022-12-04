package com.serhatozturk.ilactedarik;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.serhatozturk.ilactedarik.databinding.TumIlaclarRecyclerRowBinding;

import java.util.ArrayList;





public class AdapterTumStok extends RecyclerView.Adapter<AdapterTumStok.ModelHolder> {

    private ArrayList<ModelTedStok> modelTedStokArrayList;
    public AdapterTumStok(ArrayList<ModelTedStok> modelTedStokArrayList) {
        this.modelTedStokArrayList = modelTedStokArrayList;
    }
    @NonNull
    @Override
    public ModelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TumIlaclarRecyclerRowBinding tumIlaclarRecyclerRowBinding= TumIlaclarRecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);


        return new ModelHolder(tumIlaclarRecyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ModelHolder holder, @SuppressLint("RecyclerView") int position) {



        holder.tumIlaclarRecyclerRowBinding.ilac.setText(modelTedStokArrayList.get(position).ilac);
        holder.tumIlaclarRecyclerRowBinding.stok.setText(modelTedStokArrayList.get(position).stok);
        holder.tumIlaclarRecyclerRowBinding.adres.setText(modelTedStokArrayList.get(position).adres);
        holder.tumIlaclarRecyclerRowBinding.unvan.setText(modelTedStokArrayList.get(position).unvan);
        holder.tumIlaclarRecyclerRowBinding.telefon.setText(modelTedStokArrayList.get(position).telefon);

        holder.tumIlaclarRecyclerRowBinding.btnTelAra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String telGonder=modelTedStokArrayList.get(position).telefon;


                Intent intent = new Intent(holder.itemView.getContext(), MainActivity.class);
                intent.putExtra("telGonder",telGonder);
                holder.itemView.getContext().startActivity(intent);

            }
        });




    }


    @Override
    public int getItemCount() {
        return modelTedStokArrayList.size();
    }
    class ModelHolder extends RecyclerView.ViewHolder {
        TumIlaclarRecyclerRowBinding tumIlaclarRecyclerRowBinding;
        public ModelHolder(TumIlaclarRecyclerRowBinding tumIlaclarRecyclerRowBinding) {
            super(tumIlaclarRecyclerRowBinding.getRoot());
            this.tumIlaclarRecyclerRowBinding=tumIlaclarRecyclerRowBinding;
        }
    }
}
