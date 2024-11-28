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

public class MainActivity extends AppCompatActivity {

    private Intent in;
    private FirebaseAuth firebaseAuth;
    private EditText fieldEmail, fieldPass;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        fieldEmail = findViewById(R.id.editTextTextEmailAddress2);
        fieldPass = findViewById(R.id.editTextTextPassword2);
        progressBar = findViewById(R.id.progressBar2);

        progressBar.setVisibility(View.GONE);
    }

    public void login(View view) {
        String email = String.valueOf(fieldEmail.getText());
        String password = String.valueOf(fieldPass.getText());

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(view.getContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Login bem-sucedido
                            Toast.makeText(view.getContext(), "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            in = new Intent(this, Controller_CRUD.class);
                            startActivity(in);
                            Log.d("LoginUsuario", "Usuário logado: " + firebaseAuth.getCurrentUser().getEmail());
                        } else {
                            // Tratamento de erro
                            String erro = "Erro ao realizar login.";
                            if (task.getException() != null) {
                                erro += " " + task.getException().getMessage();
                            }
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(view.getContext(), erro, Toast.LENGTH_SHORT).show();
                            Log.e("LoginUsuario", "Erro: ", task.getException());
                        }
                    });
    }

    public boolean isUsuarioLogado() {
        return firebaseAuth.getCurrentUser() != null;
    }

    public void cadastrar(View view) {
        in = new Intent(this, Controller_Cadastro.class);
        startActivity(in);
    }

    public void senha(View view) {
        in = new Intent(this, Controller_Redefinir_Senha.class);
        startActivity(in);
    }
}