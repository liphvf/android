package me.alumia.listadecompras

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

            btn_inserir.setOnClickListener{
            val produto = txt_produto.text.toString()
            if(produto.isEmpty()) {
                txt_produto.error = "Preencha um valor"
                return@setOnClickListener
            }

//            produtosAdapter.add(produto)
            txt_produto.text.clear()
        }
    }
}
