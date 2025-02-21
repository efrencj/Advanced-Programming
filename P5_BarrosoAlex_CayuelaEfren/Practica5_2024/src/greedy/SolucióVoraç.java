package greedy;

import estructura.Encreuades;
import estructura.PosicioInicial;

import java.util.Arrays;

public class SolucióVoraç {
    private final Encreuades repte;
    private final boolean[] marcatge;
    private final char[][] solucio;

    public SolucióVoraç(Encreuades repte) {
        this.repte = repte;
        this.marcatge = new boolean[repte.getItemsSize()];
        this.solucio = new char[repte.getPuzzle().length][repte.getPuzzle()[0].length];

        // Inicializamos la solución con el puzzle base
        for (int i = 0; i < repte.getPuzzle().length; i++) {
            solucio[i] = repte.getPuzzle()[i].clone();
        }

        // Ejecutamos el algoritmo voraz
        greedy();
    }

    public char[][] getSolucio() {
        return this.solucio;
    }

    private char[][] greedy() {
        int paraulesAnotades = 0;
        for (PosicioInicial pos : repte.getEspaisDisponibles()) {
            // Intentamos encontrar la mejor palabra para la posición actual
            for (int i = 0; i < repte.getItemsSize(); i++) {
                if (!marcatge[i] && esAcceptable(pos, repte.getItem(i))) {
                    anotarASolucio(this.repte.getEspaisDisponibles().get(i), this.repte.getItem(i));
                    paraulesAnotades++;
                    if (this.repte.getEspaisDisponibles().size() == paraulesAnotades) return this.solucio;
                }
            }
        }
        return this.solucio;
    }

    private boolean esAcceptable(PosicioInicial pos, char[] item) {
        if (pos.getLength() != item.length) return false;

        int fila = pos.getInitRow();
        int columna = pos.getInitCol();

        for (int i = 0; i < item.length; i++) {
            if (pos.getDireccio() == 'H') {
                if (columna + i >= solucio[0].length ||
                        (solucio[fila][columna + i] != ' ' && solucio[fila][columna + i] != item[i])) {
                    return false;
                }
            } else { // Dirección vertical
                if (fila + i >= solucio.length ||
                        (solucio[fila + i][columna] != ' ' && solucio[fila + i][columna] != item[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    private void anotarASolucio(PosicioInicial pos, char[] item) {
        int fila = pos.getInitRow();
        int columna = pos.getInitCol();

        for (int i = 0; i < item.length; i++) {
            if (pos.getDireccio() == 'H') {
                solucio[fila][columna + i] = item[i];
            } else {
                solucio[fila + i][columna] = item[i];
            }
        }
    }
}
