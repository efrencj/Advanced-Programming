import java.util.ArrayList;

class Jugador<E extends ItipoPieza> {

    private class NodePieza{
        public E pieza;
        public NodePieza seguent;
        public NodePieza(E pieza, NodePieza seguent)
        {
            this.pieza = pieza;
            this.seguent = seguent;
        }
    }
    //Está Bien.
    private NodePieza piezasVivas;

    public Jugador(ArrayList<E> piezasInicials){
        piezasVivas = new NodePieza(piezasInicials.get(0), null);
        NodePieza aux = piezasVivas;
        for(int i = 1; i < piezasInicials.size(); i++){
            NodePieza peçaCreada = new NodePieza(piezasInicials.get(i), null);
            aux.seguent= peçaCreada;
            aux= peçaCreada;
        }

    }
    public ArrayList<E> getPiezasVivas() {
        ArrayList<E> piezas = new ArrayList<>();
        NodePieza aux = piezasVivas;
        while (aux != null) {
            piezas.add(aux.pieza);
            aux = aux.seguent;
        }
        return piezas;
    }

    // Método para mover una pieza usando la posición anterior
    public void moverPieza(int columnaAnterior, int filaAnterior, int nuevaColumna, int nuevaFila) {
        if (this.buscarEnPosicion(nuevaFila, nuevaColumna) != null)
            throw new RuntimeException("Posició ocupada per una peça del mateix jugador");

        E item = this.buscarEnPosicion(filaAnterior,columnaAnterior);
        if( item == null)
            throw new RuntimeException("Peça no trobada fila:" + filaAnterior + " columna:" + columnaAnterior);

        item.setPosicion(nuevaFila, nuevaColumna);
        System.out.println("Peça moguda");
    }

    // Método para buscar en una posición específica
    private E buscarEnPosicion(int nuevaFila, int nuevaColumna) {
        NodePieza aux = piezasVivas;
        while (aux != null) {
            if (aux.pieza.getFila() == nuevaFila && aux.pieza.getColumna() == nuevaColumna)
                return aux.pieza;
            aux = aux.seguent;
        }
        return null;
    }

    // Método para buscar y eliminar una pieza en una posición específica
    public boolean eliminarPiezaEnPosicion(int columna, int fila) throws FiJocException {
        NodePieza actual = piezasVivas;
        NodePieza anterior = null;

        // Iterar para encontrar la pieza a eliminar.
        while (actual != null) {
            if (actual.pieza.getFila() == fila && actual.pieza.getColumna() == columna) {
                // Eliminar el nodo correspondiente de la lista enlazada.
                if (anterior == null) {
                    // Si el nodo a eliminar es el primero de la lista.
                    piezasVivas = actual.seguent;
                } else {
                    // Si el nodo a eliminar no es el primero.
                    anterior.seguent = actual.seguent;
                }

                if (actual.pieza.fiJoc()) {
                    throw new FiJocException();
                }

                System.out.println("Pieza eliminada.");
                return true;
            }

            anterior = actual;
            actual = actual.seguent;
        }

        return false; // No se encontró la pieza.
    }
    public void imprimirPiezasVivas(){
        NodePieza aux = piezasVivas;
        while (aux != null){
            System.out.println(aux.pieza.toString());
            aux = aux.seguent;
        }
    }

}
