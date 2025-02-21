import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.LoggingPermission;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean finish = false;
        while(!finish) {
            displayMenu();
            int option = scanner.nextInt();

            scanner.nextLine();
            switch (option) {
                case 1:
                    displayAllStudentsNames(readAllStudents("Files"));
                    break;
                case 2:
                    showStudentFamily(readAllStudents("Files"));
                    break;
                case 3:
                    addNewStudent(readAllStudents("Files"));
                    break;
                case 4:
                    modifyStudent(readAllStudents("Files"));
                    break;
                case 5:
                    mostrarInforme(readAllStudents("Files"));
                    break;
                case 6:
                    saveAllStudents(readAllStudents("Files"));
                    finish = true;
                    break;
                default:
                    System.out.println("Opción no válida. Has d'escriure un número entre 1 i 6.");
                    break;
            }
        }
    scanner.close();
    }
    private static void displayMenu() {
        System.out.println("1. Mostrar nombres de los estudiantes");
        System.out.println("2. Mostrar familia de un estudiante");
        System.out.println("3. Añadir un nuevo estudiante");
        System.out.println("4. Modificar un estudiante");
        System.out.println("5. Mostrar informe");
        System.out.println("6. Salir");
    }

    public static Students readAllStudents(String folderPath) {
        Students studentsList = new Students();
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(".txt"));

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                try (Scanner fileScanner = new Scanner(file)) {
                    BinaryTree studentTree = new BinaryTree();

                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine().trim();
                        int levelCount = line.length() - line.replace(";", "").length();
                        String personData = line.replace(";", "").trim();
                        Person person = new Person(personData);

                        // Construir la cadena
                        StringBuilder level = new StringBuilder();
                        for (int i = 0; i < levelCount; i++) {
                            level.append("L");
                        }

                        // ficar a l'arbre els nodes
                        studentTree.addNode(person, level.toString());
                    }

                    studentsList.addStudent(studentTree);  // afegir l'arbre a la llista
                } catch (Exception e) {
                    System.out.println("Error al leer el archivo " + file.getName());
                }
            }
        } else {
            System.out.println("No hay archivos en la carpeta " + folderPath);
        }

        return studentsList;
    }

    public static void saveAllStudents(Students studentsList) {
            // Guarda todos los estudiantes en archivos
        ArrayList<String>studentsNames=studentsList.getAllStudentsName();
        if(studentsNames!=null){
            for(String name:studentsNames){
                BinaryTree studentTree=studentsList.getStudent(name);
                try {
                    studentTree.preorderSave();
                } catch (Exception e) {
                    System.out.println("Error al guardar el archivo "+name);
                }
            }
        }

    }

    public static void displayAllStudentsNames(Students studentsList) {
        ArrayList<String> names = studentsList.getAllStudentsName();
        if (names == null || names.isEmpty()) {
            System.out.println("No hay estudiantes");
        } else {
            for (String name : names) {
                System.out.println(name);
            }
        }
    }

    public static void showStudentFamily(Students studentsList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Quin és el nom del estudiant que vols buscar?");
        String name = scanner.nextLine();
        BinaryTree binaryTree = studentsList.getStudent(name);
        if (binaryTree == null) {
            System.out.println("L'estudiant no es troba en aquesta llista");
        }else{
            binaryTree.DisplayTree();
        }
    }

    public static void addNewStudent(Students studentsList) {
        Scanner scanner = new Scanner(System.in);
        // Añade un nuevo estudiante a la lista
        System.out.println("Esriu el nom de l'estudiant:");
        String name = scanner.nextLine();
        System.out.println("Lloc d'origen:");
        String place = scanner.nextLine();
        System.out.println("Ingrese el estado civil.");
        System.out.println("Escriu un número entre 0 i 3.\n1: casat/da, 2: divorciat/da, 3: solter/a, 4: vidu/a");
        int maritalStatus = scanner.nextInt();
        Person newStudent = new Person(name, place, maritalStatus);
        BinaryTree newStudentTree = new BinaryTree();
        newStudentTree.addNode(newStudent, "");
        studentsList.addStudent(newStudentTree);//AQUI FALLA
        System.out.println("Estudiant afegit correctament");
    }

    public static void modifyStudent(Students studentsList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Com es diu l'estudiant que vols modificar?");
        String name = scanner.nextLine();
        BinaryTree binaryTree = studentsList.getStudent(name);
        if (binaryTree == null) {
            System.out.println("L'estudiant no es troba en aquesta llista");
            return;
        }

        System.out.println("Escull una opció (introdueix el número)");
        System.out.println("1. Nou membre de la familia.");
        System.out.println("2. Eliminar membre de la familia");
        System.out.println("3. Cancelar");

        int option = scanner.nextInt();
        scanner.nextLine();

        while (option > 3 || option < 1) {
            System.out.println("Opció no válida. Has d'escriure un número vàlid.");
            option = scanner.nextInt();
            scanner.nextLine();
        }

        switch (option) {
            case 1:
                System.out.println("Com es diu el familiar?");
                String namefamiliar = scanner.nextLine();
                System.out.println("On va néixer?");
                String place = scanner.nextLine();
                System.out.println("Quin és el seu estat civil?");
                System.out.println("Opcions (ingresi número): casat->1, divorciat->2, solter->3, viut->4");
                int status = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Quin és el seu nivell en l'arbre? ex: LLR");
                String level = scanner.nextLine();
                System.out.println("Creant familiar...");
                Person familiar = new Person(namefamiliar, place, status);
                binaryTree.addNode(familiar, level);
                System.out.println("Creat correctament!");
                return;

            case 2:
                System.out.print("Com es deia el familiar?");
                String namefamiliar2 = scanner.nextLine();
                System.out.println("Eliminant familiar...");
                if(binaryTree.removePerson(namefamiliar2))
                System.out.println("Familiar eliminat correctament.");
                System.out.println("No s'ha trobat.");
                return;

            case 3:
                System.out.print("Cancelant...");
                return;

            default:
                // No tendría que llegar aquí
                return;
        }
    }


    private static void mostrarInforme(Students studentsList) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Indica la ciutat de naixement a buscar: ");
        String birthPlace = scanner.nextLine();
        System.out.print("Indica la ciutat de procedència a buscar: ");
        String descentPlace = scanner.nextLine();

        int totalStudents = 0;
        int bornInCity = 0, descentFromCity = 0, singleParents = 0, divorcedParents = 0, moreThanTwoGrandparents = 0;

        for (String studentName : studentsList.getAllStudentsName()) {
            BinaryTree studentTree = studentsList.getStudent(studentName);
            totalStudents++;

            if (studentTree.isFrom(birthPlace)) bornInCity++;
            if (studentTree.isDescentFrom(descentPlace)) descentFromCity++;
            if (studentTree.howManyParents() == 1) singleParents++;
            if (!studentTree.marriedParents()) divorcedParents++;
            int grandparents = studentTree.howManyGrandparents();
            if (grandparents >= 2) moreThanTwoGrandparents++;
        }

        System.out.println("Nombre d'alumnes totals: " + totalStudents);
        System.out.println("Hi ha " + bornInCity + " alumnes de " + birthPlace);
        System.out.println("Hi ha " + descentFromCity + " alumnes descendents de " + descentPlace);
        System.out.println("Hi ha " + singleParents + " alumnes amb un únic progenitor.");
        System.out.println("Hi ha " + divorcedParents + " alumnes amb progenitors no casats.");
        System.out.println("Hi ha " + moreThanTwoGrandparents + " alumnes amb dos o més avis o àvies.");
    }
}
