import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Main {
    public static void main(String[] args) {

    mostrarMenu();
    }
    private static void mostrarMenu() {
        boolean jugant=true;
        Scanner sc = new Scanner(System.in);

        while(jugant) {
            System.out.println("Benvingut als escacs!");
            System.out.println("1. Jugar una partida nova");
            System.out.println("2. Reproduir una partida des d'un fitxer");
            System.out.println("3. Sortir");
            System.out.print("Selecciona una opció: ");
            String input = sc.nextLine();
            try {
                int opcio = Integer.parseInt(input); //nos aseguramos de que el usuario siempre escriba un num
                switch (opcio) {
                    case 1:
                        jugarPartidaNova();
                        break;
                    case 2:
                        reproduirPartida();
                        //case de reproduir desde fitxer
                        break;
                    case 3:
                        jugant = false;
                        System.out.println("Sortint del joc...");
                        break;
                    default:
                        System.out.println("Si pasa esto me meto un tiro.");
                }
            }catch (NumberFormatException e){
                System.out.println("Escriu un numero entre L'1 i el 3.");
            }
        }
        sc.close();
    }
    private static void reproduirPartida() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introdueix el nom del fitxer a reproduir (ej: partida.txt): ");
        String nomFitxer = sc.nextLine();

        Torns<String> torns = llegirTorns(nomFitxer);

        if (torns == null) {
            System.out.println("No s'han pogut carregar els torns.");
            return;
        }


        ArrayList<Pieza> Blanques = iniciarJuegoBlancas(new ArrayList<>());
        ArrayList<Pieza> Negres = iniciarJuegoNegras(new ArrayList<>());
        Jugador<Pieza> JugadorBlanc = new Jugador<>(Blanques);
        Jugador<Pieza> JugadorNegre = new Jugador<>(Negres);

        boolean color = true; // Turno de las blancas

        // Reproducir cada movimENT

            while (torns.getNumTornsRestants() > 0) {
                mostrarTauler(JugadorBlanc, JugadorNegre);

                String moviment = torns.agafarPrimerTorn();
                System.out.println("Reproduint moviment: " + moviment);

                // Realizar el movimiento en el tablero
                try {
                    tornToPosition(moviment, (color ? JugadorBlanc : JugadorNegre), (color ? JugadorNegre : JugadorBlanc));
                } catch (FiJocException e) {
                    System.out.println("Reproducció finalitzada!");
                }

                // Cambiar de turno
                color = !color;
            }


    }

    private static Torns<String> llegirTorns(String nomFitxer) {
        try {

            return new Torns<>(nomFitxer); //IntelIJ dice que mejor asi
        } catch (IOException e) {
            System.out.println("Error en carregar els torns del fitxer: " + e.getMessage());
            return null;
        }
    }
    private static void tornToPosition(String movimentP1, Jugador<Pieza> p1, Jugador<Pieza> p2) throws FiJocException {

        String[] moviments = movimentP1.split(" ");

        if (moviments.length != 2) {
            throw new RuntimeException("Format de moviment incorrecte. Utilitza 'a2 d7'.");

        }

        // Extraer pos inicial y final
        String posInicial = moviments[0];
        String posFinal = moviments[1];
        
        // Convertir pos inicial (ex----> "a2")
        int colInicial = posInicial.charAt(0) - 'a' + 1; // Convertir 'a'-'h' a 1-8
        int filInicial = Character.getNumericValue(posInicial.charAt(1));

        // Convertir pos final (ex. "d7")
        int colFinal = posFinal.charAt(0) - 'a' + 1;
        int filFinal = Character.getNumericValue(posFinal.charAt(1));

        // Mover la pieza
        p1.moverPieza(filInicial, colInicial, filFinal, colFinal);
        if(p2.eliminarPiezaPosicion(filFinal, colFinal)){
            System.out.println("Has matat la peça que está en "+ posFinal);
        }

    }

    private static void jugarPartidaNova(){
        Torns<String> torns=new Torns<>();

        Scanner sc = new Scanner(System.in);
        boolean partida = true;
        boolean color=true; //true blanques false negres
        //TODO: nombre archivo
       //inicialitzem les peces i jugadors
        ArrayList<Pieza>Blanques=new ArrayList<>();
        ArrayList<Pieza>Negres=new ArrayList<>();
        Blanques = iniciarJuegoBlancas(Blanques);
        Negres = iniciarJuegoNegras(Negres);

        Jugador<Pieza>JugadorBlanc=new Jugador<>(Blanques);
        Jugador<Pieza>JugadorNegre=new Jugador<>(Negres);

        System.out.print("Ingresa el nombre del arxiu per guardar els moviments (ex: partida.txt): ");
        String nomFitxer = sc.nextLine();
        nomFitxer=nomFitxer.toLowerCase();
        if(!nomFitxer.endsWith(".txt")){
            System.out.println("El fitxer ha d'acabar en .txt");
            return;
        }

        System.out.println("Peces posicionades correctament! (Blanques abaix)");
        String movimentP1 = null;
        while (partida) {
            try {
                mostrarTauler(JugadorBlanc, JugadorNegre);//mostrar taulell

                System.out.println("Torn de les " + (color?"blanques":"negres"));
                System.out.println("Introdueix el moviment (ex: 'a2 d7'):");
                movimentP1 = sc.nextLine();

                tornToPosition(movimentP1,(color?JugadorBlanc:JugadorNegre), (color?JugadorNegre:JugadorBlanc));

                torns.afegirTorn(movimentP1);

                // todavia no -->System.out.println("peça eliminada!");
                color = !color;
            } catch (FiJocException e) {
                torns.afegirTorn(movimentP1);
                System.out.println("Has matat al rei!!! " + e.getMessage());
                partida = false;
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }

        }
        try {
            torns.guardarFitxer(nomFitxer);  // Guardamos el archivo al final de la partida
            System.out.println("Moviments guardats a " + nomFitxer);
        } catch (IOException e) {
            System.out.println("Error en guardar el fitxer: " + e.getMessage());
        }
    }

    private static ArrayList<Pieza> iniciarJuegoBlancas(ArrayList<Pieza> piezasBlancas){
    piezasBlancas.clear();


        // Fila 1 (torre, caballo, alfil, reina, rey, alfil, caballo, torre)
        piezasBlancas.add(new Pieza(1, 'a', Pieza.TORRE));
        piezasBlancas.add(new Pieza(1, 'b', Pieza.CABALLO));
        piezasBlancas.add(new Pieza(1, 'c', Pieza.ALFIL));
        piezasBlancas.add(new Pieza(1, 'd', Pieza.REINA));
        piezasBlancas.add(new Pieza(1, 'e', Pieza.REI));
        piezasBlancas.add(new Pieza(1, 'f', Pieza.ALFIL));
        piezasBlancas.add(new Pieza(1, 'g', Pieza.CABALLO));
        piezasBlancas.add(new Pieza(1, 'h', Pieza.TORRE));

        // Fila 2 (peones)
        piezasBlancas.add(new Pieza(2, 'a', Pieza.PEON));
        piezasBlancas.add(new Pieza(2, 'b', Pieza.PEON));
        piezasBlancas.add(new Pieza(2, 'c', Pieza.PEON));
        piezasBlancas.add(new Pieza(2, 'd', Pieza.PEON));
        piezasBlancas.add(new Pieza(2, 'e', Pieza.PEON));
        piezasBlancas.add(new Pieza(2, 'f', Pieza.PEON));
        piezasBlancas.add(new Pieza(2, 'g', Pieza.PEON));
        piezasBlancas.add(new Pieza(2, 'h', Pieza.PEON));

        return piezasBlancas;
    }

    private static ArrayList<Pieza> iniciarJuegoNegras(ArrayList<Pieza> piezasNegras){
        piezasNegras.clear();
        // Fila 8 (torre, caballo, alfil, reina, rey, alfil, caballo, torre)
        piezasNegras.add(new Pieza(8, 'a', Pieza.TORRE));
        piezasNegras.add(new Pieza(8, 'b', Pieza.CABALLO));
        piezasNegras.add(new Pieza(8, 'c', Pieza.ALFIL));
        piezasNegras.add(new Pieza(8, 'd', Pieza.REINA));
        piezasNegras.add(new Pieza(8, 'e', Pieza.REI));
        piezasNegras.add(new Pieza(8, 'f', Pieza.ALFIL));
        piezasNegras.add(new Pieza(8, 'g', Pieza.CABALLO));
        piezasNegras.add(new Pieza(8, 'h', Pieza.TORRE));

        // Fila 7 (peones)
        piezasNegras.add(new Pieza(7, 'a', Pieza.PEON));
        piezasNegras.add(new Pieza(7, 'b', Pieza.PEON));
        piezasNegras.add(new Pieza(7, 'c', Pieza.PEON));
        piezasNegras.add(new Pieza(7, 'd', Pieza.PEON));
        piezasNegras.add(new Pieza(7, 'e', Pieza.PEON));
        piezasNegras.add(new Pieza(7, 'f', Pieza.PEON));
        piezasNegras.add(new Pieza(7, 'g', Pieza.PEON));
        piezasNegras.add(new Pieza(7, 'h', Pieza.PEON));

        return piezasNegras;
    }

    private static void mostrarTauler(Jugador<Pieza> JugadorBlanc, Jugador<Pieza> JugadorNegre) {
        // Matriu per mostrar Taulell
        char[][] taulell = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                taulell[i][j] = '.';
            }
        }

        // Aafegim les blanquess
        for (Pieza p : JugadorBlanc.getPiezasVivas()) {
            int fila = p.getFila() - 1;
            int columna = p.getColumna() - 1;
            taulell[fila][columna] = Character.toUpperCase(p.getTipus());//en mayus per distingir de les neges
        }

        // Afegim negres
        for (Pieza p : JugadorNegre.getPiezasVivas()) {
            int fila = p.getFila() - 1;
            int columna = p.getColumna() - 1;
            taulell[fila][columna] = Character.toLowerCase(p.getTipus());//en minus per distingir de les altres
        }

        // fem el print del taulell
        System.out.println("  a b c d e f g h");
        for (int i = 7; i >= 0; i--) {  // Recorremos de 7 a 0 para mostrar de la fila 8 a la 1
            System.out.print((i + 1) + " ");  // Num fila
            for (int j = 0; j < 8; j++) {
                System.out.print(taulell[i][j] + " ");
            }
            System.out.println((i + 1));  // Num fila
        }
        System.out.println("  a b c d e f g h");
    }
}
