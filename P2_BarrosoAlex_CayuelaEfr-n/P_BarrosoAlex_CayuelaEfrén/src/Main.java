import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static String torn;

    public static void main(String[] args) {
        mostrarMenu();
    }
    private static void mostrarMenu() {
        boolean sortir = false;

        while (!sortir) {
            System.out.println("**** Menú Principal ****");
            System.out.println("1. Jugar una partida nova");
            System.out.println("2. Reproduir una partida des d'un fitxer");
            System.out.println("3. Sortir");

            System.out.print("Seleccioneu una opció: ");
            int opcio = scanner.nextInt();
            scanner.nextLine(); // Neteja el buffer del salt de línia

            switch (opcio) {
                case 1:
                    jugarNovaPartida();
                    break;
                case 2:
                    reproduirPartida();
                    break;
                case 3:
                    System.out.println("Sortint del programa...");
                    sortir = true;
                    break;
                default:
                    System.out.println("Opció no vàlida. Si us plau, torneu-ho a intentar.");
            }
        }
    }

    private static void jugarNovaPartida() {
        boolean next = true;
        Jugador<Pieza> blanc = new Jugador<>(iniciarJuegoBlancas());
        Jugador<Pieza> negre = new Jugador<>(iniciarJuegoNegras());
        Torns<String> torns = new Torns<>();

        System.out.println("Iniciant una partida nova...");
        mostrarTauler(blanc,negre);
        System.out.println("Exemple del primer moviment de les blanques: E1 E3");
        do {
            try {

                System.out.println("Introdueix el torn del jugador blanc (minúscula):");
                torn = scanner.nextLine();
                next = tornToPosition(true, blanc, negre);
                torns.afegirTorn(torn);

                mostrarTauler(blanc, negre);
                if(next) {
                    System.out.println("Introdueix el torn del jugador negre (majúscula):");
                    torn = scanner.nextLine();
                    next = tornToPosition( true, negre, blanc);
                    torns.afegirTorn(torn);

                    mostrarTauler(blanc, negre);
                }
            }catch(NoSuchElementException e )
            {
                System.out.println(e.getMessage());
                next = false;
            }
        }while(next);
        mostrarTauler(blanc,negre);

        System.out.println("Partida finalitzada.");
        try {
            LocalDateTime ara = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String timestamp = ara.format(format);
            torns.guardarAFitxer("Files/torns_" + timestamp + ".txt");

            System.out.println("Partida guardada.");
        } catch (IOException e) {
            System.err.println("Error al guardar la partida.");
        }
        System.out.println("Tornant al menú principal...");
    }

    private static void reproduirPartida() {
        boolean next = true;
        Jugador<Pieza> blanc = new Jugador<>(iniciarJuegoBlancas());
        Jugador<Pieza> negre = new Jugador<>(iniciarJuegoNegras());
        System.out.println("Reproduint partida des de l'arxiu (exemple Files/torns.txt): ");
        Torns<String> tornsCarregats = llegirTorns();

        mostrarTauler(blanc,negre);
        do {
            try {
                torn = tornsCarregats.agafarPrimerTorn();
                System.out.println("Torn del jugador blanc (minúscula): "+torn );
                next = tornToPosition(false, blanc, negre);

                mostrarTauler(blanc,negre);

                if(next) {

                    torn = tornsCarregats.agafarPrimerTorn();
                    System.out.println("Torn del jugador negre (majúscula): " + torn);
                    next = tornToPosition(false, negre, blanc);

                    mostrarTauler(blanc, negre);
                }

            }catch(NoSuchElementException e )
            {
                System.out.println(e.getMessage());
                next = false;
            }
        }while(next);
        mostrarTauler(blanc,negre);
        System.out.println("Reproducció finalitzada. Tornant al menú principal...");
    }

    private static Torns<String> llegirTorns() {
        System.out.println("Introdueix el nom del fitxer per reproduir la partida: ");
        String nomFitxer = scanner.nextLine();
        if (nomFitxer.startsWith("Files/") && nomFitxer.endsWith(".txt")) {
            Torns<String> tornsCarregats = new Torns<>();
            try {
                tornsCarregats.carregarDesDeFitxer(nomFitxer);
                return tornsCarregats;
            } catch (IOException e) {
                System.out.println("Fitxer no vàlid. Si us plau, torneu-ho a intentar.");
                return llegirTorns();
            }
        }
        System.err.println("Fitxer no vàlid, exemple Files/torns.txt");
        return llegirTorns();
    }

    private static boolean tornToPosition(boolean novaPartida, Jugador<Pieza> jugActual, Jugador<Pieza> jugAdversario){
        int[] posicions = new int[4];
        //format esperat: E4 F3
        torn = torn.toUpperCase();
        String[] text = torn.split(" ");
        if( novaPartida && (torn.length() != 5 || text.length != 2) )
        {
            System.err.println("Torna-ho a intentar, exemple: E1 E3");
            torn = scanner.nextLine();
            return tornToPosition(novaPartida, jugActual, jugAdversario);
        }
        // columna i fila actuals
        posicions[0] = text[0].charAt(0) - 'A';
        posicions[1] = text[0].charAt(1) - '0';
        // columna i fila noves
        posicions[2] = text[1].charAt(0) - 'A';
        posicions[3] = text[1].charAt(1) - '0';

        try {
            jugActual.moverPieza(posicions[0], posicions[1], posicions[2], posicions[3]);
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            if( novaPartida) {
                System.err.println("Torna-ho a intentar, introdueix el torn del jugador:");
                torn = scanner.nextLine();
                return tornToPosition(novaPartida, jugActual, jugAdversario);
            }
            return false; //fitxer incorrecte
        }
        try {
            jugAdversario.eliminarPiezaEnPosicion(posicions[2], posicions[3]);
        } catch (FiJocException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        return true;

    }

    private static void mostrarTauler(Jugador<Pieza> blanc, Jugador<Pieza> negre) {
        char[][] tauler = new char[8][8];
        for (char[] chars : tauler) Arrays.fill(chars, ' ');

        // peces vives ambdós jugadors
        for (ItipoPieza item : blanc.getPiezasVivas()) {
            tauler[item.getFila()][item.getColumna()] = Character.toLowerCase(item.getTipus());
        }

        for (ItipoPieza item : negre.getPiezasVivas()) {
            tauler[item.getFila()][item.getColumna()] = Character.toUpperCase(item.getTipus());
        }

        System.out.print("  ABCDEFGH\n");
        for (int row = 0; row < tauler.length; row++) {
            System.out.print(row + " ");
            for (int col = 0; col < tauler[row].length; col++) {
                System.out.print(tauler[row][col]);
            }
            System.out.print("\n");
        }
    }

    private static ArrayList<Pieza> iniciarJuegoBlancas() {
        ArrayList<Pieza> piezasBlancas = new ArrayList<>();
        // Piezas Blancas
        piezasBlancas.add(new Pieza(Pieza.TORRE, 0, 0));
        piezasBlancas.add(new Pieza(Pieza.CABALLO, 0, 1));
        piezasBlancas.add(new Pieza(Pieza.ALFIL, 0, 2));
        piezasBlancas.add(new Pieza(Pieza.REINA, 0, 3));
        piezasBlancas.add(new Pieza(Pieza.REY, 0, 4));
        piezasBlancas.add(new Pieza(Pieza.ALFIL, 0, 5));
        piezasBlancas.add(new Pieza(Pieza.CABALLO, 0, 6));
        piezasBlancas.add(new Pieza(Pieza.TORRE, 0, 7));

        // Peones Blancos
        for (int i = 0; i < 8; i++) {
            piezasBlancas.add(new Pieza(Pieza.PEON, 1, i));
        }
        return piezasBlancas;
    }
    private static ArrayList<Pieza> iniciarJuegoNegras() {
        ArrayList<Pieza> piezasNegras = new ArrayList<>();
        // Piezas Negras
        piezasNegras.add(new Pieza(Pieza.TORRE, 7, 0));
        piezasNegras.add(new Pieza(Pieza.CABALLO, 7, 1));
        piezasNegras.add(new Pieza(Pieza.ALFIL, 7, 2));
        piezasNegras.add(new Pieza(Pieza.REINA, 7, 3));
        piezasNegras.add(new Pieza(Pieza.REY, 7, 4));
        piezasNegras.add(new Pieza(Pieza.ALFIL, 7, 5));
        piezasNegras.add(new Pieza(Pieza.CABALLO, 7, 6));
        piezasNegras.add(new Pieza(Pieza.TORRE, 7, 7));

        // Peones Negros
        for (int i = 0; i < 8; i++) {
            piezasNegras.add(new Pieza(Pieza.PEON, 6, i));
        }
        return piezasNegras;
    }
}
