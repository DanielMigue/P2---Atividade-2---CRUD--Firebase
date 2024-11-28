package com.example.novo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Controller_CRUD extends AppCompatActivity {

    private TextView idtxt, pricetxt;
    private EditText idC, priceC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);
        idC = findViewById(R.id.editTextNumber);
        priceC = findViewById(R.id.editTextNumberDecimal);

        idtxt = findViewById(R.id.textView6);
        pricetxt = findViewById(R.id.textView7);
    }

    public void cadastrar(View view) {
        ProdutosServices service = new ProdutosServices();

        String id = String.valueOf(idC.getText());
        String price = String.valueOf(priceC.getText());

        if (id.isEmpty() || price.isEmpty()) {
            Toast.makeText(view.getContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        int idInt = Integer.parseInt(id);
        float priceFloat = Float.parseFloat(price);

        service.cadastrar(idInt,priceFloat, this);
    }

    public void deletar(View view) {
        ProdutosServices service = new ProdutosServices();

        String id = String.valueOf(idC.getText());

        if (id.isEmpty()) {
            Toast.makeText(view.getContext(), "Preencha o campo ID.", Toast.LENGTH_SHORT).show();
            return;
        }

        int idInt = Integer.parseInt(id);

        service.deletar(idInt, this);
    }

    public void ler(View view) {
        ProdutosServices service = new ProdutosServices();

        String idd = String.valueOf(idC.getText());

        if (idd.isEmpty()) {
            Toast.makeText(view.getContext(), "Preencha o campo ID.", Toast.LENGTH_SHORT).show();
            return;
        }

        int idInt = Integer.parseInt(idd);

        service.buscar(idInt, this, idtxt, pricetxt);
    }

    public void alterar(View view) {
        ProdutosServices service = new ProdutosServices();

        String id = String.valueOf(idC.getText());
        String price = String.valueOf(priceC.getText());

        if (id.isEmpty() || price.isEmpty()) {
            Toast.makeText(view.getContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        int idInt = Integer.parseInt(id);
        float priceFloat = Float.parseFloat(price);

        service.alterar(idInt, priceFloat, this);

    }
}
