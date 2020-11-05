package Arbol;

import java.util.ArrayList;

public class Programa {

    public String clase;
    public String nombreClase;
    public ArrayList<DefVar> defvariables;
    public Statement stat;

    public Programa() {
        defvariables=new ArrayList<>();
    }
}
