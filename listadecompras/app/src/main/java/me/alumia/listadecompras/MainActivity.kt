package me.alumia.listadecompras

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.toast
import java.text.NumberFormat
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var botaoAdicionar: Button? = null
    private var listViewProdutos: ListView? = null
    private var produtos: ArrayList<Produto>? = null
    private var produtoAdapter: ProdutoAdapter? = null
    private var valorTotal: TextView? = null
    private var _db: BancoLocal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listViewProdutos = findViewById(R.id.list_view_produtos)
        botaoAdicionar = findViewById(R.id.btn_adicionar)
        valorTotal = findViewById(R.id.txt_total)

        produtos = ArrayList()
        produtoAdapter = ProdutoAdapter(this, produtos)
        listViewProdutos?.adapter = produtoAdapter

        _db = BancoLocal(this)
        produtos?.addAll(_db!!.getProdutos())
        produtoAdapter?.notifyDataSetChanged()


        btn_adicionar.setOnClickListener {
            //Criando a Intent explícita
            val intentCadastro = Intent(this, CadastroActivity::class.java)

            //iniciando a atividade.
            startActivity(intentCadastro)
        }

        listViewProdutos?.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent, view, position, id ->
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
        produtos?.addAll(_db?.getProdutos()!!)

        produtoAdapter?.notifyDataSetChanged()

        AtualizaValorTotal()
    }

    private fun AtualizaValorTotal() {

        val soma = produtos?.sumByDouble { it.valor!! * it.quantidade!! }

        val formatadorNumerico = NumberFormat.getCurrencyInstance()

        valorTotal?.text = "TOTAL: " + formatadorNumerico.format(soma)
    }
}
