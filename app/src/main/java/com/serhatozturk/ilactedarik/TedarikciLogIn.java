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
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.serhatozturk.ilactedarik.databinding.ActivityTedarikciLogInBinding;

public class TedarikciLogIn extends AppCompatActivity {
    ActivityTedarikciLogInBinding binding;
    ActionBar actionBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTedarikciLogInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        actionBar = getSupportActionBar();
        actionBar.setTitle("Ecza Deposu Girisi");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(203, 67, 53 )));


        binding.btnGiris.setBackgroundColor(Color.rgb(203, 67, 53));

        FirebaseUser user= mAuth.getCurrentUser();
        if(user!=null) {
            Intent intent = new Intent(TedarikciLogIn.this, TedarikciMainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tedarikci_uyeol, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if(item.getItemId()==R.id.uyeOl){

            Intent intentToTedMain=new Intent(TedarikciLogIn.this,UyeOlActivity.class);
            intentToTedMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentToTedMain);

        }

       else if(item.getItemId()==R.id.sifremiUnuttum) {



          // String m_Text = "";

           AlertDialog.Builder builder = new AlertDialog.Builder(this);
           builder.setTitle("E-Mail adresinizi giriniz");

// Set up the input
           final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
           input.setInputType(InputType.TYPE_CLASS_TEXT);
           builder.setView(input);

// Set up the buttons
           builder.setPositiveButton("Gonder", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {

                   if (input.getText().toString().equals("")) {
                       Toast.makeText(TedarikciLogIn.this, "E-posta Adresinizi Giriniz.", Toast.LENGTH_LONG).show();
                   } else {

                       String e_posta = input.getText().toString();


                       mAuth.sendPasswordResetEmail(e_posta)
                               .addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       if (task.isSuccessful()) {

                                           Toast.makeText(TedarikciLogIn.this, "Parola Sifirlama Icin E-posta Gonderildi.", Toast.LENGTH_LONG).show();

                                       } else {

                                           Toast.makeText(TedarikciLogIn.this, "Hata Olustu.", Toast.LENGTH_LONG).show();

                                       }
                                   }
                               });


                   }
               }
               });
           builder.setNegativeButton("Geri", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   dialog.cancel();
               }
           });

           builder.show();

       }
        return super.onOptionsItemSelected(item);
    }

    public void girisOnClick(View view)
    {
        String email=binding.editTextTextEmailAddress.getText().toString();
        String pass=binding.editTextTextPassword.getText().toString();
        if (email.equals("")||pass.equals(""))
        {
            Toast.makeText(this,"E-posta ve parola giriniz.",Toast.LENGTH_LONG).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent=new Intent(TedarikciLogIn.this,TedarikciMainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(TedarikciLogIn.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }

    }

}