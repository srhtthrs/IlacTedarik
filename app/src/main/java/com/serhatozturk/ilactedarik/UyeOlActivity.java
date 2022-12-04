package com.serhatozturk.ilactedarik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.serhatozturk.ilactedarik.databinding.ActivityTedarikciLogInBinding;
import com.serhatozturk.ilactedarik.databinding.ActivityUyeOlBinding;

import java.util.HashMap;

public class UyeOlActivity extends AppCompatActivity {

    private ActivityUyeOlBinding binding;
    ActionBar actionBar;
    private String ilceler[];
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUyeOlBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();




        actionBar = getSupportActionBar();
        actionBar.setTitle("Uye Ol");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(203, 67, 53 )));

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        binding.btnUyeOl.setBackgroundColor(Color.rgb(203, 67, 53));



        ilceler=new String[] {"Adres Giriniz",
                "Adalar",
                "Arnavutköy",
                "Ataşehir",
                "Avcılar",
                "Bağcılar",
                "Bahçelievler",
                "Bakırköy",
                "Başakşehir",
                "Bayrampaşa",
                "Beşiktaş",
                "Beykoz",
                "Beylikdüzü",
                "Beyoğlu",
                "Büyükçekmece",
                "Çatalca",
                "Çekmeköy",
                "Esenler",
                "Esenyurt",
                "Eyüp",
                "Fatih",
                "Gaziosmanpaşa",
                "Güngören",
                "Kadıköy",
                "Kâğıthane",
                "Kartal",
                "Küçükçekmece",
                "Maltepe",
                "Pendik",
                "Sancaktepe",
                "Sarıyer",
                "Silivri",
                "Sultanbeyli",
                "Sultangazi",
                "Şile",
                "Şişli",
                "Tuzla",
                "Ümraniye",
                "Üsküdar",
                "Zeytinburnu"};

        ArrayAdapter<String> adapterUnvan = new ArrayAdapter<String>(UyeOlActivity.this, android.R.layout.simple_spinner_item, ilceler);
        binding.spinnerIlceler.setAdapter(adapterUnvan);
    }

    public boolean onSupportNavigateUp() {


        Intent intentToBosNorm = new Intent(UyeOlActivity.this, TedarikciLogIn.class);
        intentToBosNorm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentToBosNorm);

        return super.onSupportNavigateUp();
    }

    public void uyeOlClick(View view) {
        String email = binding.textEmail.getText().toString();
        String pass = binding.textPass.getText().toString();
        String passAgain = binding.textPassAgain.getText().toString();

        String sirketUnvan = binding.textUnvan.getText().toString();
        String vergiNo = binding.textVergiNo.getText().toString();
        String telefon = binding.textPhoneNumber.getText().toString();
        String adresIlce = binding.spinnerIlceler.getSelectedItem().toString();

        if (email.equals("") || pass.equals("") ||  passAgain.equals("")||
                sirketUnvan.equals("")|| vergiNo.equals("") || telefon.equals("")|| adresIlce.equals("Adres Giriniz"))
        {
            Toast.makeText(this, "Tüm Alanları Doldurunuz", Toast.LENGTH_LONG).show();
        }

        else if (pass.equals(passAgain))
        {




                mAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        HashMap<String, Object> tedarikciInfo = new HashMap<>(); // anahtar string, değerler object olsun.

                        tedarikciInfo.put("usermail", email);
                        tedarikciInfo.put("sirketunvan", sirketUnvan);
                        tedarikciInfo.put("vergino", vergiNo);
                        tedarikciInfo.put("telefon", telefon);
                        tedarikciInfo.put("adresIlce", adresIlce);
                        tedarikciInfo.put("date", FieldValue.serverTimestamp());

                        firebaseFirestore.collection("tedarikcibilgileri").add(tedarikciInfo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                Toast.makeText(UyeOlActivity.this, "Kayıt Başarılı id: " + documentReference.getId(), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(UyeOlActivity.this, TedarikciMainActivity.class);

                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UyeOlActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UyeOlActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });


        }
        else {
            Toast.makeText(this, "Şifrenizi Kontrol Ediniz", Toast.LENGTH_LONG).show();

        }

    }



}