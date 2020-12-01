package Codintermedio;

public class Cuadruple {

    private String Operador;
    private String Arg1;
    private String Arg2;
    private String Resultado;

    public Cuadruple(String operador, String arg1, String arg2, String resultado) {
        Operador = operador;
        Arg1 = arg1;
        Arg2 = arg2;
        Resultado = resultado;
    }

    public String getOperador() {
        return Operador;
    }

    public void setOperador(String operador) {
        Operador = operador;
    }

    public String getArg1() {
        return Arg1;
    }

    public void setArg1(String arg1) {
        Arg1 = arg1;
    }

    public String getArg2() {
        return Arg2;
    }

    public void setArg2(String arg2) {
        Arg2 = arg2;
    }

    public String getResultado() {
        return Resultado;
    }

    public void setResultado(String resultado) {
        Resultado = resultado;
    }
}
