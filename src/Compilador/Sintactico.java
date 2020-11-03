package Compilador;

import java.util.ArrayList;

public class Sintactico {

    private final ArrayList<String> tiposTokens;
    private int contador = 0;
    private boolean bandera;

    public Arbol.Programa Arbol;


    public Sintactico(ArrayList<String> tt) {
        tiposTokens = tt;
        analizar();
    }

    private void analizar() {

        if (AnalizarClase(bandera)) {
            System.out.println("El código ha compilado con éxito.");
        }

    }

    public boolean AnalizarClase(boolean bandera) {
        if (tiposTokens.get(contador).equals("class")) {
            contador++;
            if (tiposTokens.get(contador).equals("identifier")) {
                contador++;
                boolean ciclo = true;
                while (ciclo) {
                   if (!AnalizarDeclaracion(bandera)) {
                       if (tiposTokens.get(contador).equals("open key"))
                           ciclo = false;
                       else{
                           System.out.println("Error se esperaba una llave que abre --> {");
                           return bandera;
                       }
                   }
                }
                contador++;

                if (AnalizarStatement(bandera)){
                    if (tiposTokens.size()>contador) {
                        if (tiposTokens.get(contador).equals("close key")) {
                            bandera = true;
                            return bandera;
                        }
                        System.out.println("Error: Se esperaba una llave que cierra");
                        return bandera;
                    }
                    System.out.println("Error: Se esperaba una llave que cierra");
                    return bandera;
                }
                return bandera;

            }
            System.out.println("Error: Se esperaba un identificador");
            return bandera;
        }
        System.out.println("Error: Se esperaba la palabra class");
        return bandera;
    }

    public boolean AnalizarDeclaracion(boolean bandera) {   // inicia y termina la declaracion con el ;
        if (AnalizarVariable(bandera)) {
            contador++;
            if (tiposTokens.get(contador).equals(";")) {
                contador++;
                bandera = true;
                return bandera;
            } else {
                System.out.println("Error: Se esperaba un ;");
                bandera = false;
                return bandera;
            }

        }
        bandera=false;
        return bandera;
    }

    public boolean AnalizarStatement(boolean bandera) {
        switch (tiposTokens.get(contador)) {
            case "if":
                if(AnalizarIfStatement(bandera)) {
                    bandera = true;
                }
                break;
            case "while":
                if(AnalizarWhileStatement(bandera)){
                    bandera=true;
                }
                break;
            case "write":
                if(AnalizarWriteStatement(bandera)){
                    bandera=true;
                }
                break;
            case "identifier":
                if (AnalizarAsignacionVariables(bandera)) {
                    bandera = true;
                }
                break;
            default:
                bandera = false;
                System.out.println("Statement no encontrado");
                break;
        }
        return bandera;
    }

    public boolean AnalizarVariable(boolean bandera) {  // Analiza la declaracion de una variable
        if (tiposTokens.get(contador).equals("type")) {
            contador++;
            if (tiposTokens.get(contador).equals("identifier")) {
                if (AnalizarAsignaVar(bandera)) {
                    bandera = true;
                    return bandera;
                }
                bandera = true;
                return bandera;
            }
            System.out.println("Error: Se esperaba un identificador");
            return bandera;

        }
        return bandera;
    }

    public boolean AnalizarAsignacionVariables(boolean bandera) { //revisa si una variable se le
        if (tiposTokens.get(contador).equals("identifier")) {   //asigna algun valor y termina con ;
            contador++;
            if (AnalizarAsignaVar(bandera)) {
                if (tiposTokens.get(contador).equals(";")) {
                    bandera = true;
                    return bandera;
                }
                System.out.println("Error: Se esperaba un ;");
                return bandera;

            }

        }
        System.out.println("Error: Se esperaba un identificador");
        return bandera;
    }

    public boolean AnalizarAsignaVar(boolean bandera) { //Asigna un valor a una variable
        int c = contador;
        if (tiposTokens.get(contador).equals("=")) {
            contador++;
            if (tiposTokens.get(contador).equals("integer literal") || tiposTokens.get(contador).equals("true")
                    || tiposTokens.get(contador).equals("false")) {
                contador++;
                bandera = true;
                return bandera;
            }
        } else {
            contador = c;
            bandera = false;
        }

        return bandera;
    }


    public boolean AnalizarIfStatement(boolean bandera) { //Estructura del if
        if (tiposTokens.get(contador).equals("if")) {
            contador++;
            if (tiposTokens.get(contador).equals("open parentheses")) {
                contador++;
                if (AnalizarExpresion(bandera)) {
                    contador++;
                    if (tiposTokens.get(contador).equals("close parentheses")) {
                        contador++;
                        if (AnalizarStatement(bandera)) {
                            contador++;
                            if (tiposTokens.get(contador).equals("else")) {
                                contador++;
                                if (AnalizarStatement(bandera)) {
                                    bandera = true;
                                    return bandera;
                                } else {
                                    bandera = false;
                                    return bandera;
                                }
                            }System.out.println("Error: Se esperaba un else");
                            return bandera;
                        } else {
                            bandera = false;
                            return bandera;
                        }

                    }System.out.println("Error: Se esperaba un parentesis que cierra --> )");
                    bandera = false;
                    return bandera;

                }bandera = false;
                return bandera;

            }System.out.println("Error: Se esperaba un Parentesis abierto --> ( ");
            bandera = false;
            return bandera;

        }
        return bandera;
    }

    public boolean AnalizarExpresion(boolean bandera) { //Revisa Expresiones
        switch (tiposTokens.get(contador)) {
            case "identifier":
                int c = contador;
                contador++;
                if (tiposTokens.get(contador).equals("aritmetical operator")) {
                    contador++;
                    if ((tiposTokens.get(contador).equals("identifier"))) {
                        contador++;
                        bandera = true;
                        break;
                    }
                    System.out.println("Error: Se esperaba un identificador");
                    return bandera;
                } else {
                    contador = c;
                    bandera = true;
                    break;
                }
            case "true":
            case "false":
            case "integer literal":
                bandera = true;
                break;
            default:
                bandera = false;
                System.out.println("Error: Se esperaba una expresion");
                break;
        }
        return bandera;
    }

    public boolean AnalizarWhileStatement(boolean bandera) { //Estructura del while
        if (tiposTokens.get(contador).equals("while")) {
            contador++;
            if (tiposTokens.get(contador).equals("open parentheses")) {
                contador++;
                if (AnalizarExpresion(bandera)) {
                    contador++;
                    if (tiposTokens.get(contador).equals("close parentheses")) {
                        contador++;
                        if (AnalizarStatement(bandera)) {
                            bandera = true;
                            return bandera;
                        } else {
                            bandera = false;
                            return bandera;
                        }
                    }
                    bandera = false;
                    System.out.println("Error: Se esperaba un parentesis que cierra ---> )");
                    return bandera;
                }
                bandera = false;
                return bandera;
            }
            bandera = false;
            System.out.println("Error: Se esperaba un parentesis que abre ---> (");
            return bandera;
        }

        return bandera;
    }

    public boolean AnalizarWriteStatement(boolean bandera) { //Estructura del write
        if (tiposTokens.get(contador).equals("write")) {
            contador++;
            if (tiposTokens.get(contador).equals("open parentheses")) {
                contador++;
                if (tiposTokens.get(contador).equals("integer literal") || tiposTokens.get(contador).equals("true")
                        || tiposTokens.get(contador).equals("false")) {
                    contador++;
                    if (tiposTokens.get(contador).equals("close parentheses")) {
                        contador++;
                        if (tiposTokens.get(contador).equals(";")) {
                            contador++;
                            bandera = true;
                            return bandera;
                        }
                        bandera = false;
                        System.out.println("Error: Se esperaba un ;");
                        return bandera;
                    }
                    bandera = false;
                    System.out.println("Error: Se esperaba un parentesis que cierra ---> )");
                    return bandera;
                }
                bandera = false;
            }
            bandera = false;
            System.out.println("Error: Se esperaba un parentesis que abre ---> (");
            return bandera;
        }
        return bandera;
    }


}
