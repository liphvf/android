package me.alumia.listacomprasjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button botaoAdicionar;
    private ListView listViewProdutos;
    private ArrayList<Produto> produtos;
    private ProdutoAdapter produtoAdapter;
    private TextView valorTotal;
    private BancoLocal _db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewProdutos = findViewById(R.id.list_view_produtos);
        botaoAdicionar = findViewById(R.id.btn_adicionar);
        valorTotal = findViewById(R.id.txt_total);

        produtos = new ArrayList<Produto>();
        produtoAdapter = new ProdutoAdapter(this, produtos);
        listViewProdutos.setAdapter(produtoAdapter);

        _db = new BancoLocal(this);
        produtos.addAll(_db.getProdutos());
        produtoAdapter.notifyDataSetChanged();

        // Adiciona ação ao evento de clique.
        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCadastro = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(intentCadastro);
            }
        });

        // Adiciona ação ao clicar longamente
        listViewProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produtoParaRemover = produtos.get(position);

                _db.deletaRegistro(produtoParaRemover.getId());
                produtos.remove(position);
                produtoAdapter.notifyDataSetChanged();

                AtualizaValorTotal();

                Toast.makeText(MainActivity.this, "Item excluido.", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AtualizarListaDeProdutos();
    }

    public void AtualizarListaDeProdutos() {

        produtos.clear();
        produtos.addAll(_db.getProdutos());

        produtoAdapter.notifyDataSetChanged();

        AtualizaValorTotal();
    }

    private void AtualizaValorTotal() {

        double soma = 0.0;
        for (Produto produto : produtos) {
            soma += produto.getValor() * produto.getQuantidade();
        }

        NumberFormat formatadorNumerico = NumberFormat.getCurrencyInstance();

        valorTotal.setText("TOTAL: " + formatadorNumerico.format(soma));
    }
}
