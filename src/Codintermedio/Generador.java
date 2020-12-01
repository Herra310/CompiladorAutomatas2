package Codintermedio;
import Arbol.*;
import java.util.ArrayList;
public class Generador {
    static ArrayList<Cuadruple> Cuadruples;
    static int contT = 0;

    public static ArrayList<Cuadruple> generarCod(Programa arbol){
        Cuadruples = new ArrayList<>();
        generarStat(arbol.stat);
        Cuadruples.add( new Cuadruple("End","","","") );
        return Cuadruples;
    }

    public static void generarStat(Statement stat){
        Class<? extends Statement> clase = stat.getClass();
        String tipoClase = clase.getSimpleName();
        switch (tipoClase){
            case "If":
                String op = "";
                String arg = generarExp(((If)stat).exp);
                if (!arg.startsWith("t"))
                    op = "Jz";
                int apuntador = Cuadruples.size();
                Cuadruples.add( new Cuadruple(op,arg,"","") );
                generarStat(((If) stat).st1);
                Cuadruples.add( new Cuadruple("Jp","","","") );
                Cuadruples.get(apuntador).setResultado( (Cuadruples.size()+1)+"" );
                apuntador = Cuadruples.size()-1;
                generarStat(((If) stat).st2);
                Cuadruples.get(apuntador).setResultado( (Cuadruples.size()+1)+"" );
                return;
            case "Asignacion":
                String op2 = "=";
                String arg21 = generarExp(((Asignacion)stat).exp);
                String re = ((Asignacion) stat).primIdent;
                Cuadruples.add( new Cuadruple(op2,arg21,"",re) );
                return;
            case "While":
                String op3 = "";
                String arg31 = generarExp(((While) stat).exp);
                if (!arg31.startsWith("t"))
                    op3 = "Jz";
                int apuntador2 = Cuadruples.size();
                Cuadruples.add( new Cuadruple(op3,arg31,"","") );
                generarStat(((While) stat).sta);
                Cuadruples.add( new Cuadruple("Jp","","",(apuntador2+1)+"") );
                Cuadruples.get(apuntador2).setResultado( (Cuadruples.size()+1)+"" );
                return;
            case "Write":
                String op4 = "Write";
                String re4 = generarExp(((Write) stat).exp);
                Cuadruples.add( new Cuadruple(op4,"","",re4) );
        }
    }

    public static String generarExp(Expresion exp){
        Class<? extends Expresion> clase = exp.getClass();
        String tipoClase = clase.getSimpleName();
        switch (tipoClase){
            case "SinglExp":
                return ((SinglExp) exp).token;
            case "Operacion":
                String op = ((Operacion) exp).operador;
                String arg1 = ((Operacion) exp).primerIdent;
                String arg2 = ((Operacion) exp).segundoIdent;
                String res = "t"+(++contT);
                Cuadruples.add( new Cuadruple(op,arg1,arg2,res) );
                return res;
        }
        return "";
    }
}
