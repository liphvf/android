package me.alumia.listadecompras

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val produtosAdapter = ProdutoAdapter(this)
        list_view_produtos.adapter = produtosAdapter

        btn_adicionar.setOnClickListener{
            //Criando a Intent explícita
            val intentCadastro = Intent(this, CadastroActivity::class.java)

            //iniciando a atividade
            startActivity(intentCadastro)
        }

        list_view_produtos.setOnItemLongClickListener{ adapterView: AdapterView<*>, view: View, position: Int, id: Long ->

            var itemParaRemover = produtosAdapter.getItem(position)

            produtosGlobal.remove(itemParaRemover)
            produtosAdapter.remove(itemParaRemover)
            AtualizaValorTotal()

            true // Esse true informa que o click foi realizado.
        }
    }

    override fun onResume() {
        super.onResume()

        val adapter = list_view_produtos.adapter as ProdutoAdapter

        adapter.clear()
        adapter.addAll(produtosGlobal)

        AtualizaValorTotal()
    }

    fun AtualizaValorTotal() {
        val soma = produtosGlobal.sumByDouble { it.valor * it.quantidade }

        val formatadorNumerico = NumberFormat.getCurrencyInstance()

        txt_total.text = "TOTAL: ${formatadorNumerico.format(soma)}"
    }
}
