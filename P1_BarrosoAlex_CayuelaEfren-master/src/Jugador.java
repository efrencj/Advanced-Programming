import java.util.ArrayList;
public class Jugador <E extends Itipopieza> {
    private ArrayList<E> piezasVivas;

    public Jugador(ArrayList<E> piezasVivas) {
        this.piezasVivas = piezasVivas;
    }

    private E buscarEnPosicion(int fil, int col) {
        for (E pieza : piezasVivas) {
            if (pieza.getFila() == fil && pieza.getColumna() == col) {
                return pieza;
            }
        }
        return null;
    }

    public boolean eliminarPiezaPosicion(int fil, int col) throws FiJocException {
        E pieza = buscarEnPosicion(fil, col);
        if (pieza != null) {
            piezasVivas.remove(pieza);
            if(pieza.fiJoc()){
                throw new FiJocException("Has matat al rei!");
            }
            return true;
        }
        return false;
    }

    public void moverPieza(int fil1, int col1, int fil2, int col2) throws RuntimeException {
        E pieza = buscarEnPosicion(fil1, col1);
        if(pieza == null){
            throw new RuntimeException("Peça no trobada!");
        }

        try{
            E pieza2 = buscarEnPosicion(fil2, col2);

            pieza.setPosicion(fil2, col2);

            if(pieza2 != null && eliminarPiezaPosicion(fil2,col2)) {
                System.out.println("Peça matada!");
            }
        }
        catch(RuntimeException e){
            System.out.println("No pots moure la peça:" + e.getMessage());
        }
        catch(FiJocException e){
            System.out.println("Has guanyat: " + e.getMessage());
        }

    }

    public ArrayList<E> getPiezasVivas() {
        return piezasVivas;
    }
}