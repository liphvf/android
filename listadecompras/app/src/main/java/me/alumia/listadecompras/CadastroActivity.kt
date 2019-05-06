package me.alumia.listadecompras

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {

    val COD_IMAGE = 101
    var imageBitMap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        img_foto_produto.setOnClickListener {
            abrirGaleria()
        }

        btn_inserir.setOnClickListener {

            var produtoNomeText = txt_produto?.text.toString()
            val quantidadeText = txt_qtd?.text.toString()
            val valorText = txt_valor?.text.toString()

            if (produtoNomeText.isEmpty()) {
                this.txt_produto?.error ="Preencha o nome do produto"
                return@setOnClickListener

            } else if (quantidadeText.isEmpty()) {
                txt_qtd?.error = "Preencha a quantidade"
                return@setOnClickListener

            } else if (valorText.isEmpty()) {
                txt_valor?.error = "Preencha o valor"
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
