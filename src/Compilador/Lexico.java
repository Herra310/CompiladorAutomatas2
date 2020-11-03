package Compilador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexico {
    private final ArrayList<String> PalabrasReservadas;
    private final String[] tiposDeTokens = {
            "identifier", //0
            "relational operator",
            "aritmetical operator",
            "integer literal",
            "open key",
            ";", //5
            "=",
            "open parentheses",
            "close key",
            "close parentheses",
            "class", //10
            "else",
            "false",
            "if",
            "true",
            "while", //15
            "write",
            "type"}; // 17
    private final String[] palabrasReservadas = {"boolean","class","else","false","if","integer","true","while","write"};

    public Lexico() {
        PalabrasReservadas = new ArrayList<>();
        PalabrasReservadas.addAll(Arrays.asList(palabrasReservadas));
    }

    public void analizarLinea(String linea, int l, int c) {
        while (linea != null && linea.length() > 0) {
            int corte = linea.indexOf(" ");
            String palabra;
            if (corte == -1) {
                palabra = linea;
                if (palabra.contains("{") || palabra.contains("=") ||
                        palabra.contains(";") || palabra.contains(">") || palabra.contains("<")) {
                    separaTokens(palabra);
                    linea = "";
                    continue;
                }
                linea = "";
            } else {
                palabra = linea.substring(0, corte);
                if (palabra.contains("{") || palabra.contains("=") ||
                        palabra.contains(";") || palabra.contains(">") || palabra.contains("<")) {
                    separaTokens(palabra);
                    linea = linea.substring(corte + 1);
                    continue;
                }
                if (palabra.equals("")) {
                    linea = linea.substring(corte + 1);
                    continue;
                }
                linea = linea.substring(corte + 1);
            }
            if (palabra.charAt(0)==9)
                palabra=palabra.substring(1);
            if (palabra.length()==0)
                continue;
            boolean pres = comprobarPalabra(palabra);
            String tipo = tipoDeToken(palabra);
            if (tipo.equals("Error")) {
                mostrarError(palabra.charAt(0), l, c + 1);
                continue;
            }
            c = c + corte + 1;
            MainCompilador.agregarToken(palabra, pres, tipo);
        }
    }


    //Retorna el tipo de token en base a la palabra
    public String tipoDeToken(String palabra) {
        if (palabra.equals("("))
            return tiposDeTokens[7];
        if (palabra.equals(")"))
            return tiposDeTokens[9];
        if (palabra.equals("="))
            return tiposDeTokens[6];
        if (palabra.equals(";"))
            return tiposDeTokens[5];
        if (palabra.equals("{"))
            return tiposDeTokens[4];
        if (palabra.equals("}"))
            return tiposDeTokens[8];
        if (palabra.equals("+") || palabra.equals("-") || palabra.equals("*") || palabra.equals("/"))
            return tiposDeTokens[3];
        if (palabra.equalsIgnoreCase("boolean") || palabra.equalsIgnoreCase("integer"))
            return tiposDeTokens[17];
        if (palabra.equals("<") || palabra.equals("==") || palabra.equals("!=") || palabra.equals(">") || palabra.equals("<=") || palabra.equals(">="))
            return tiposDeTokens[2];
        if (palabra.equals("true"))
            return tiposDeTokens[14];
        if(palabra.equals("false"))
            return tiposDeTokens[12];
        if(palabra.equals("if"))
            return tiposDeTokens[13];
        if(palabra.equals("while"))
            return tiposDeTokens[15];
        if(palabra.equals("write"))
            return tiposDeTokens[16];
        if(palabra.equals("class"))
            return tiposDeTokens[10];
        try {
            Integer.parseInt(palabra);
            return tiposDeTokens[3];
        } catch (Exception ignored) {
        }
        if (validarExpresion(palabra))
            return tiposDeTokens[0];
        return "Error";
    }

    //Primer llamado de la busqueda binaria
    public boolean comprobarPalabra(String palabra) {
        return busquedaBinaria(palabra, 0, PalabrasReservadas.size());
    }



    //Muestra errores léxicos
    public void mostrarError(char token, int linea, int columna) {
        System.out.println("\u001B[31m" + "Error en la línea " + linea + ",columna " + columna + ", " + token + " es un simbolo no permitido" + "\u001B[0m");
    }

    //Valida que el identifier cumpla con la expresion
    public boolean validarExpresion(String palabra) {
        Pattern pat = Pattern.compile("[A-Za-z0-9]");
        Matcher mat = pat.matcher(palabra);
        return mat.find();
    }

    //Separa tokens sin espacios entre ellos
    public void separaTokens(String palabra) {
        int i;
        String t;
        while (palabra.length() != 0) {
            if (palabra.charAt(0) == '(' || palabra.charAt(0) == ')') {
                MainCompilador.agregarToken(palabra.charAt(0) + "", false, tipoDeToken(palabra.charAt(0) + ""));
                if (palabra.length() != 1) {
                    palabra = palabra.substring(1);
                    continue;
                }
                palabra = "";
                continue;
            }
            if (palabra.indexOf("{") == 0 || palabra.indexOf("}") == 0) {
                MainCompilador.agregarToken(palabra.substring(0, 1), false, tipoDeToken(palabra.charAt(0) + ""));
                if (palabra.length() != 1) {
                    palabra = palabra.substring(1);
                    continue;
                }
                palabra = "";
                continue;
            }
            if (palabra.indexOf(";") == 0) {
                MainCompilador.agregarToken(";", false, ";");
                if (palabra.length() != 1) {
                    palabra = palabra.substring(1);
                    continue;
                }
                palabra = "";
                continue;
            }
            try {
                if (
                        (palabra.charAt(0) == '=' || palabra.charAt(0) == '>' || palabra.charAt(0) == '<')
                                &&
                                (palabra.charAt(1) == '=' || palabra.charAt(1) == '>' || palabra.charAt(1) == '<')
                ) {
                    String aux = palabra.substring(0, 2);
                    MainCompilador.agregarToken(aux, false, tipoDeToken(aux));
                    if (palabra.length() != 2) {
                        palabra = palabra.substring(2);
                        continue;
                    }
                    palabra = "";
                    continue;
                }
            } catch (Exception ignored) {

            }
            if (palabra.charAt(0) == '=') {
                MainCompilador.agregarToken(palabra.charAt(0) + "", false, tipoDeToken(palabra.charAt(0) + ""));
                if (palabra.length() != 1) {
                    palabra = palabra.substring(1);
                    continue;
                }
                palabra = "";
                continue;
            }
            for (i = 0; validarExpresion(palabra.charAt(i)); i++) ;
            t = palabra.substring(0, i);
            boolean pres = comprobarPalabra(t);
            String tipo = tipoDeToken(t);
            MainCompilador.agregarToken(t, pres, tipo);
            palabra = palabra.substring(i);
        }
    }

    //Busqueda para verificar palabra reservada
    private boolean busquedaBinaria(String palabra, int izq, int der) {
        int medio = (der + izq) / 2;
        int band = palabra.compareTo(PalabrasReservadas.get(medio));
        if (band == 0)
            return true;
        if (izq == der || (izq == der - 1))
            return false;
        if (band > 0)
            return busquedaBinaria(palabra, medio, der);
        return busquedaBinaria(palabra, izq, medio);
    }

    //Valida que sean Letras de la a-z mayusculas o minusculas o numeros del 0-9
    public static boolean validarExpresion(char c) {
        Pattern pat = Pattern.compile("[A-Za-z0-9]");
        Matcher mat = pat.matcher(c + "");
        return mat.find();
    }
}
