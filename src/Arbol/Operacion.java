package Arbol;

import Compilador.Token;

public class Operacion extends Expresion {

    private Token primerIdent;
    private Token operador;
    private Token segundoIdent;

    public Token getPrimerIdent() {
        return primerIdent;
    }

    public void setPrimerIdent(Token primerIdent) {
        this.primerIdent = primerIdent;
    }

    public Token getOperador() {
        return operador;
    }

    public void setOperador(Token operador) {
        this.operador = operador;
    }

    public Token getSegundoIdent() {
        return segundoIdent;
    }

    public void setSegundoIdent(Token segundoIdent) {
        this.segundoIdent = segundoIdent;
    }


    @Override
    public Token getToken() {
        return null;
    }

    @Override
    public void setToken(Token token) {

    }

}
