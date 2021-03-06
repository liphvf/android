package me.alumia.listadecompras

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private var produtos: ArrayList<Produto>? = null
    private var produtoAdapter: ProdutoAdapter? = null
    private var _db: BancoLocal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        produtos = ArrayList()
        produtoAdapter = ProdutoAdapter(this, produtos)
        list_view_produtos.adapter = produtoAdapter

        _db = BancoLocal(this)
        produtos?.addAll(_db!!.getProdutos())
        produtoAdapter?.notifyDataSetChanged()

        btn_adicionar.setOnClickListener {
            val intentCadastro = Intent(this, CadastroActivity::class.java)
            startActivity(intentCadastro)
        }

        list_view_produtos.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent, view, position, id ->
            val produtoParaRemover = produtos?.get(position)

            _db?.deletaRegistro(produtoParaRemover?.id!!)
            produtos?.removeAt(position)
            produtoAdapter?.notifyDataSetChanged()

            AtualizaValorTotal()

            Toast.makeText(this@MainActivity, "Item excluido.", Toast.LENGTH_SHORT).show()
            true
        }
    }

    override fun onResume() {
        super.onResume()
        AtualizarListaDeProdutos()
    }

    fun AtualizarListaDeProdutos() {
        produtos?.clear()
        _db?.getProdutos()?.let { produtos?.addAll(it) }
        produtoAdapter?.notifyDataSetChanged()

        AtualizaValorTotal()
    }

    private fun AtualizaValorTotal() {
        val soma = produtos?.sumByDouble { it.valor!! * it.quantidade!! }

        val formatadorNumerico = NumberFormat.getCurrencyInstance()

        txt_total.text = "TOTAL: " + formatadorNumerico.format(soma)
    }
}