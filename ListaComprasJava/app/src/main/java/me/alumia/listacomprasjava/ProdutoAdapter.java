package me.alumia.listacomprasjava;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProdutoAdapter extends ArrayAdapter<Produto> {

    private Context context;
    private ArrayList<Produto> produtos;

    public ProdutoAdapter(Context context, ArrayList<Produto> produtos) {
        super(context, 0 , produtos);

        this.context  = context;
        this.produtos = produtos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View _convertView = convertView;
        final ViewHolder viewHolder;

        if (produtos != null) {

            // Linkar o xml para classe java
           if (_convertView == null ){
               // Obtendo uma instancia do LayoutInflater apartir do servico
               LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

               // Inflando a View e adicionando a _convertView.
               _convertView = inflater.inflate(R.layout.list_view_item, parent, false);

               viewHolder = new ViewHolder();
               viewHolder.imgItemFoto = _convertView.findViewById(R.id.img_item_foto);
               viewHolder.txtItemProduto = _convertView.findViewById(R.id. txt_item_produto);
               viewHolder.txtQuantidade = _convertView.findViewById(R.id.txt_item_qtd);
               viewHolder.txtValor = _convertView.findViewById(R.id.txt_item_valor);

               _convertView.setTag(viewHolder);
           }
           else {
               viewHolder = (ViewHolder) _convertView.getTag();

           }
            viewHolder.imgItemFoto.setImageBitmap(produtos.get(position).getFoto());
            viewHolder.txtItemProduto.setText(produtos.get(position).getNome());
            viewHolder.txtQuantidade.setText(produtos.get(position).getQuantidade());
            viewHolder.txtValor.setText(String.valueOf(produtos.get(position).getValor()));
        }
        return _convertView;
    }

    private static class ViewHolder {
        public ImageView imgItemFoto;
        public TextView txtItemProduto;
        public TextView txtQuantidade;
        public TextView txtValor;
    }
}
