package com.serhatozturk.ilactedarik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.serhatozturk.ilactedarik.databinding.ActivityTedarikciMainBinding;

public class TedarikciMainActivity extends AppCompatActivity {

    ActivityTedarikciMainBinding binding;
    ActionBar actionBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    String tedarikci;
    String tedarikciId;

    String unvaniAl,idsiniAl;
    String adresiAl;
    String telefonuAl;
    String mailiAl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTedarikciMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        firebaseFirestore = FirebaseFirestore.getInstance();


        actionBar = getSupportActionBar();
        tedarikci=mAuth.getCurrentUser().getEmail().toString();


        actionBar.setTitle("Firma Bilgilerim");

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(203, 67, 53 )));











        getDataTedBilgileri();
    }



    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tedarikci_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.stokGir) {
            Intent intent=new Intent(TedarikciMainActivity.this, StokGirActivity.class);
            intent.putExtra("unvaniAl",unvaniAl);
            intent.putExtra("adresiAl",adresiAl);
            intent.putExtra("telefonuAl",telefonuAl);
            intent.putExtra("mailiAl",mailiAl);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.cikis) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Uyarı");
            alert.setMessage("Cikis Yapmak Istiyor Musun?");
            alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                                        mAuth.signOut();
                                        Intent intentToMain=new Intent(TedarikciMainActivity.this, GirisActivity.class);
                                        intentToMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intentToMain);
                }
            });
            alert.setNegativeButton("Hayir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alert.show();

        }

        if(item.getItemId()==R.id.bilgiguncelle) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Uyarı");
            alert.setMessage("Uyeligi Silmek Istiyor Musun?");
            alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteDataUserTumUrunleri();
                    deleteUser();
                    deleteUser2();

                }
            });
            alert.setNegativeButton("Hayir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alert.show();
        }

        if(item.getItemId()==R.id.stoklarim) {


            Intent intent=new Intent(TedarikciMainActivity.this, TedarikciStoklarimActivity.class);
            intent.putExtra("unvaniAl",unvaniAl);
            intent.putExtra("adresiAl",adresiAl);
            intent.putExtra("telefonuAl",telefonuAl);
            intent.putExtra("mailiAl",mailiAl);
            intent.putExtra("tedarikciId",tedarikciId);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }



        return super.onOptionsItemSelected(item);
    }


    public void getDataTedBilgileri() {
        CollectionReference tedarikcibilgileriRef = firebaseFirestore.collection("tedarikcibilgileri");
        Query query = tedarikcibilgileriRef.whereEqualTo("usermail", tedarikci);

        query.get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            unvaniAl=document.get("sirketunvan").toString();
                            adresiAl=document.get("adresIlce").toString();
                            telefonuAl=document.get("telefon").toString();
                            mailiAl=document.get("usermail").toString();


                            tedarikciId=document.getId();
                            binding.textTedMainUnvan.setText("Unvan: "+unvaniAl);
                            binding.textTedMainAdres.setText("Adres: "+adresiAl);
                            binding.textTedMainTel.setText("Tel: "+telefonuAl);
                            binding.textTedMainMail.setText("Mail: "+mailiAl);
                            binding.textTedMainVergiNo.setText("Vergi No: "+document.get("vergino"));


                        }
                    } else {
                        Toast.makeText(TedarikciMainActivity.this,"Hata Oluştu",Toast.LENGTH_LONG).show();
                    }
                }
            });


    }



    public void deleteDataUserTumUrunleri(){

        CollectionReference tedarikcibilgileriRef = firebaseFirestore.collection("ilacstoklar");
        Query query = tedarikcibilgileriRef.whereEqualTo("usermail", tedarikci);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                idsiniAl=document.getId();

                                firebaseFirestore.collection("ilacstoklar").document(idsiniAl)
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                              //  Toast.makeText(TedarikciMainActivity.this, "Kayıt Silindi", Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(TedarikciMainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });


                            }
                        } else {
                            Toast.makeText(TedarikciMainActivity.this,"Hata Oluştu",Toast.LENGTH_LONG).show();
                        }
                    }
                });








    }


    public void deleteUser(){

        mAuth.getCurrentUser().delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(TedarikciMainActivity.this, "Kullanici ve Urunleri Silindi", Toast.LENGTH_LONG);
                            Intent intentToMain = new Intent(TedarikciMainActivity.this, GirisActivity.class);
                            intentToMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intentToMain);
                        }
                    }
                });

    }


    public void deleteUser2(){

        CollectionReference tedarikcibilgileriRef = firebaseFirestore.collection("tedarikcibilgileri");
        Query query = tedarikcibilgileriRef.whereEqualTo("usermail", tedarikci);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        idsiniAl=document.getId();

                        firebaseFirestore.collection("tedarikcibilgileri").document(""+idsiniAl)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //  Toast.makeText(TedarikciMainActivity.this, "Kayıt Silindi", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(TedarikciMainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });


                    }
                } else {
                    Toast.makeText(TedarikciMainActivity.this,"Hata Oluştu",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}