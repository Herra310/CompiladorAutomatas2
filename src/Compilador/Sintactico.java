package Compilador;

import Arbol.*;
import java.util.ArrayList;

public class Sintactico {

    private final ArrayList<String> tiposTokens;
    private int contador = 0;
    private boolean bandera;

    public Arbol.Programa Arbol;


    public Sintactico(ArrayList<String> tt) {
        tiposTokens = tt;
        Arbol= new Arbol.Programa();
        analizar();
    }

    private void analizar() {

        if (AnalizarClase(bandera)) {
            System.out.println("El código ha compilado con éxito.");
        }

    }

    public boolean AnalizarClase(boolean bandera) {
        if (tiposTokens.get(contador).equals("class")) {
            Arbol.clase=MainCompilador.tokens.get(contador).getToken();
            contador++;
            if (tiposTokens.get(contador).equals("identifier")) {
                Arbol.nombreClase=MainCompilador.tokens.get(contador).getToken();
                contador++;
                boolean ciclo = true;
                while (ciclo) {
                   DefVar df=AnalizarDeclaracion(bandera);
                   if (df==null) {
                       if (tiposTokens.get(contador).equals("open key")) {
                           ciclo = false;
                           continue;
                       }
                       else{
                           System.out.println("Error se esperaba una llave que abre --> {");
                           return bandera;
                       }
                   }
                       Arbol.defvariables.add(df);
                }
                contador++;
                Arbol.stat=AnalizarStatement(bandera);
                if (Arbol.stat!=null){
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

    public DefVar AnalizarDeclaracion(boolean bandera) {   // inicia y termina la declaracion con el ;
        DefVar df=AnalizarVariable(bandera);
        if (df != null) {
            contador++;
            if (tiposTokens.get(contador).equals(";")) {
                contador++;
                return df;
            } else {
                System.out.println("Error: Se esperaba un ;");
                return null;
            }

        }
        return null;
    }

    public Statement AnalizarStatement(boolean bandera) {
        switch (tiposTokens.get(contador)) {
            case "if" -> {
                If f = AnalizarIfStatement(bandera);
                if (f != null) {
                    return f;
                }
                return null;
            }
            case "while" -> {
                While wh = AnalizarWhileStatement(bandera);
                if (wh != null) {
                    return wh;
                }
                return null;
            }
            case "write" -> {
                Write wr = AnalizarWriteStatement(bandera);
                if (wr != null) {
                    return wr;
                }
                return null;
            }
            case "identifier" -> {
                Asignacion a = AnalizarAsignacionVariables(bandera);
                if (a != null) {
                    return a;
                }
                return null;
            }
            default -> {
                System.out.println("Statement no encontrado");
                return null;
            }
        }
    }

    public DefVar AnalizarVariable(boolean bandera) {  // Analiza la declaracion de una variable
        DefVar df= new DefVar();
        if (tiposTokens.get(contador).equals("type")) {
            df.tipo=MainCompilador.tokens.get(contador).getToken();
            contador++;
            if (tiposTokens.get(contador).equals("identifier")) {
              df.nombre=MainCompilador.tokens.get(contador).getToken();
                if (AnalizarAsignaVar(bandera)) {
                    return df;
                }
                return df;
            }
            System.out.println("Error: Se esperaba un identificador");
            return null;

        }
        return null;
    }

    public Asignacion AnalizarAsignacionVariables(boolean bandera) { //revisa si una variable se le
        Asignacion a=new Asignacion();
        if (tiposTokens.get(contador).equals("identifier")) {//asigna algun valor y termina con ;
            a.primIdent=MainCompilador.tokens.get(contador).getToken();
            contador++;
            if (tiposTokens.get(contador).equals("=")) {
                a.igual=MainCompilador.tokens.get(contador).getToken();
                contador++;
                a.exp=AnalizarExpresion(bandera);
                if (a.exp!=null) {
                    contador++;
                    if (tiposTokens.get(contador).equals(";")) {
                        return a;
                    }
                    System.out.println("Error: Se esperaba un ;");
                    return null;
                }
            }


        }
        System.out.println("Error: Se esperaba un identificador");
        return null;
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


    public If AnalizarIfStatement(boolean bandera) { //Estructura del if
        If f= new If();
        if (tiposTokens.get(contador).equals("if")) {
            contador++;
            if (tiposTokens.get(contador).equals("open parentheses")) {
                contador++;
                f.exp=AnalizarExpresion(bandera);
                if (f.exp!=null) {
                    contador++;
                    if (tiposTokens.get(contador).equals("close parentheses")) {
                        contador++;
                        f.st1=AnalizarStatement(bandera);
                        if(f.st1!=null) {
                            contador++;
                            if (tiposTokens.get(contador).equals("else")) {
                                contador++;
                                f.st2=AnalizarStatement(bandera);
                                if (f.st2!=null) {
                                    return f;
                                } else {
                                    return null;
                                }
                            }System.out.println("Error: Se esperaba un else");
                            return null;
                        } else {
                            return null;
                        }

                    }System.out.println("Error: Se esperaba un parentesis que cierra --> )");
                    return null;

                }
                return null;

            }System.out.println("Error: Se esperaba un Parentesis abierto --> ( ");
            return null;

        }
        return null;
    }

    public Expresion AnalizarExpresion(boolean bandera) { //Revisa Expresiones
        switch (tiposTokens.get(contador)) {
            case "identifier":
                int c = contador;
                contador++;
                if (tiposTokens.get(contador).equals("aritmetical operator")) {
                    Operacion o= new Operacion();
                    o.primerIdent=MainCompilador.tokens.get(c).getToken();
                    o.operador=MainCompilador.tokens.get(contador).getToken();
                    contador++;
                    if ((tiposTokens.get(contador).equals("identifier"))) {
                        o.segundoIdent=MainCompilador.tokens.get(contador).getToken();
                        return o;
                    }
                    System.out.println("Error: Se esperaba un identificador");
                    return null;
                } else {
                    SinglExp se=new SinglExp();
                    se.token=MainCompilador.tokens.get(c).getToken();
                    contador = c;
                    return se;
                }
            case "true":
            case "false":
            case "integer literal":
                SinglExp se=new SinglExp();
                se.token=MainCompilador.tokens.get(contador).getToken();
                return se;
            default:
                System.out.println("Error: Se esperaba una expresion");
                return null;
        }
    }

    public While AnalizarWhileStatement(boolean bandera) { //Estructura del while
        While wh= new While();
        if (tiposTokens.get(contador).equals("while")) {
            contador++;
            if (tiposTokens.get(contador).equals("open parentheses")) {
                contador++;
                wh.exp=AnalizarExpresion(bandera);
                if (wh.exp!=null) {
                    contador++;
                    if (tiposTokens.get(contador).equals("close parentheses")) {
                        contador++;
                        wh.sta=AnalizarStatement(bandera);
                        if (wh.sta!=null) {
                            return wh;
                        } else {
                            return null;
                        }
                    }
                    System.out.println("Error: Se esperaba un parentesis que cierra ---> )");
                    return null;
                }
                return null;
            }
            System.out.println("Error: Se esperaba un parentesis que abre ---> (");
            return null;
        }
        return null;
    }

    public Write AnalizarWriteStatement(boolean bandera) { //Estructura del write
        Write wr=new Write();
        if (tiposTokens.get(contador).equals("write")) {
            contador++;
            if (tiposTokens.get(contador).equals("open parentheses")) {
                contador++;
                wr.exp=AnalizarExpresion(bandera);
                if (wr.exp!=null) {
                    contador++;
                    if (tiposTokens.get(contador).equals("close parentheses")) {
                        contador++;
                        if (tiposTokens.get(contador).equals(";")) {
                            contador++;
                            return wr;
                        }
                        System.out.println("Error: Se esperaba un ;");
                        return null;
                    }
                    System.out.println("Error: Se esperaba un parentesis que cierra ---> )");
                    return null;
                }
            }
            System.out.println("Error: Se esperaba un parentesis que abre ---> (");
            return null;
        }
        return null;
    }


}
