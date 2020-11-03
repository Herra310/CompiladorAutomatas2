package Arbol;

import Compilador.Token;

public class Asignacion extends Statement {

    private Token primIdent;
    private Token igual;
    private Expresion exp;

    public Token getPrimIdent() {
        return primIdent;
    }

    public void setPrimIdent(Token primIdent) {
        this.primIdent = primIdent;
    }

    public Token getIgual() {
        return igual;
    }

    public void setIgual(Token igual) {
        this.igual = igual;
    }

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
    public void setSta(Statement sta) {

    }

}