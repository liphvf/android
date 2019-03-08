package liphvf.quantovalemeudinheiro

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_calcular.setOnClickListener {

            var valorSalario = 0.0;
            if (txt_valor_salario.text.isEmpty()) {
                Toast.makeText(this, "Valor do salário não definido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            valorSalario = txt_valor_salario.text.toString().toDouble()

            var horasTrabalhaadas = 0.0;
            if (txt_horas_trabalhadas.text.isEmpty()) {
                Toast.makeText(this, "Valor de horas trabalhadas não definido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            horasTrabalhaadas = txt_horas_trabalhadas.text.toString().toDouble()

            var valorProduto = 0.0;
            if (txt_valor_produto.text.isEmpty()) {
                Toast.makeText(this, "Valor do produto não definido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            valorProduto = txt_valor_produto.text.toString().toDouble()

            var resultado = valorProduto / (valorSalario/ horasTrabalhaadas)
            txt_resultado.text = "Você gastará $resultado hora(s) para obter este produto.";
        }
    }
}
