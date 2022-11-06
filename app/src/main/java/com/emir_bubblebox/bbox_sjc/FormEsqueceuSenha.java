package com.emir_bubblebox.bbox_sjc;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.emir_bubblebox.bbox_sjc.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class FormEsqueceuSenha extends AppCompatActivity {

    private EditText edit_email;
    private Button bt_confirmar;
    private FirebaseAuth auth;
    String[] mensagens = {"Preencha o email cadastrado",
                          "Email inválido",
                          "Email enviado - Verifique cx. postal",
                          "Email não existe na base do App"};

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_esqueceu_senha);
        edit_email = findViewById(R.id.edit_email);
        bt_confirmar = findViewById(R.id.bt_entrar);
        auth = FirebaseAuth.getInstance();

//        getSupportActionBar().hide();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.turquoise_light)));



        bt_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edit_email.getText().toString();

                if (email.isEmpty() || email.trim().isEmpty()){
//                    Toast.makeText(getBaseContext(),mensagens [0],
//                            Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(v, mensagens[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    ValidarUsuario(v);
                    if (!isEmailValido(email)){
//                        Toast.makeText(getBaseContext(),mensagens [1],
//                                Toast.LENGTH_SHORT).show();

                        Snackbar snackbar = Snackbar.make(v, mensagens[1], Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.show();
                    }else {
//                        RecuperarSenha();
//                        String email = edit_email.getText().toString().trim();

                        auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
//                Toast.makeText(getBaseContext(),"Mensagem enviada - Verifique seu email",
//                        Toast.LENGTH_LONG).show();

                                Snackbar snackbar = Snackbar.make(v, mensagens[2], Snackbar.LENGTH_SHORT);
                                snackbar.setBackgroundTint(Color.WHITE);
                                snackbar.setTextColor(Color.BLACK);
                                snackbar.show();
//                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

//                Toast.makeText(getBaseContext(),"Email não existe na base do App",
//                        Toast.LENGTH_LONG).show();

                                Snackbar snackbar = Snackbar.make(v, mensagens[3], Snackbar.LENGTH_SHORT);
                                snackbar.setBackgroundTint(Color.WHITE);
                                snackbar.setTextColor(Color.BLACK);
                                snackbar.show();

                            }
                        });

                    }
                }
            }
        });
    }

//    private void RecuperarSenha(){
//
//        String email = edit_email.getText().toString().trim();
//
//        auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(getBaseContext(),"Mensagem enviada - Verifique seu email",
//                        Toast.LENGTH_LONG).show();
//
//               Snackbar snackbar = Snackbar.make(xx, mensagens[2], Snackbar.LENGTH_SHORT);
//                snackbar.setBackgroundTint(Color.WHITE);
//                snackbar.setTextColor(Color.BLACK);
//                snackbar.show();
//                finish();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//                Toast.makeText(getBaseContext(),"Email não existe na base do App",
//                        Toast.LENGTH_LONG).show();
//
//                Snackbar snackbar = Snackbar.make(xx, mensagens[3], Snackbar.LENGTH_SHORT);
//                snackbar.setBackgroundTint(Color.WHITE);
//                snackbar.setTextColor(Color.BLACK);
//                snackbar.show();
//
//            }
//        });
//    }

    private void ValidarUsuario(View view) {
        String email = edit_email.getText().toString();
    }

    private boolean isEmailValido (String email){
        boolean resultado = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        return resultado;
    }
}