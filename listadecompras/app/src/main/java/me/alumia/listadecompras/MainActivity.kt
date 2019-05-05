package me.alumia.listadecompras

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.toast
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

        Log.i("PosNotify", produtos?.size.toString())
//        Log.i("PosNotify", produtoAdapter?.produtos?.size.toString())



        btn_adicionar.setOnClickListener {
            //Criando a Intent explícita
            val intentCadastro = Intent(this, CadastroActivity::class.java)

            //iniciando a atividade.
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
        Log.i("Resume", produtos?.size.toString())
//        Log.i("Resume", produtoAdapter?.produtos?.size.toString())
    }

    fun AtualizarListaDeProdutos() {

        produtos?.clear()
        produtoAdapter?.notifyDataSetChanged()
        produtos?.addAll(_db?.getProdutos()!!)

        produtoAdapter?.notifyDataSetChanged()

        AtualizaValorTotal()
    }

    private fun AtualizaValorTotal() {

        val soma = produtos?.sumByDouble { it.valor!! * it.quantidade!! }

        val formatadorNumerico = NumberFormat.getCurrencyInstance()

        txt_total.text = "TOTAL: " + formatadorNumerico.format(soma)
    }
}
