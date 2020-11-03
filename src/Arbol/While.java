package Arbol;

import Compilador.Token;

public class While extends Statement{

    private Expresion exp;
    private Statement sta;

    public Expresion getExp() {
        return exp;
    }

    public void setExp(Expresion exp) {
        this.exp = exp;
    }

    public Statement getSta() {
        return sta;
    }

    public void setSta(Statement sta) {
        this.sta = sta;
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
}