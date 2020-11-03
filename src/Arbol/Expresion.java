package Arbol;

import Compilador.Token;

public abstract class Expresion {

    public abstract Token getToken();

    public abstract void setToken(Token token);

    public abstract Token getPrimerIdent();

    public abstract void setPrimerIdent(Token primerIdent);

    public abstract Token getOperador();

    public abstract void setOperador(Token operador);

    public abstract Token getSegundoIdent();

    public abstract void setSegundoIdent(Token segundoIdent);
}