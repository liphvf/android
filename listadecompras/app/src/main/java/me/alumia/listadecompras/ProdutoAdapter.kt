package me.alumia.listadecompras

import android.widget.ArrayAdapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.text.NumberFormat

class ProdutoAdapter(contexto: Context) : ArrayAdapter<Produto>(contexto, 0) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view: View

        if (convertView != null) {
            view = convertView
        }
        else{
            view = LayoutInflater.from(context).inflate(R.layout.list_view_item, parent, false)
        }

        val item = getItem(position)

        val txt_produto = view.findViewById<TextView>(R.id.txt_item_produto)
        val txt_qtd = view.findViewById<TextView>(R.id.txt_item_qtd)
        val txt_valor = view.findViewById<TextView>(R.id.txt_item_valor)
        val img_produto = view.findViewById<ImageView>(R.id.img_item_foto)


//        NumberFormat.getCurrencyInstance(Locale("pt", "br")) Caso queira passar uma moeda
        val formatadorMoeada = NumberFormat.getCurrencyInstance()

        txt_qtd.text = item.quantidade.toString()
        txt_produto.text = item.nome
        txt_valor.text = formatadorMoeada.format(item.valor)

        if (item.foto != null){
            img_produto.setImageBitmap(item.foto)
        }

        return view
    }
}