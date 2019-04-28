package me.alumia.listacomprasjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button botaoAdicionar;
    private ListView listView;
    private ArrayList<Produto> produtos;
    private ArrayAdapter<Produto> produtoAdapter;

    private TextView valorTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view_produtos);
        botaoAdicionar = findViewById(R.id.btn_adicionar);
        valorTotal = findViewById(R.id.txt_total);

        produtos = new ArrayList<Produto>();
        produtoAdapter = new ProdutoAdapter(this, produtos);
        listView.setAdapter(produtoAdapter);

        // produtoAdapter.notifyDataSetChanged(); // notifica que alterou para a lista ser recarregada.


        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Criando a Intent explícita
                Intent intentCadastro = new Intent(MainActivity.this, CadastroActivity.class);

                //iniciando a atividade
                startActivity(intentCadastro);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                produtos.remove(position);
                produtoAdapter.notifyDataSetChanged();

                AtualizaValorTotal();

                Toast.makeText(MainActivity.this, "tem deletado com sucesso", Toast.LENGTH_SHORT).show();
                return true;
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        AtualizaValorTotal();
    }

    // TODO: converter para logica de variaveis na classe
    private void AtualizaValorTotal() {

        double soma = 0.0;

        for (Produto produto : produtos) {
            soma += produto.getValor();
        }


        NumberFormat formatadorNumerico = NumberFormat.getCurrencyInstance();


        valorTotal.setText("TOTAL: " + formatadorNumerico.format(soma));
    }
}
