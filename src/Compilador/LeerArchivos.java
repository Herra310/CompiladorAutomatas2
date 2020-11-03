package Compilador;

import java.io.*;

public class LeerArchivos {

    private BufferedReader bufferedReader;

    public LeerArchivos(String nom) {
        try {
            File archivo = new File("src/" + nom + ".txt");
            FileReader fileReader = new FileReader(archivo);
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            System.out.println("Problema al cargar el archivo");
        }
    }

    public String leerSigLinea() throws IOException {
        return bufferedReader.readLine();
    }
}
