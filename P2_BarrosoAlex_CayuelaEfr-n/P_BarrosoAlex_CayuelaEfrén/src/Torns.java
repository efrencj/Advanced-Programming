import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Torns <E>{

    // Constructor que inicialitza la llista de torns
    private class nodeTorn{
        public E moviment;
        public nodeTorn seguent;
        public nodeTorn(E moviment, nodeTorn seguent){
            this.moviment = moviment;
            this.seguent = seguent;

        }
    }
    private nodeTorn llistatTorns;
    // Constructor que carrega la llista de torns des d'un fitxer
    public Torns(){
        llistatTorns=null;
    }
    // Mètode per afegir un torn al final de la llista
    public void afegirTorn(E torn) {

        nodeTorn nouTorn=new nodeTorn(torn, null);
        if(llistatTorns==null){
            llistatTorns=nouTorn;
        }else{
            nodeTorn aux=llistatTorns;
            while(aux.seguent!=null){
                aux=aux.seguent;
            }
            aux.seguent=nouTorn;
        }
    }

    // Mètode per agafar el primer torn a la llista i s'elimina
    public E agafarPrimerTorn() throws NoSuchElementException {
        if(llistatTorns==null){
            throw new NoSuchElementException("Llistat de torns buit");
        }
        else {
            E primerMoviment=llistatTorns.moviment;
            llistatTorns=llistatTorns.seguent; //actualitzem la llista de torns
            return primerMoviment;
        }
    }

    // Mètode per guardar la llista de torns a un fitxer
    public void guardarAFitxer(String nomFitxer) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(nomFitxer));
        nodeTorn aux=llistatTorns;
        while(aux!=null){
            writer.write(aux.moviment.toString()); //TODO: a lo mejor hay que hacer un toString.
            writer.newLine();
            aux=aux.seguent;
        }
        writer.close();
    }

    // Mètode per carregar la llista de torns des d'un fitxer
    public void carregarDesDeFitxer(String nomFitxer) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(nomFitxer));
        String linia;
        while ((linia = reader.readLine()) != null) {
            afegirTorn((E) linia);
        }
        reader.close();
    }
}
