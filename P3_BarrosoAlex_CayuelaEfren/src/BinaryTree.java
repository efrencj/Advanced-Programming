import java.io.*;

public class BinaryTree {
    private class NodeA {
        Person persona;
        NodeA ESQ;
        NodeA DRET;

        public NodeA(Person persona) {
            this.persona = persona;
            ESQ = null;
            DRET = null;
        }
        public NodeA(Person persona, NodeA ESQ, NodeA DRET) {
            this.persona = persona;
            this.ESQ = ESQ;
            this.DRET = DRET;
        }
        private boolean addNodeRecursive(Person unaPersona, String level) {
            if (level.isEmpty()) {
                return false;
            }
            char direction = level.charAt(0);

            if (direction == 'L') {
                if (ESQ == null) {
                    ESQ = new NodeA(unaPersona);
                    //System.out.println("Añadido a la izquierda: " + unaPersona.getName());
                    return true;
                } else {
                    return ESQ.addNodeRecursive(unaPersona, level.substring(1));
                }
            } else if (direction == 'R') {
                if (DRET == null) {
                    DRET = new NodeA(unaPersona);
                    //System.out.println("Añadido a la derecha: " + unaPersona.getName());
                    return true;
                } else {
                    return DRET.addNodeRecursive(unaPersona, level.substring(1));
                }
            }
            return false;
        }


        private void DisplayTreeRecursive(int level) {
            for (int i = 0; i < level; i++) {
                System.out.print("\t");
            }
            System.out.println(this.persona.getName());
            if (ESQ != null) {
                ESQ.DisplayTreeRecursive(level + 1);
            }
            if (DRET != null) {
                DRET.DisplayTreeRecursive(level + 1);
            }

        }

        private void preorderSaveRecursive(BufferedWriter bw) throws IOException {
            if (this == null) {
                bw.write("null; ");
                return;
            }
            bw.write(this.persona.toString() + ";");
            if (ESQ != null) {
                ESQ.preorderSaveRecursive(bw);
            } else {
                bw.write("null");
            }
            if (DRET != null) {
                DRET.preorderSaveRecursive(bw);
            } else {
                bw.write("null");
            }

        }

        private boolean removePersonRecursive(String name) {
            if (arrel == null) {
                return false;
            }

            if (this.ESQ.persona.getName().equalsIgnoreCase(name)) {
                if (this.ESQ.ESQ == null && this.ESQ.DRET == null) {
                    this.ESQ = null;
                } else {
                    this.ESQ.persona = null;
                }
                return true;
            }
            if (this.DRET.persona.getName().equalsIgnoreCase(name)) {
                if (this.DRET.ESQ == null && this.DRET.DRET == null) {
                    this.DRET = null;
                } else {
                    this.DRET.persona = null;
                }
                return true;
            }
            if (this.ESQ != null) {
                this.ESQ.removePersonRecursive(name);
            }
            if (this.DRET != null) {
                this.DRET.removePersonRecursive(name);
            }
            return false;
        }

        private boolean isDescentFromRecursive (String place){
            if(this.persona.getPlaceOfOrigin().equalsIgnoreCase(place)){
                return true;
            }
            if(ESQ!=null){
                ESQ.isDescentFromRecursive(place);
            }if(DRET!=null){
                DRET.isDescentFromRecursive(place);
            }
            return false;
        }

        private int countnodeRecursive(){
            int count = 0;
            //if(this==null)return 0;
            if(this.ESQ!=null){
                if(this.ESQ.ESQ.persona != null){
                    count++;
                }
                if(this.ESQ.DRET.persona!=null){
                    count++;
                }
            }
            count += this.ESQ.countnodeRecursive();

            if (this.DRET!=null){
                if(this.DRET.DRET.persona!= null){
                    count++;
                }
                if(this.DRET.DRET.persona!=null){
                    count++;
                }
            }
            count += this.DRET.countnodeRecursive();
            return count;
        }
    }

    private NodeA arrel;

    public BinaryTree() {
        this.arrel = null;
    }

    public BinaryTree(String FileName) {
        try {
            BufferedReader bur = new BufferedReader(new FileReader(FileName));
            this.arrel = preOrderLoad(bur);
            bur.close();
        } catch (IOException e) {
            System.out.println("Error al obrir el fitxer");
        }
    }

    private NodeA preOrderLoad(BufferedReader bur) throws IOException {
        String linea = bur.readLine();
        if (linea == null || !linea.equals("null;")) {
            return null;
        }
        Person p = new Person(linea);
        NodeA node = new NodeA(p);
        node.ESQ = preOrderLoad(bur);
        node.DRET = preOrderLoad(bur);
        return node;
    }

    public String getName() {
        if (arrel != null) {
            return arrel.persona.getName();
        }
        return null;
    }

    public boolean addNode(Person unaPersona, String Level) {
        if(arrel==null){
            arrel = new NodeA(unaPersona);
            return true;
        }else {
            return this.arrel.addNodeRecursive(unaPersona, Level);
        }

        //System.err.println("Peta addnode");
        //return false;
    }


    public void DisplayTree() {
        if(arrel==null){
            System.err.println("Arbre buit");
            return;
        }
        this.arrel.DisplayTreeRecursive(0);
    }

    public void preorderSave() throws IOException {
        if (this.arrel == null) {
            throw new IOException("Arbre buit o sense nom en arrel");
        }
        //fileWriter crea el nom del fitxer
        BufferedWriter bw = new BufferedWriter(new FileWriter(arrel.persona.getName() + ".txt"));
        arrel.preorderSaveRecursive(bw);
        bw.close();
    }


    public boolean removePerson(String name) {
        if (arrel.persona.getName().equalsIgnoreCase(name) || arrel == null) {
            System.err.println("El nom no pot ser l'Arrel/Arrel BUIDA");
            return false;
        }
        if (arrel.ESQ != null) if(arrel.ESQ.removePersonRecursive(name))return true;
        if (arrel.DRET != null) if(arrel.DRET.removePersonRecursive(name)) return true;
        return false;
    }

    public boolean isFrom(String place) {
        return arrel == null || arrel.persona.getPlaceOfOrigin().equals(place);
    }

    public boolean isDescentFrom(String place) {
        if (arrel == null) {
            System.err.println("Arrel BUIDA");
            return false;
        }
        return arrel.isDescentFromRecursive(place);
    }

    public int howManyParents() {
    if(arrel==null){
        return 0;
    }
    int count = 0;
    if(arrel.ESQ!=null){ count++;}
    if(arrel.DRET!=null){ count++;}
    return count;
    }
    public int howManyGrandparents() {
        if(arrel==null){
            return 0;
        }
        return arrel.countnodeRecursive();
    }

    public boolean marriedParents() {
        if(arrel.ESQ!=null&&arrel.DRET!=null)
        return arrel.ESQ.persona.getMaritalStatus()==Person.MARRIED&&arrel.DRET.persona.getMaritalStatus()==Person.MARRIED;
        return false;
    }
}
