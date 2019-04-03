package me.alumia.listadecompras

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

            btn_inserir.setOnClickListener{

                //pegando os valores digitados pelo usuário
            val produto = txt_produto.text.toString()
            val qtd = txt_qtd.text.toString()
            val valor = txt_valor.text.toString()

            if(produto.isEmpty()) {
                txt_produto.error = "Preencha o nome do produto"
                return@setOnClickListener
            } else if (qtd.isEmpty()) {
                txt_qtd.error = "Preencha a quantidade"
                return@setOnClickListener
            } else if(qtd.isEmpty()) {
                txt_valor.error = "Preencha o valor"
                return@setOnClickListener
            }

            val prod = Produto(produto, qtd.toInt(), valor.toDouble())
            produtosGlobal.add(prod)

//          Limpeza de caixas
            txt_produto.text.clear()
            txt_qtd.text.clear()
            txt_valor.text.clear()

        }
    }
}
