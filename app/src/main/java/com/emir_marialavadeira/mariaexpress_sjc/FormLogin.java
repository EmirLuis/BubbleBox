package com.emir_marialavadeira.mariaexpress_sjc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.emir_marialavadeira.mariaexpress_sjc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FormLogin extends AppCompatActivity {

    private TextView text_tela_cadastro;
    private TextView text_tela_esqueceu_senha;
    private EditText edit_email,edit_senha;
    private Button bt_entrar;
    private ProgressBar progressBar;
    String[] mensagens = {"Preencha todos os campos","Login efetuado com sucesso","Email/Senha não cadastrado"};

    @Override
    public void onBackPressed() {
    }

    ImageButton buttonExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        buttonExit = findViewById(R.id.buttonExit);

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        getSupportActionBar().hide();
        IniciarComponentes();
        text_tela_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FormLogin.this,FormCadastro.class);
                startActivity(intent);
            }
        });

        text_tela_esqueceu_senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FormLogin.this,FormEsqueceuSenha.class);
                startActivity(intent);
            }
        });

        bt_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edit_email.getText().toString();
                String senha = edit_senha.getText().toString();

                if (email.isEmpty() || senha.isEmpty()){
//                    Toast.makeText(getBaseContext(),mensagens [0],
//                            Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(v, mensagens[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                }else{
                    AutenticarUsuario(v);
                }
            }
        });
    }

    private void AutenticarUsuario(View view){

        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    progressBar.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Telaprincipal();
                        }
                    },4000);
                }else {
                    String erro;
                    try {
                        throw task.getException();
                    } catch (Exception e) {
//                        erro = "Email/Senha não cadastrado";
//                    }
//                    Toast.makeText(getBaseContext(), mensagens[2],
//                            Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(view, mensagens[2], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();

        if (usuarioAtual != null){
            Telaprincipal();
        }
    }

    private void Telaprincipal(){
        Intent intent = new Intent(FormLogin.this,FormTelaPrincipal.class);
        startActivity(intent);
        finish();
    }

    private void IniciarComponentes(){
        text_tela_cadastro = findViewById(R.id.text_tela_cadastro);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        text_tela_esqueceu_senha = findViewById(R.id.text_esqueceu_senha);
        bt_entrar = findViewById(R.id.bt_entrar);
        progressBar = findViewById(R.id.progressbar);
    }
}