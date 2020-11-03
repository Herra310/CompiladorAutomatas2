package Arbol;

import Compilador.Token;

public class SinglExp extends Expresion {

    private Token token;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public Token getPrimerIdent() {
        return null;
    }

    @Override
    public void setPrimerIdent(Token primerIdent) {

    }

    @Override
    public Token getOperador() {
        return null;
    }

    @Override
    public void setOperador(Token operador) {

    }

    @Override
    public Token getSegundoIdent() {
        return null;
    }

    @Override
    public void setSegundoIdent(Token segundoIdent) {

    }
}