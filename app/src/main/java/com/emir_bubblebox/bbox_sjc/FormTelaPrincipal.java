package com.emir_bubblebox.bbox_sjc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.emir_bubblebox.bbox_sjc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class FormTelaPrincipal extends AppCompatActivity {

    private TextView nomeUsuario,emailUsuario,cpfUsuario,dt_nascUsuario,celularUsuario,cepUsuario;
    private Button bt_deslogar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;
    String usuarioNome;
    String usuarioEmail;
    String usuarioCPF;
    String usuarioDt_nasc;
    String usuarioCelular;
    String usuarioCep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_tela_principal);

        getSupportActionBar().hide();
        IniciarComponentes();

        bt_deslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(FormTelaPrincipal.this,FormLogin.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                     if (documentSnapshot != null){
                         nomeUsuario.setText(documentSnapshot.getString("nome"));
                         emailUsuario.setText(email);
                         cpfUsuario.setText(documentSnapshot.getString("CPF"));
                         dt_nascUsuario.setText(documentSnapshot.getString("data_nasc"));
                         celularUsuario.setText(documentSnapshot.getString("celular"));
                         cepUsuario.setText(documentSnapshot.getString("CEP"));
                     }
            }
        });
    }

    private void IniciarComponentes(){
        nomeUsuario = findViewById(R.id.textNomeUsuario);
        emailUsuario = findViewById(R.id.textEmailUsuario);
        cpfUsuario = findViewById(R.id.textCpfUsuario);
        dt_nascUsuario = findViewById(R.id.textData_nascUsuario);
        celularUsuario = findViewById(R.id.textCelularUsuario);
        cepUsuario = findViewById(R.id.textCepUsuario);
        bt_deslogar = findViewById(R.id.bt_deslogar);
    }
}