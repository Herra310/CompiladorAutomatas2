package Compilador;

import Arbol.DefVar;
import Tabla.Semantico;
import Tabla.Simbolo;

import java.io.IOException;
import java.util.ArrayList;

public class MainCompilador {

    public static Arbol.Programa Arbol;
    public static ArrayList<Token> tokens;
    public static Sintactico sin;
    public static ArrayList<Simbolo> tablasim;
    public static void main(String[] args) throws IOException {
        LeerArchivos arch = new LeerArchivos("Codigo");
        Lexico analizadorLexico = new Lexico();
        tokens = new ArrayList<>();
        String linea = "";
        int l = 0;

        while (linea != null) {
            l++;
            linea = arch.leerSigLinea();
            analizadorLexico.analizarLinea(linea, l, 0);
        }
        sin = new Sintactico(obtenerTipos());
        Arbol = sin.analizar();
        ObtenerSimbolos();
        Semantico.analizarStatement(Arbol.stat, tablasim);
        System.out.println("SIU");
    }

    private static void ObtenerSimbolos() {
        tablasim = new ArrayList<>();
        Simbolo s;
        for (DefVar df : Arbol.defvariables) {
            s = new Simbolo(df.nombre, df.tipo);
            tablasim.add(s);
        }
    }


    private static ArrayList<String> obtenerTipos() {
        ArrayList<String> al = new ArrayList<>();
        for (Token token : tokens)
            al.add(token.getTipo());
        return al;
    }

    public static void agregarToken(String palabra, boolean pres, String tipo) {
        tokens.add(new Token(palabra, pres, tipo));
    }

}
