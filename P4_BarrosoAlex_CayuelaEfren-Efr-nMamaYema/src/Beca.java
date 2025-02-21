import Alumnes.Alumnes_SEC;
import Alumnes.Assignatura;
import EstructuraArbre.AcbEnll;
import EstructuraArbre.ArbreException;

import java.util.Queue;
import java.util.Scanner;

public class Beca {
    private static final Scanner scanner = new Scanner(System.in);
    private AcbEnll<Alumnes_SEC> arbreACB;
    private Queue<Alumnes_SEC> llistaDescendent;

    public Beca() {
        arbreACB = new AcbEnll<>();
        try{
            arbreACB.inserir(exempleEnric());
            arbreACB.inserir(exempleRosa());
            arbreACB.inserir(exempleRandom("Joan"));
            arbreACB.inserir(exempleRandom("Laura"));
            arbreACB.inserir(exempleRandom("Marc"));

        } catch (ArbreException e) {
            System.out.println("Error al inserir alumne");
        }
    }
    private Alumnes_SEC exempleRosa() {
        Alumnes_SEC rosa = new Alumnes_SEC("Rosa");
        rosa.addAssignatura(new Assignatura("Fonaments de la Programació", 6, 7, false));
        rosa.addAssignatura(new Assignatura("Programació Orientada a l'Objecte", 6, 5, false));
        rosa.addAssignatura(new Assignatura("Estructura de Dades i Algorismes", 4, 9, false));
        rosa.addAssignatura(new Assignatura("Programació Avançada", 4, 5, false));
        return rosa;
    }
    private Alumnes_SEC exempleEnric() {
        Alumnes_SEC enric = new Alumnes_SEC("Enric");
        enric.addAssignatura(new Assignatura("Fonaments de la Programació", 6, 8, false));
        enric.addAssignatura(new Assignatura("Programació Orientada a l'Objecte", 6, 6, false));
        enric.addAssignatura(new Assignatura("Estructura de Dades i Algorismes", 4, 9, true)); // Matrícula d'honor
        enric.addAssignatura(new Assignatura("Programació Avançada", 4, 3, false));
        return enric;
    }
    private Alumnes_SEC exempleRandom(String nom) {
        Alumnes_SEC alumne = new Alumnes_SEC(nom);
        alumne.addAssignatura(new Assignatura("Fonaments de la Programació", 6, Math.random() * 5 + 5, false));
        alumne.addAssignatura(new Assignatura("Programació Orientada a l'Objecte", 6, Math.random() * 5 + 5, false));
        alumne.addAssignatura(new Assignatura("Estructura de Dades i Algorismes", 4, Math.random() * 5 + 5, false));
        alumne.addAssignatura(new Assignatura("Programació Avançada", 4, Math.random() * 5 + 5, false));
        return alumne;
    }
    public void eliminarAlumneSenseMatricula(){
        Queue<Alumnes_SEC> cuaAux = arbreACB.getAscendentList();
        while (!cuaAux.isEmpty()){
            Alumnes_SEC alumne = cuaAux.poll();
            if (alumne.hiHa(4)){
                try {
                    arbreACB.esborrar(alumne);
                } catch (ArbreException e) {
                    System.out.println("Error al esborrar alumne" +e.getMessage());
                }
            }
        }
        llistaDescendent = arbreACB.getDescendentList();
    }
    public void afegirAlumne() {
        System.out.print("Nom de l'alumne: ");
        String nom = scanner.nextLine();
        Alumnes_SEC alumne = new Alumnes_SEC(nom);

        boolean afegintAssignatures = true;
        while (afegintAssignatures) {
            // Obté les dades de cada assignatura
            System.out.print("Nom de l'assignatura: ");
            String nomAssignatura = scanner.nextLine();
            System.out.print("Crèdits de l'assignatura: ");
            int credits = scanner.nextInt();
            System.out.print("Nota de l'assignatura: ");
            double nota = scanner.nextDouble();
            System.out.print("Té matrícula d'honor? (true/false): ");
            boolean mHonor = scanner.nextBoolean();
            scanner.nextLine(); // Neteja el buffer

            alumne.addAssignatura(new Assignatura(nomAssignatura, credits, nota, mHonor));

            System.out.print("Vols afegir més assignatures? (true/false): ");
            afegintAssignatures = scanner.nextBoolean();
            scanner.nextLine(); // Neteja el buffer
        }

        try {
            arbreACB.inserir(alumne); // Afegeix l'alumne a l'arbre
            llistaDescendent = arbreACB.getDescendentList(); // Actualitza la llista descendent
        } catch (ArbreException e) {
            System.out.println("Error afegint alumne: " + e.getMessage());
        }
    }

    // Mostra la llista d'alumnes en ordre descendent
    public void mostrarAlumnesDescendent() {
        llistaDescendent = arbreACB.getDescendentList(); // Obté la llista actualitzada
        System.out.println("Llista d'alumnes en ordre descendent:");
        while (!llistaDescendent.isEmpty()) {
            System.out.println(llistaDescendent.poll()); // Mostra i elimina cada alumne de la cua
        }
    }
    public static void main(String[] args) {
        Beca programaBeca = new Beca(); // Inicialitza el programa de beques

        boolean sortir = false;
        while (!sortir) {
            // Mostra el menú principal
            System.out.println("\nMenú:");
            System.out.println("1. Afegir un nou alumne");
            System.out.println("2. Esborrar un alumne a partir del seu nom");
            System.out.println("3. Mostrar tots els alumnes en ordre descendent");
            System.out.println("4. Esborrar alumnes sense matrícula d'honor");
            System.out.println("5. Sortir del programa");
            System.out.print("Tria una opció: ");

            int opcio = scanner.nextInt(); // Llegeix l'opció seleccionada
            scanner.nextLine(); // Neteja el buffer

            switch (opcio) {
                case 1:
                    programaBeca.afegirAlumne(); // Afegeix un nou alumne
                    break;
                case 2:
                    System.out.print("Nom de l'alumne a esborrar: ");
                    String nom = scanner.nextLine();
                    try {
                        programaBeca.arbreACB.esborrar(new Alumnes_SEC(nom)); // Elimina l'alumne
                        programaBeca.llistaDescendent = programaBeca.arbreACB.getDescendentList(); // Actualitza la llista
                    } catch (ArbreException e) {
                        System.out.println("Error esborrant alumne: " + e.getMessage());
                    }
                    break;
                case 3:
                    programaBeca.mostrarAlumnesDescendent(); // Mostra els alumnes
                    break;
                case 4:
                    programaBeca.eliminarAlumneSenseMatricula(); // Elimina els alumnes sense matrícula d'honor
                case 5:
                    sortir = true; // Finalitza el programa
                    System.out.println("Programa finalitzat.");
                    break;
                default:
                    System.out.println("Opció no vàlida. Si us plau, tria una opció del menú.");
            }
        }
    }
}