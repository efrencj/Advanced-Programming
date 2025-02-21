public class Pieza implements Itipopieza{
    private int fil;
    private char col;
    private final char tipus;
    public static final char REI = 'R', REINA = 'Q', TORRE = 'T', CABALLO = 'C', ALFIL = 'A', PEON = 'P';
    public Pieza(int fil, char col, char tipus) {
        checkTipo(tipus);
        this.fil = fil;
        this.col = col;
        this.tipus = tipus;
    }

    private void checkTipo(char tipus) {
        if (tipus != REI && tipus != REINA && tipus != TORRE && tipus != CABALLO && tipus != ALFIL && tipus != PEON) {
            throw new IllegalArgumentException("Peça no vàlida, tipus incorrecte.");
        }
    }

    public char getTipus(){
        return tipus;
    }
    public void setFil(int fil){
        this.fil = fil;
    }
    public int getFila(){
        return fil;
    }
    public void setCol(int col){
        switch (col){
            case 1: this.col='a'; break;
            case 2: this.col='b'; break;
            case 3: this.col='c'; break;
            case 4: this.col='d'; break;
            case 5: this.col='e'; break;
            case 6: this.col='f'; break;
            case 7: this.col='g'; break;
            case 8: this.col='h'; break;
        }
    }
    public int getColumna(){
        switch (this.col){
            case 'a': return 1;
            case 'b': return 2;
            case 'c': return 3;
            case 'd': return 4;
            case 'e': return 5;
            case 'f': return 6;
            case 'g': return 7;
            case 'h': return 8;
            default: return 0;
        }
    }
    public void setPosicion(int fil, int col) throws RuntimeException{
        if (fil < 1 || fil > 8 || col < 1 || col > 8) {
            throw new RuntimeException("Posició fora dels límits.");
        }
        setFil(fil);
        setCol(col);
    }
    // si la posició no és correcte cal llançar una excepció
    public boolean fiJoc(){
        return tipus == REI;
    }

    public String toString() {
        return (this.tipus + " " + this.fil + this.col);
    }

    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Pieza) || o == null) return false;
        return this.fil == ((Pieza) o).getFila() && this.col == ((Pieza) o).getColumna() && this.tipus == ((Pieza) o).getTipus();
    }
}