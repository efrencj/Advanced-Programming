import java.util.ArrayList;

public class Students {
    private Node first;

    private class Node {
        Node next;
        BinaryTree info;

        Node(BinaryTree info) {
            this.info = info;
            this.next = null;
        }
    }

    public Students() {
        this.first = null;
    }

    public void addStudent(BinaryTree nouEstudiant){
        Node aux = new Node(nouEstudiant);
        if (first == null || first.info.getName().compareTo(nouEstudiant.getName()) > 0) {//compara alfabeticament
            aux.next = first;
            first = aux;
        } else {
            Node current = first;
            while (current.next != null && current.next.info.getName().compareTo(nouEstudiant.getName()) < 0) {
                current = current.next;
            }
            aux.next = current.next;
            current.next = aux;
        }
    }

    public void removeStudent(String name){
        if (first == null) return;

        if (first.info.getName().equals(name)) {
            first = first.next;  // Elimina el primer si coincideix --> cas fàcil
        } else {
            Node actual = first;
            while (actual.next != null && !actual.next.info.getName().equals(name)) {
                actual = actual.next;
            }
            if (actual.next != null) {
                actual.next = actual.next.next;  // Elimina el node
            }
        }
    }

    public BinaryTree getStudent(String name){
        Node current = first;
        while (current != null) {
            if (current.info.getName().equals(name)) {
                return current.info;
            }
            current = current.next;
        }
        return null;//si no es troba
    }

    public ArrayList<String> getAllStudentsName(){
        ArrayList<String> names = new ArrayList<>();
        Node current = first;
        while (current != null) {
            names.add(current.info.getName());
            current = current.next;
        }
        return names.isEmpty() ? null : names;
    }
}