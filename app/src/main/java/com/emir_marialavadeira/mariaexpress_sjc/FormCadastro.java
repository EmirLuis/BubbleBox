package com.emir_marialavadeira.mariaexpress_sjc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.emir_marialavadeira.mariaexpress_sjc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FormCadastro extends AppCompatActivity {

    private EditText edit_nome, edit_email, edit_cpf, edit_data_nasc, edit_celular, edit_cep, edit_senha;
    private Button bt_cadastro;
    String[] mensagens = {"Preencha todos os campos",
                          "Conta cadastrada com sucesso"};

    String usuarioID;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);
//        getSupportActionBar().hide();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.pink)));
        IniciarComponentes();

        bt_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edit_email.getText().toString();
                String nome = edit_nome.getText().toString();
                String CPF = edit_cpf.getText().toString();
                String data_nasc = edit_data_nasc.getText().toString();
                String celular = edit_celular.getText().toString();
                String CEP = edit_cep.getText().toString();
                String senha = edit_senha.getText().toString();

                if (nome.isEmpty() || email.isEmpty() || email.trim().isEmpty() || CPF.isEmpty() ||
                        data_nasc.isEmpty() || celular.isEmpty() || CEP.isEmpty() || senha.isEmpty()) {
//                    Toast.makeText(getBaseContext(), mensagens[0],
//                            Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(v, mensagens[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                } else {
                    CadastrarUsuario(v);
                }
            }
        });
    }

    private void CadastrarUsuario(View v) {

        String email = edit_email.getText().toString().trim();
        String senha = edit_senha.getText().toString().trim();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    SalvarDadosUsuario();

//                    Toast.makeText(getBaseContext(), mensagens[1],
//                            Toast.LENGTH_SHORT).show();
//                    finish();

                    Snackbar snackbar = Snackbar.make(v, mensagens[1], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                    finish();

                } else {
                    String erro;
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Senha deve ter mais de 5 caracteres";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erro = "Conta já cadastrada";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "Email inválido";
                    } catch (Exception e) {
                        erro = "Erro ao cadastrar usuário";
                    }
//                    Toast.makeText(getBaseContext(), erro,
//                            Toast.LENGTH_SHORT).show();

                   Snackbar snackbar = Snackbar.make(v, erro, Snackbar.LENGTH_SHORT);
                   snackbar.setBackgroundTint(Color.WHITE);
                   snackbar.setTextColor(Color.BLACK);
                   snackbar.show();
                }
            }
        });
    }

    private void usuarioID() {
    }

    private void SalvarDadosUsuario() {
        String nome = edit_nome.getText().toString();
        String CPF = edit_cpf.getText().toString();
        String data_nasc = edit_data_nasc.getText().toString();
        String celular = edit_celular.getText().toString();
        String CEP = edit_cep.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> usuarios = new HashMap<>();
        usuarios.put("nome", nome);
        usuarios.put("CPF", CPF);
        usuarios.put("data_nasc", data_nasc);
        usuarios.put("celular", celular);
        usuarios.put("CEP", CEP);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("usuarios").document(usuarioID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("db", "Salvo com sucesso");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db_error", "Erro ao salvar" + e.toString());
                    }
                });
    }

    private void IniciarComponentes() {
        edit_nome = findViewById(R.id.edit_nome);
        edit_email = findViewById(R.id.edit_email);
        edit_cpf = findViewById(R.id.edit_cpf);
        edit_data_nasc = findViewById(R.id.edit_data_nasc);
        edit_celular = findViewById(R.id.edit_celular);
        edit_cep = findViewById(R.id.edit_CEP);
        edit_senha = findViewById(R.id.edit_senha);
        bt_cadastro = findViewById(R.id.bt_cadastro);
    }
}