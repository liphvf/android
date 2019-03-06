package liphvf.helloworld

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        btn_calcular.setOnClickListener {

//            Toast.makeText(this, "Test", Toast.LENGTH_SHORT )
            var valorSalario = 0.0;

            if (!txt_valor_salario.text.isEmpty()) {
                valorSalario = txt_valor_salario.text.toString().toDouble()
            }

            var horasTrabalhaadas = txt_horas_trabalhadas.text.toString().toDouble()

            var valorProduto = txt_valor_produto.text.toString().toDouble()

            var resultado = valorProduto / (valorSalario/ horasTrabalhaadas)

            txt_resultado.text = "Você gastará $resultado horas de trabalho para obter esse produto.";

        }
    }
}
