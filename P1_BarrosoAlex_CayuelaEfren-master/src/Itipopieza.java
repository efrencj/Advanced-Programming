public interface Itipopieza {

    public abstract char getTipus();

    public abstract int getFila();

    public abstract int getColumna();

    public abstract void setPosicion(int fila, int columna) throws RuntimeException;
    // si la posició no és correcte cal llançar una excepció

    public abstract boolean fiJoc() ;

}
