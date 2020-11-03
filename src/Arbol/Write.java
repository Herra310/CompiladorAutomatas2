package Arbol;

import Compilador.Token;

public class Write extends Statement{

    private Expresion exp;

    public Expresion getExp() {
        return exp;
    }

    public void setExp(Expresion exp) {
        this.exp = exp;
    }


    @Override
    public Statement getSta() {
        return null;
    }

    @Override
    public void setSta2(Statement sta) {

    }

    @Override
    public Statement getSta2() {
        return null;
    }

    @Override
    public Token getIgual() {
        return null;
    }

    @Override
    public void setIgual(Token igual) {

    }

    @Override
    public Token getPrimIdent() {
        return null;
    }

    @Override
    public void setPrimIdent(Token primIdent) {

    }

    @Override
    public void setSta(Statement sta) {

    }


}