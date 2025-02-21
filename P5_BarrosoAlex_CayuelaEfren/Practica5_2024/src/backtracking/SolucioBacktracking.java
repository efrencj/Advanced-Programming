package backtracking;
import estructura.Encreuades;
import estructura.PosicioInicial;

public class SolucioBacktracking {

		private boolean [] marcatge;
		private char [][] millorSol;
		private int millorPunt;
		private  char[][] solucioActual;

	/* TODO
	 * cal definir els atributs necessaris
	 */
	private final Encreuades repte;


	public SolucioBacktracking(Encreuades repte) {
		this.repte = repte;
	}

	public char[][] getMillorSolucio() {
		return this.millorSol; //TODO
	}

	public Runnable start(boolean optim){
		marcatge= new boolean[repte.getItemsSize()];
		millorSol = null;
		millorPunt = -1;
		solucioActual = new char[repte.getPuzzle().length][];
		for (int i = 0; i < repte.getPuzzle().length; i++) {
			solucioActual[i] = repte.getPuzzle()[i].clone();
		}

		/* TODO
		 * cal inicialitzar els atributs necessaris
		 */

		if(!optim) {
			if (!this.backUnaSolucio(0))
				throw new RuntimeException("solució no trobada");
			guardarMillorSolucio();
		}else
			this.backMillorSolucio(0);
		return null;
	}

	private boolean backUnaSolucio(int indexUbicacio) {
		boolean trobada = false;
		// iterem sobre els possibles elements
		for(int indexItem = 0; indexItem < this.repte.getItemsSize() && !trobada; indexItem++) {
			//mirem si l'element es pot posar a la ubicació actual
			if(acceptable( indexUbicacio, indexItem)) {
				//posem l'element a la solució actual
				anotarASolucio(indexUbicacio, indexItem);

				if(esSolucio(indexUbicacio)) { // és solució si totes les ubicacions estan plenes
					return true;
				} else
					trobada = this.backUnaSolucio(indexUbicacio+1); //inserim la següent paraula
				if(!trobada)
					// esborrem la paraula actual, per després posar-la a una altra ubicació
					desanotarDeSolucio(indexUbicacio, indexItem);
			}
		}
		return trobada;
	}

	private void backMillorSolucio(int indexUbicacio) {
		for(int indexItem = 0; indexItem < this.repte.getItemsSize(); indexItem++) {
			//mirem si l'element es pot posar a la ubicació actual
			if(acceptable( indexUbicacio, indexItem)) {
				//posem l'element a la solució actual
				anotarASolucio(indexUbicacio, indexItem);

				if(esSolucio(indexUbicacio) && calcularFuncioObjectiu(this.solucioActual) > this.millorPunt) { // és solució si totes les ubicacions estan plenes
					millorPunt = calcularFuncioObjectiu(this.solucioActual);
					guardarMillorSolucio();
				} else this.backMillorSolucio(indexUbicacio+1); //inserim la següent paraula

				// esborrem la paraula actual, per després posar-la a una altra ubicació
				desanotarDeSolucio(indexUbicacio, indexItem);
			}
		}
	}

	private boolean acceptable(int indexUbicacio, int indexItem) {

		PosicioInicial pos = repte.getEspaisDisponibles().get(indexUbicacio);
		char[] item = repte.getItem(indexItem);
		int fil = pos.getInitRow();
		int col = pos.getInitCol();

		if (marcatge[indexItem] || pos.getLength() != item.length) return false; // no es acceptable si ja l'hem usat o té un tamany diferent

		for (int i = 0; i < item.length; i++) {
			if (pos.getDireccio() == 'H') {
				//supera els limits horitzontals
				if (col + i >= solucioActual[0].length ||
						(solucioActual[fil][col + i] != ' ' && solucioActual[fil][col + i] != item[i])) {
					return false;
				}
			} else { // Dirección vertical
				if (fil + i >= solucioActual.length ||
						(solucioActual[fil + i][col] != ' ' && solucioActual[fil + i][col] != item[i])) {
					return false;
				}
			}
		}
		return true;
	}

	private void anotarASolucio(int indexUbicacio, int indexItem) {
		PosicioInicial pos = repte.getEspaisDisponibles().get(indexUbicacio);
		char[] item = repte.getItem(indexItem);
		int fil = pos.getInitRow();
		int col = pos.getInitCol();

		for (int i = 0; i < item.length; i++) {
			if (pos.getDireccio() == 'H') {
				solucioActual[fil][col + i] = item[i];
			} else {
				solucioActual[fil + i][col] = item[i];
			}
		}
		marcatge[indexItem] = true; // ho marca usat
	}

	private void desanotarDeSolucio(int indexUbicacio, int indexItem) {
		PosicioInicial pos = repte.getEspaisDisponibles().get(indexUbicacio);
		char[] item = repte.getItem(indexItem);
		int fil = pos.getInitRow();
		int col = pos.getInitCol();

		if (pos.getDireccio() == 'H') {
			for (int i = 0; i < pos.getLength(); i++) {
				if (potElimiar(fil, col+i, pos.getDireccio())) {
					solucioActual[fil][col + i] = ' ';
				}
			}
		} else {
			for (int i = 0; i < pos.getLength(); i++) {
				if (potElimiar(fil + i, col, pos.getDireccio())) {
					solucioActual[fil + i][col] = ' ';
				}
			}
		}
		marcatge[indexItem] = false; // ho marca com a no usat
	}

	private boolean potElimiar(int fil, int col, char dir) {
		if (dir == 'H') {
            return (fil - 1 > 0 && (this.solucioActual[fil - 1][col] == '▪' || this.solucioActual[fil - 1][col] == ' ')) ||
                    (fil + 1 < this.solucioActual.length && (this.solucioActual[fil + 1][col] == '▪' || this.solucioActual[fil + 1][col] == ' '));
		} else {
            return (col - 1 > 0 && (this.solucioActual[fil][col - 1] == '▪' || this.solucioActual[fil][col - 1] == ' ')) ||
                    (col + 1 < this.solucioActual[0].length && (this.solucioActual[fil][col + 1] == '▪' || this.solucioActual[fil][col + 1] == ' '));
		}
    }

	private boolean esSolucio(int index) {
		return (index + 1 == this.repte.getEspaisDisponibles().size());
	}

	private int calcularFuncioObjectiu(char[][] matriu) {
		int puntuacio = 0;
		for (int i = 0; i < matriu.length; i++) {
			for (int j = 0; j < matriu[i].length; j++) {
				char c = matriu[i][j];
				// Suma el valor ASCII del caràcter
				if (c != ' ' && c != '▪') {
					puntuacio += (int) c; // ASCII del carácter
				}
			}
		}
		return puntuacio;
	}

	private void guardarMillorSolucio() {
		millorSol = new char[solucioActual.length][];
		for (int i = 0; i < solucioActual.length; i++) {
			millorSol[i] = solucioActual[i].clone();
		}
	}

	public String toString() {
		StringBuilder resultat = new StringBuilder();
		for (char[] fila : solucioActual) {
			for (char c : fila) {
				resultat.append(c == ' ' ? '.' : c).append(' ');
			}
			resultat.append('\n');
		}
		return resultat.toString();
	}

}
