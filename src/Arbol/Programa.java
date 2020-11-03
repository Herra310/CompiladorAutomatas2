package Arbol;

import Compilador.Token;

import java.util.ArrayList;

public class Programa {

    private Token clase;
    private Token nombreClase;
    private ArrayList<DefVar> defvariables;
    private Statement stat;

    public Programa() {
        this.defvariables = new ArrayList<>();
    }

    public Token getClase() {
        return clase;
    }

    public void setClase(Token clase) {
        this.clase = clase;
    }

    public Token getNombreClase() {
        return nombreClase;
    }

    public void setNombreClase(Token nombreClase) {
        this.nombreClase = nombreClase;
    }

    public void addDefvariables(DefVar defvariable) {
        defvariables.add(defvariable);
    }

    public ArrayList<DefVar> getVariables() {
        return defvariables;
    }

    public Statement getStatment() {
        return stat;
    }

    public void setStatement(Statement s) {
        stat = s;
    }
}
