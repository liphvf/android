package me.alumia.listadecompras

import android.widget.ArrayAdapter
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.text.NumberFormat
import java.util.ArrayList

class ProdutoAdapter(contexto: Context, var produtos: ArrayList<Produto>? = null) : ArrayAdapter<Produto>(contexto, 0) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {

        Log.i("ProdutoAdater", "chegou na view")

        var _convertView: View? = convertView
        val viewHolder: ViewHolder

        if (produtos != null) {

            // Linkar o xml para classe java
            if (_convertView == null) {
                // Inflando a View e adicionando a _convertView.
                _convertView = LayoutInflater.from(context).inflate(R.layout.list_view_item, parent, false)

                viewHolder = ViewHolder()
                viewHolder.imgItemFoto = _convertView.findViewById(R.id.img_item_foto)
                viewHolder.txtItemProduto = _convertView.findViewById(R.id.txt_item_produto)
                viewHolder.txtQuantidade = _convertView.findViewById(R.id.txt_item_qtd)
                viewHolder.txtValor = _convertView.findViewById(R.id.txt_item_valor)

                _convertView.tag = viewHolder
            } else {
                viewHolder = _convertView.tag as ViewHolder
        }


            var produto = getItem(position)

            Log.i("ProdutoAdater", "chegou aqui")

            val formatadorNumerico = NumberFormat.getCurrencyInstance()

            viewHolder.imgItemFoto?.setImageBitmap(produto.foto)
            viewHolder.txtItemProduto?.text = produto.nome
            viewHolder.txtQuantidade?.text = "x " + produto?.quantidade
            viewHolder.txtValor?.text = formatadorNumerico.format(produto?.valor)
        }

        return _convertView
    }

    companion object {
        private class ViewHolder {
            var imgItemFoto: ImageView? = null
            var txtItemProduto: TextView? = null
            var txtQuantidade: TextView? = null
            var txtValor: TextView? = null
        }
    }
}