import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Torns<E> {

    private ArrayList<E> llistatTorns;

    public Torns() {
        this.llistatTorns = new ArrayList<>();
    }

    public Torns (String nomFitxer) throws IOException {
        this.llistatTorns= new ArrayList<>();
         carregarDesDeFitxer(nomFitxer);
         if(llistatTorns.isEmpty()){
             throw new IOException("Fitxer buit o no es poden carregar els torns.");
         }
    }
    public void afegirTorn(E torn){
        llistatTorns.add(torn);
    }
    public void guardarFitxer (String nomFitxer) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFitxer))) {
            for (E torn : llistatTorns) {
                writer.write(torn.toString()); //en el toString añado como se modifica el file
                writer.newLine();
            }

        }catch (IOException e){
            throw new IOException("No s'han pogut guardar el fitxer: " + e.getMessage());
        }
    }
    private void carregarDesDeFitxer(String nomFitxer) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomFitxer))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                llistatTorns.add((E)linea);
            }
        } catch (IOException e) {
            throw new IOException("Error en llegir el fitxer: " + e.getMessage());
        }
    }
    public E agafarPrimerTorn() {
        if (llistatTorns.isEmpty()) {
            throw new NoSuchElementException("No hi ha més torns.");
        }
        return llistatTorns.remove(0); // Devuelve y elimina el primer turno.
    }

    public int getNumTornsRestants() {
        return llistatTorns.size();
    }
}

