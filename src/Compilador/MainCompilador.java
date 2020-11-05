package Compilador;

import java.io.IOException;
import java.util.ArrayList;

public class MainCompilador {

    public static ArrayList<Token> tokens;

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
        new Sintactico(obtenerTipos());
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
