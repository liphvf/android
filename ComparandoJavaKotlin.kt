// - Meotod com retorno em uma unica linha

String foo() {return "abc";} // JAVA
fun foo() : String = "abc" // KOTLIN --Note que ausencia de {} e ;

// - Atribuição de variável. Tipagem implicita?

String bar  = "bar"; // JAVA
val bar =  "bar" // KOTLIN

// Motodo void
void bar()

// - Sobrecarga

// JAVA
void foo() {}
void foo (int param1) {}
void foo (int param1, String param2) {}
void foo (int param1, string param2, booelan param3 ) {}

// KOTLIN
fun bar (param1: Int = 0, param2 : String = "", param3 : Boolean = false){}

// - Construtor em linha

// JAVA 
public class Usuario {
    private String nome;
    private int idade;

    public Usuario() {

    }

    public Usuario(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public Usuario(String nome){
        this.nome = nome;
    }

    public Usuario (int age) {
        this.age = age;
    }
}

// KOTLIN

data class  Usuario (var nome : String = "", var idade : Int = 0) {

}



// - Try Catch


// JAVA
int i = 0;

try {
    i = 1;
} catch (Exception e) {
    i = 2
}

// Kotlin

var i = try{1} catch (e : Exception) {2}


// - Unificação da Sintaxe de Extends e Implements

// Java
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

}


// KOTLIN
class MainActivity : AppCompatActivity(), View.OnClickListener{

}


// - Criação implícita de Getters e Setters

// JAVA
private String nome;

public String getName(){
    return nome;
}

public void setName(String nome) {
    this.nome = nome;
}

// Kotlin

var name = "";

