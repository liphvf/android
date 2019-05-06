package me.alumia.listadecompras

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_cadastro.*
import org.jetbrains.anko.toast

class CadastroActivity : AppCompatActivity() {

    val COD_IMAGE = 101
    var imageBitMap: Bitmap? = null

    private var produtoNome: TextView? = null
    private var quantidade: TextView? = null
    private var valor: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        img_foto_produto.setOnClickListener {
            abrirGaleria()
        }

        btn_inserir.setOnClickListener {
            this.produtoNome = findViewById(R.id.txt_produto)
            this.quantidade = findViewById(R.id.txt_qtd)
            this.valor = findViewById(R.id.txt_valor)

            var produtoNomeText = produtoNome?.text.toString()
            val quantidadeText = quantidade?.text.toString()
            val valorText = valor?.text.toString()

            if (produtoNomeText.isEmpty()) {
                this.produtoNome?.error ="Preencha o nome do produto"
                return@setOnClickListener

            } else if (quantidadeText.isEmpty()) {
                quantidade?.error = "Preencha a quantidade"
                return@setOnClickListener

            } else if (valorText.isEmpty()) {
                valor?.error = "Preencha o valor"
                return@setOnClickListener
            }

            val db = BancoLocal(this@CadastroActivity)
            db.insereDado(
                produtoNomeText,
                Integer.parseInt(quantidadeText),
                java.lang.Double.parseDouble(valorText),
                imageBitMap
            )

            this@CadastroActivity.finish()
        }
    }

    fun abrirGaleria() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), COD_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == COD_IMAGE && resultCode == Activity.RESULT_OK) {
            data?.let {
                imageBitMap = data.extras.get("data") as Bitmap
                img_foto_produto.setImageBitmap(imageBitMap)
            }
        }
    }
}
