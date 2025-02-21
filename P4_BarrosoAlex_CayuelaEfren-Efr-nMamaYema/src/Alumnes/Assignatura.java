package Alumnes;

public class Assignatura {
    public final String nom;
    public final int credits;
    public double nota;
    public final boolean mHonor;
    public static final int APROBAT = 5;
    public static final int NOTABLE = 7;
    public static final int EXCELLENT = 9;

    public Assignatura(String nom, int credits, double nota, boolean mHonor) throws IllegalArgumentException {
        if(credits<0||nota<0){
            throw new IllegalArgumentException("Els valors de la nota i els credits han de ser posoitius");
        }
        this.nom = nom;
        this.credits = credits;
        this.nota = nota;
        this.mHonor = nota >= EXCELLENT && mHonor;
    }

    public Assignatura(String nom){
        this(nom, 0, 0, false);
    }

    public double getNota(){
        return nota;
    }

    public void setNota(double nota){
        if(nota<0){
            throw new IllegalArgumentException("Nota ha de ser major a 0");
        }
        this.nota = nota;
    }

    public int getPunts() {
        if (nota >= EXCELLENT) {
            return getCredits() * 3;
        } else if (nota >= NOTABLE) {
            return getCredits() * 2;
        } else if (nota >= APROBAT) {
            return getCredits();
        } else {
            return 0;
        }
    }

    public int getCredits() {
        return credits;
    }
    public String toString() {
        return ("Alumne: " + nom + "\n" + "Nota mitjana: " + nota);
    }

    public boolean equals(Object obj){
        if (obj != null) {
            if(obj instanceof Assignatura){
                return ((Assignatura)obj).nom.equals(this.nom);
            }
        }
        return false;
    }

}
