package Compilador;

public class Token {

    private final boolean reservada;
    private final String token;
    private String tipo;

    public Token(String t,boolean r,String ti){
        reservada=r;
        token=t;
        tipo=ti;
        if(tipo.equals("identifier") && reservada)
            tipo=token;
    }

    public String getTipo() {
        return tipo;
    }

    public String toString(){
        return token+"\t"+reservada+"\t"+tipo;
    }

    public String getToken(){ return token; }

}
