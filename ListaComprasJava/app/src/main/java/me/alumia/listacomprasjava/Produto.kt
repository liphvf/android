package me.alumia.listacomprasjava

import android.graphics.Bitmap

data class Produto(var id: Int? = null, var nome: String? = "", var quantidade: Int? = 0, var valor: Double? = 0.0, var foto: Bitmap? = null)