package me.alumia.listadecompras

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cadastro.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.toast
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper

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

            //pegando os valores digitados pelo usuário
            val produto = txt_produto.text.toString()
            val qtd = txt_qtd.text.toString()
            val valor = txt_valor.text.toString()

            if (produto.isEmpty()) {
                txt_produto.error = "Preencha o nome do produto"
                return@setOnClickListener
            } else if (qtd.isEmpty()) {
                txt_qtd.error = "Preencha a quantidade"
                return@setOnClickListener
            } else if (qtd.isEmpty()) {
                txt_valor.error = "Preencha o valor"
                return@setOnClickListener
            }

            database.use {

                val idProduto = insert(
                    "Produtos",
                    "nome" to produto,
                    "quantidade" to qtd,
                    "valor" to valor.toDouble(),
                    "foto" to imageBitMap
                )

                if (idProduto != -1L) {
                    toast("Item Inserido com sucesso")

//                  Limpeza de caixas
                    txt_produto.text.clear()
                    txt_qtd.text.clear()
                    txt_valor.text.clear()
                } else {
                    toast("Erro ao inserir no banco de dados")
                }
            }
        }
    }

    fun abrirGaleria() {
        // definindo a ação de conteúdo
        val intent = Intent(Intent.ACTION_GET_CONTENT)

        // definindo filtro para imagens
        intent.type = "image/*"

        // inicializando a activity com resultado
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), COD_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Verifica se a resposta é do código que você precisa e teve o resultado ok
        if (requestCode == COD_IMAGE && resultCode == Activity.RESULT_OK) {
            // Verifica se veio dados
            if (data != null) {

                // Lendo a URI da imagem
                val inputStream = contentResolver.openInputStream(data.getData())

                //transformando o resultado em bitmap
                imageBitMap = BitmapFactory.decodeStream(inputStream)

                img_foto_produto.setImageBitmap(imageBitMap)
            }
        }
    }

}
