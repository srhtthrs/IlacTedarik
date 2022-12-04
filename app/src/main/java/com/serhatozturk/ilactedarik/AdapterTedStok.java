package com.serhatozturk.ilactedarik;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.serhatozturk.ilactedarik.databinding.TedarikciStoklarimRecyclerRowBinding;

import java.util.ArrayList;


public class AdapterTedStok extends RecyclerView.Adapter<AdapterTedStok.ModelHolder> {
    private ArrayList<ModelTedStok>modelTedStokArrayList;
    public AdapterTedStok(ArrayList<ModelTedStok> modelTedStokArrayList) {
        this.modelTedStokArrayList = modelTedStokArrayList;
    }
    @NonNull
    @Override
    public ModelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TedarikciStoklarimRecyclerRowBinding tedarikciStoklarimRecyclerRowBinding=TedarikciStoklarimRecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ModelHolder(tedarikciStoklarimRecyclerRowBinding);
    }
    @Override
    public void onBindViewHolder(@NonNull ModelHolder holder, @SuppressLint("RecyclerView") int position) {
       holder.tedarikciStoklarimRecyclerRowBinding.ilacadi.setText(modelTedStokArrayList.get(position).ilac);
       holder.tedarikciStoklarimRecyclerRowBinding.stok.setText(modelTedStokArrayList.get(position).stok);





       //holder.itemView.setOnClickListener(new View.OnClickListener() {
       holder.tedarikciStoklarimRecyclerRowBinding.btnStokSil.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String idGonder=modelTedStokArrayList.get(position).id;


               Intent intent = new Intent(holder.itemView.getContext(), TedarikciStoklarimActivity.class);
               intent.putExtra("idGonder",idGonder);
               holder.itemView.getContext().startActivity(intent);

           }
       });

    }
    @Override
    public int getItemCount() {
        return modelTedStokArrayList.size();
    }
    class ModelHolder extends RecyclerView.ViewHolder {
        TedarikciStoklarimRecyclerRowBinding tedarikciStoklarimRecyclerRowBinding;
        public ModelHolder(TedarikciStoklarimRecyclerRowBinding tedarikciStoklarimRecyclerRowBinding) {
            super(tedarikciStoklarimRecyclerRowBinding.getRoot());
            this.tedarikciStoklarimRecyclerRowBinding=tedarikciStoklarimRecyclerRowBinding;
        }
    }
}
