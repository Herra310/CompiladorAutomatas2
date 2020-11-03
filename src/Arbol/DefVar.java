package Arbol;
import Compilador.Token;
public class DefVar {

    private Token tipo;
    private Token nombre;

    public Token getTipo() {
        return tipo;
    }

    public void setTipo(Token tipo) {
        this.tipo = tipo;
    }

    public Token getNombre() {
        return nombre;
    }

    public void setNombre(Token nombre) {
        this.nombre = nombre;
    }
}

