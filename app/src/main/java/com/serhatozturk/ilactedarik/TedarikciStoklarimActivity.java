package com.serhatozturk.ilactedarik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.serhatozturk.ilactedarik.databinding.ActivityTedarikciStoklarimBinding;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.annotation.Nullable;

public class TedarikciStoklarimActivity extends AppCompatActivity {
    private ActivityTedarikciStoklarimBinding binding;
    ActionBar actionBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<ModelTedStok> modelTedStokArrayList;
    AdapterTedStok adapterTedStok;
    String user;

    String unvaniAl;
    String adresiAl;
    String telefonuAl;
    String mailiAl;
    String idGonder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTedarikciStoklarimBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        modelTedStokArrayList = new ArrayList<>();

        actionBar = getSupportActionBar();
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        user=mAuth.getCurrentUser().getEmail();


        actionBar.setTitle("Stoklarim");

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(203, 67, 53)));

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        binding.RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterTedStok = new AdapterTedStok (modelTedStokArrayList);
        binding.RecyclerView.setAdapter(adapterTedStok);






        getDataBosStok();



        Intent intent = getIntent();
        idGonder = intent.getStringExtra("idGonder");

        if(idGonder!=null){
            AlertDialog.Builder alert = new AlertDialog.Builder(TedarikciStoklarimActivity.this);
            //alert.setTitle("Uyarı");
            alert.setMessage("Kayıt Silinecek?");
            alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    firebaseFirestore.collection("ilacstoklar").document(idGonder)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(TedarikciStoklarimActivity.this, "Kayıt Silindi", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(TedarikciStoklarimActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

                }
            });
            alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });

            alert.show();




        }









    }


    public boolean onSupportNavigateUp() {


        Intent intentToBosNorm = new Intent(TedarikciStoklarimActivity.this, TedarikciMainActivity.class);
        intentToBosNorm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentToBosNorm);

        return super.onSupportNavigateUp();
    }

    public void getDataBosStok()
    {
        firebaseFirestore.collection("ilacstoklar").whereEqualTo("usermail",user).addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(TedarikciStoklarimActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
                if (value != null) {
                    modelTedStokArrayList.clear();
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Map<String, Object> data = snapshot.getData();
                        String ilac = (String) data.get("ilacadi");
                        String stok = (String) data.get("stokdurumu");
                        String id= snapshot.getId();
                        Timestamp date = (Timestamp) data.get("date"); //burada bir dönüşüm yapılacak
                        String dateee=null;
                        try{
                            Date datee=date.toDate();
                            dateee= DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(datee).toString();
                        }catch(Exception e){e.getLocalizedMessage();}
                        ModelTedStok pst = new ModelTedStok (""+id,"","","    "+ilac,"    Stok Durumu: "+stok,"","","");
                        modelTedStokArrayList.add(pst);
                    }
                    adapterTedStok.notifyDataSetChanged();  // değişikleri göstermek.
                }
            }
        });


    }





}




