package me.alumia.listadecompras

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val produtosAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)

        val item = "Feijão"

        produtosAdapter.add(item)

        list_view_produtos.adapter = produtosAdapter

        btn_inserir.setOnClickListener{
            val produto = txt_produto.text.toString()
            if(produto.isEmpty()) {
                txt_produto.error = "Preencha um valor"
                return@setOnClickListener
            }

            produtosAdapter.add(produto)
            txt_produto.text.clear()
        }

        list_view_produtos.setOnItemLongClickListener{ adapterView: AdapterView<*>, view: View, position: Int, id: Long ->


            produtosAdapter.remove(produtosAdapter.getItem(position))
            true // Esse true informa que o click foi realizado.
        }
    }
}
