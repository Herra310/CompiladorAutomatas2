package Tabla;
import Arbol.*;
import Compilador.Token;
import java.util.ArrayList;

public class Semantico {

    public static boolean analizarStatement(Statement stat, ArrayList<Simbolo> tablaS) {
        Class<? extends Statement> clase = stat.getClass();
        String tipoClase = clase.getSimpleName();
        switch (tipoClase){
            case "If":
                String e = analizarExpresion(((If) stat).exp,tablaS);
                if (e.equals("error"))
                    return false;
                if (!e.equalsIgnoreCase("boolean")){
                    System.out.println("Error es necesario un valor booleano para la expresión del If");
                    return false;
                }
                return analizarStatement(((If)stat).st1,tablaS) & analizarStatement(((If) stat).st2,tablaS);
            case "Asignacion":
                String tokenId = ((Asignacion)stat).primIdent;
                String tipoId = "";
                for (Simbolo s:tablaS)
                    if (s.getIdentificador().equals(tokenId)) {
                        tipoId = s.getTipo();
                        break;
                    }
                if (tipoId.equals("")){
                    System.out.println("Error el"+tokenId+" no ha sido declarado");
                    return false;
                }
                String tipoExp = analizarExpresion(((Asignacion)stat).exp,tablaS);
                if (!tipoId.equalsIgnoreCase(tipoExp)){
                    System.out.println("Error tipos no compatibles");
                    return false;
                }
                return true;
            case "While":
                String exp2 = analizarExpresion(((While)stat).exp,tablaS);
                if (exp2.equals("error"))
                    return false;
                if (!exp2.equalsIgnoreCase("boolean")){
                    System.out.println("Error es necesario un valor booleano para la expresión del While");
                    return false;
                }
                return analizarStatement(((While) stat).sta,tablaS);
            case "Write":
                return !analizarExpresion(((Write)stat).exp,tablaS).equals("error");
        }
        return false;
    }

    public static String analizarExpresion(Expresion exp, ArrayList<Simbolo> tablaS){
        Class<? extends Expresion> clase = exp.getClass();
        String tipoClase = clase.getSimpleName();
        switch (tipoClase){
            case "SinglExp":
                if (((SinglExp) exp).token.equalsIgnoreCase("True")||((SinglExp) exp).token.equalsIgnoreCase("false"))
                    return "boolean";
                if (isNumeric(((SinglExp)exp).token))
                    return "integer";
                for (Simbolo s:tablaS)
                    if (((SinglExp) exp).token.equals(s.getIdentificador()))
                        return s.getTipo();
                System.out.println("Error el"+((SinglExp)exp).token+" no ha sido declarado");
                return "error";
            case "Operacion":
                String t2 = ((Operacion) exp).primerIdent;
                String t3 = ((Operacion) exp).segundoIdent;
                String tipoPrimTok = "";
                String tipoSegTok = "";
                for (Simbolo s:tablaS) {
                    if (s.getIdentificador().equals(t2))
                        tipoPrimTok = s.getTipo();
                    if (s.getIdentificador().equals(t3))
                        tipoSegTok = s.getTipo();
                }
                if (!tipoPrimTok.equals(tipoSegTok)){
                    System.out.println("Error tipos de variables incompatibles");
                    return "error";
                }
                return tipoPrimTok;
        }
        return "";
    }

    public static boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }
}