package me.alumia.listadecompras

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val produtosAdapter = ProdutoAdapter(this)
        list_view_produtos.adapter = produtosAdapter

        btn_adicionar.setOnClickListener {
            //Criando a Intent explícita
            val intentCadastro = Intent(this, CadastroActivity::class.java)

            //iniciando a atividade.
            startActivity(intentCadastro)
        }

        list_view_produtos.setOnItemLongClickListener { adapterView: AdapterView<*>, view: View, position: Int, id: Long ->

            var itemParaRemover = produtosAdapter.getItem(position)
            produtosAdapter.remove(itemParaRemover)

            //deletando do banco de dados
            deletarProduto(itemParaRemover.id)

            AtualizaValorTotal(produtosAdapter as List<Produto>)

            toast("item deletado com sucesso")

            true // Esse true informa que o click foi realizado.
        }
    }


    // TODO: replicar logica da lista de produtos
    override fun onResume() {
        super.onResume()

        val adapter = list_view_produtos.adapter as ProdutoAdapter

        database.use {

            //Efetuando uam consulta no banco de dados
            select("produtos").exec {

                //Criando o parser que montará o objeto produto
                val parser = rowParser {

                        id: Int, nome: String,
                        quantidade: Int,
                        valor: Double,
                        foto: ByteArray? ->
                    //Colunas do banco de dados


                    //Montagem do objeto Produto com as colunas do banco
                    Produto(id, nome, quantidade, valor, foto?.toBitmap())
                }

                adapter.clear()
                var listaProdutos = parseList(parser);
                adapter.addAll(listaProdutos)
                AtualizaValorTotal(listaProdutos)
            }
        }


    }

    fun AtualizaValorTotal(produtos : List<Produto>) {
        val soma = produtos.sumByDouble { it.valor * it.quantidade }

        val formatadorNumerico = NumberFormat.getCurrencyInstance()

        txt_total.text = "TOTAL: ${formatadorNumerico.format(soma)}"
    }

    fun deletarProduto(idProduto:Int) {

        database.use {

            var idParaRemover = arrayOf(idProduto.toString())
//            delete("produtos", "id = {_id}", _id to idProduto)
        }

    }
}
