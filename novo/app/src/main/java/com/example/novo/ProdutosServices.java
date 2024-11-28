package com.example.novo;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProdutosServices {

    public void cadastrar(int ID, float Price, Context context){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("Produtos").document("Produto_" + ID);
        docRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Toast.makeText(context, "Produto com "+ ID + " já existe", Toast.LENGTH_SHORT).show();
                            Log.d("Firestore", "Produto já existe.");
                        } else {
                            Map<String, Object> produtoCompleto = new HashMap<>();
                            produtoCompleto.put("id", ID);
                            produtoCompleto.put("preco", Price);

                            docRef.set(produtoCompleto)
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d("Firestore", "Produto adicionado com ID: " + ID);
                                        Toast.makeText(context, "Produto adicionado com ID: " + ID, Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.w("Firestore", "Erro ao adicionar produto: ", e);
                                        Toast.makeText(context, "Erro ao adicionar produto", Toast.LENGTH_SHORT).show();
                                    });
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w("Firestore", "Erro ao verificar produto: ", e);
                    Toast.makeText(context, "Erro ao verificar produto", Toast.LENGTH_SHORT).show();
                });
    }

    public void deletar(int ID, Context context){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("Produtos").document("Produto_" + ID);
        docRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            docRef.delete();
                            Log.d("Firestore", "Produto deletado com sucesso: " + ID);
                            Toast.makeText(context, "Produto deletado com sucesso: " + ID, Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("Firestore", "Produto não existe.");
                            Toast.makeText(context, "Produto com "+ ID + " não existe", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w("Firestore", "Erro ao deletar produto: ", e);
                    Toast.makeText(context, "Erro ao deletar produto", Toast.LENGTH_SHORT).show();
                });
    }

    public void buscar(int ID, Context context, TextView idTxt, TextView precoTxt){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("Produtos").document("Produto_" + ID);
        docRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            try {
                                int id = Objects.requireNonNull(document.getLong("id")).intValue();
                                idTxt.setText("ID: " + id);
                            } catch (NullPointerException e) {
                                Log.e("Firestore", "Erro: O campo 'id' não está disponível.", e);
                                Toast.makeText(context, "Erro: id não encontrado.", Toast.LENGTH_SHORT).show();
                            }

                            try {
                                float preco = Objects.requireNonNull(document.getDouble("preco")).floatValue();
                                DecimalFormat df = new DecimalFormat("R$#,##0.00");
                                String precoFormatado = df.format(preco);
                                precoTxt.setText("Preço: " + precoFormatado);
                            } catch (NullPointerException e) {
                                Log.e("Firestore", "Erro: O campo 'preco' não está disponível.", e);
                                Toast.makeText(context, "Erro: Preço não encontrado.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("Firestore", "Produto não encontrado: ", e);
                    Toast.makeText(context, "Produto com "+ ID + " não encontrado", Toast.LENGTH_SHORT).show();
                });
    }

    public void alterar(int ID, float Price, Context context){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("Produtos").document("Produto_" + ID);
        docRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            docRef.update("preco", Price)
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d("Firestore", "Preço atualizado com sucesso.");
                                        Toast.makeText(context, "Preço atualizado com sucesso.", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.w("Firestore", "Erro ao atualizar preço: ", e);
                                        Toast.makeText(context, "Erro ao atualizar preço.", Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            Log.d("Firestore", "Produto não encontrado.");
                            Toast.makeText(context, "Produto com "+ ID + " não encontrado", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w("Firestore", "Erro ao alterar produto: ", e);
                    Toast.makeText(context, "Erro ao alterar produto", Toast.LENGTH_SHORT).show();
                });
    }
}
