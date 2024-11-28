package com.example.novo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Controller_Cadastro extends AppCompatActivity {

    private Intent in;
    private FirebaseAuth mAuth;
    private EditText fieldEmail, fieldPass;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        mAuth = FirebaseAuth.getInstance();
        fieldEmail = findViewById(R.id.editTextTextEmailAddress);
        fieldPass = findViewById(R.id.editTextTextPassword);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);
    }

    public void cadastrar(View view) {

        String email, password;

        email = String.valueOf(fieldEmail.getText());
        password = String.valueOf(fieldPass.getText());

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(view.getContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        // Cadastro bem-sucedido
                        Toast.makeText(view.getContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        in = new Intent(this, MainActivity.class);
                        startActivity(in);
                        Log.d("CadastroUsuario", "Usuário cadastrado: " + mAuth.getCurrentUser().getEmail());
                    } else {
                        // Tratamento de erro
                        String erro = "Erro ao cadastrar usuário.";
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            erro = "Este email já está em uso.";
                        }
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(view.getContext(), erro, Toast.LENGTH_SHORT).show();
                        Log.e("CadastroUsuario", "Erro: ", task.getException());
                    }
                });
    }
}
