package me.alumia.listacomprasjava;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CadastroActivity extends AppCompatActivity {

    private int COD_IMAGE = 101;
    private Bitmap imageBitMap;

    private ImageView fotoProduto;
    private Button botaoInserir;

    private TextView produtoNome;
    private TextView quantidade;
    private TextView valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        fotoProduto = findViewById(R.id.img_foto_produto);
        botaoInserir = findViewById(R.id.btn_inserir);

        fotoProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });

        botaoInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produtoNome = findViewById(R.id.txt_produto);
                quantidade = findViewById(R.id.txt_qtd);
                valor = findViewById(R.id.txt_valor);

                String produtoNomeText = produtoNome.getText().toString();
                String quantidadeText = quantidade.getText().toString();
                String valorText = valor.getText().toString();

                if (produtoNomeText.isEmpty()) {
                    produtoNome.setError("Preencha o nome do produto");
                    return;

                } else if (quantidadeText.isEmpty()) {
                    quantidade.setError("Preencha a quantidade");
                    return;

                } else if (valorText.isEmpty()) {
                    valor.setError("Preencha o valor");
                    return;
                }

                BancoLocal db = new BancoLocal(CadastroActivity.this);
                db.insereDado(produtoNomeText, Integer.parseInt(quantidadeText) , Double.parseDouble(valorText), imageBitMap );

                CadastroActivity.this.finish();
            }
        });

    }

    private void abrirGaleria() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), COD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == COD_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {

                imageBitMap = (Bitmap) data.getExtras().get("data");
                fotoProduto.setImageBitmap(imageBitMap);
            }
        }
    }
}
