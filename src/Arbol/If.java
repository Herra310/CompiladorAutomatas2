package Arbol;

import Compilador.Token;

public class If extends Statement {

    private Expresion exp;
    private Statement st1;
    private Statement st2;

    public Expresion getExp() {
        return exp;
    }

    public void setExp(Expresion exp) {
        this.exp = exp;
    }

    public Statement getSta() {
        return st1;
    }

    public void setSta(Statement st1) {
        this.st1 = st1;
    }

    public Statement getSta2() {
        return st2;
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

    public void setSta2(Statement st2) {
        this.st2 = st2;
    }
}
