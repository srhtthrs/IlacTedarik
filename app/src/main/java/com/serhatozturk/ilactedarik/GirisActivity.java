package com.serhatozturk.ilactedarik;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.serhatozturk.ilactedarik.databinding.ActivityGirisBinding;
import com.serhatozturk.ilactedarik.databinding.ActivityTedarikciStoklarimBinding;

public class GirisActivity extends AppCompatActivity {
    private ActivityGirisBinding binding;
    private String secim[];
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGirisBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        actionBar = getSupportActionBar();
        actionBar.setTitle("İlaç Tedarik");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(203, 67, 53)));
    }


    public void eczaneClick(View view){

        Intent intentEczane=new Intent(GirisActivity.this,MainActivity.class);
        startActivity(intentEczane);
    }

    public void eczaDeposuClick(View view){
        Intent intentEczaDeposu=new Intent(GirisActivity.this,TedarikciLogIn.class);
        startActivity(intentEczaDeposu);

    }

}