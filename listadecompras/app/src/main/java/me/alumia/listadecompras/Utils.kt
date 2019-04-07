package me.alumia.listadecompras

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

val produtosGlobal = mutableListOf<Produto>()

fun Bitmap.toByteArray(): ByteArray {

    val stream = ByteArrayOutputStream()

    // Comprimindo a imagem
    this.compress(android.graphics.Bitmap.CompressFormat.PNG, 0, stream)

    // Retornando e transformando em byteArray
    return stream.toByteArray()
}

fun ByteArray.toBitmap() :Bitmap{

    return BitmapFactory.decodeByteArray(this, 0, this.size);
}