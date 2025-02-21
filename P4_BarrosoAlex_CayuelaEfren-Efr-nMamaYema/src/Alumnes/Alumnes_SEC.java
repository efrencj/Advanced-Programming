package Alumnes;

public class Alumnes_SEC implements Comparable<Alumnes_SEC> {
    private class Node{
        private Assignatura info;
        private Node next;
        public Node(Assignatura info){
            this.info = info;
            this.next = null;
        }
        public Node(String nom){
            this.info = new Assignatura(nom);
            this.next = null;
        }
    }

    private static Node cap;

    public Alumnes_SEC(String nom){
        this.cap= new Node(nom);
    }



    @Override
    public String toString(){
        return cap.info.toString();
    }
    public static boolean hiHa(int punts){
        Node aux=cap.next;
        while(aux.next!=null){
            if(aux.info.getPunts()==punts){
                return true;
            }
            aux=aux.next;
        }
        return false;
    }
    @Override
    public int compareTo(Alumnes_SEC other){
        if(cap.info.getNota()>other.cap.info.getNota()){
            return 1;
        }else if(cap.info.getNota()<other.cap.info.getNota()){
            return -1;
        }else{
            return 0;
        }
    }
    private void calcularNotaMitjana(){
        Node aux=cap.next;
        int puntsmax=0;
        int creditsmax=0;
        //recorremos sumando puntos y creditos
        while(aux.next!=null){
            int punts=aux.info.getPunts();
            int credits=aux.info.getCredits();
            puntsmax+=punts;
            creditsmax+=credits;
            aux=aux.next;
        }
        if (creditsmax>0) cap.info.setNota((double)puntsmax/creditsmax);
        else cap.info.setNota(0);
    }
    public void addAssignatura(Assignatura nova){
        Node aux=cap;
        boolean trobat=false;

        while(aux.next!=null&&!trobat){
            if(aux.info.equals(nova)){
                trobat=true;
            }
            aux=aux.next;
        }
        if(!trobat) {
            aux.next = new Node(nova);
        }
        calcularNotaMitjana();
    }
}
