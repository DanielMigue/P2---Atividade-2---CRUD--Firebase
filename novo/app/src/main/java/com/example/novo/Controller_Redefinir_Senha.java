package com.example.novo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Controller_Redefinir_Senha extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText fieldEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);

        firebaseAuth = FirebaseAuth.getInstance();
        fieldEmail = findViewById(R.id.editTextTextEmailAddress3);
    }


    public void redefinir(View view) {
        String email = String.valueOf(fieldEmail.getText());

        if (email.isEmpty()) {
            Toast.makeText(view.getContext(), "Por favor, insira seu email.", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Email enviado com sucesso
                        Toast.makeText(view.getContext(), "Email de redefinição enviado. Verifique sua caixa de entrada!", Toast.LENGTH_SHORT).show();
                        Log.d("RedefinirSenha", "Email de redefinição enviado para: " + email);
                    } else {
                        // Tratamento de erro
                        String erro = "Erro ao enviar email de redefinição.";
                        if (task.getException() != null) {
                            erro += " " + task.getException().getMessage();
                        }
                        Toast.makeText(view.getContext(), erro, Toast.LENGTH_SHORT).show();
                        Log.e("RedefinirSenha", "Erro: ", task.getException());
                    }
                });
    }
}
