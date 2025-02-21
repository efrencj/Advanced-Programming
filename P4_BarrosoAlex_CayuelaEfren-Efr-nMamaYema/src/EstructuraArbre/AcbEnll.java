package EstructuraArbre;

import java.util.LinkedList;
import java.util.Queue;

public class AcbEnll <E extends Comparable<E>> implements Acb<E>{
    private NodeA arrel;
    private class NodeA implements Cloneable {
        private E info;
        private NodeA esq;
        private NodeA dret;

        public NodeA(NodeA esq, NodeA dret, E info) {
            this.dret = dret;
            this.esq = esq;
            this.info = info;
        }

        public NodeA(E info) {
            this(null, null, info);
        }

        //Clonem arbre per fillEsq i fillDret
        protected NodeA clone() throws CloneNotSupportedException {

            NodeA arbreClonat = new NodeA(this.info);
            if (this.esq != null) {
                arbreClonat.esq = this.esq.clone();
            }
            if (this.dret != null) {
                arbreClonat.dret = this.dret.clone();
            }
            return arbreClonat;
        }
        private void inserirRecursive(E element) throws ArbreException {
            if(arrel==null){
                System.out.println("arrel null en nodeA/Llegamos al final");
                return;
            }
            int comp=element.compareTo(this.info);

            if(comp==0){
                throw new ArbreException("Element ja existeix");
            }
            if(comp<0){
                if(this.esq==null){
                    this.esq=new NodeA(element);
                }else{
                    this.esq.inserirRecursive(element);
                }
            }if(comp>0){
                if(this.dret==null){
                    this.dret=new NodeA(element);
                }else{
                    this.dret.inserirRecursive(element);
                }
            }
        }
        private void esborrarRecursive(E element) throws ArbreException{
            if(arrel==null){
                throw new ArbreException("no trobat");
            }
            int comp=element.compareTo(this.info);
            if (comp==0){
                if(this.esq==null&&this.dret==null){
                    arrel=null;
                }
                else if(this.esq==null){
                    arrel=this.dret;
                }
                else if(this.dret==null){
                    arrel=this.esq;
                }
                else{
                    NodeA min=findMin(this.dret);
                    this.info=min.info;
                    this.dret.esborrarRecursive(min.info);
                }

            }
            if(comp<0){
                if(this.esq==null){
                    throw new ArbreException("Element no trobat");
                }
                this.esq.esborrarRecursive(element);
            }
            if(comp>0){
                if(this.dret==null){
                    throw new ArbreException("Element no trobat");
                }
                this.dret.esborrarRecursive(element);
            }
        }
        private NodeA findMin(NodeA node) {
            while (node.esq != null) {
                node = node.esq;
            }
            return node;
        }
        private boolean membreRecursive(E element){
            if(arrel==null) return false;
            int comp=element.compareTo(this.info);
            if(comp==0)return true;
            if(comp>0){
                if(this.dret==null){
                    return false;
                }
                return this.dret.membreRecursive(element);
            }
            if(comp<0){
                if(this.esq==null){
                    return false;
                }
                return this.esq.membreRecursive(element);
            }
            return false;
        }
        private int cardinalitatRecursive(){
            int count=1;
            if(this.esq!=null){
                count+=this.esq.cardinalitatRecursive();
            }
            if(this.dret!=null){
                count+=this.dret.cardinalitatRecursive();
            }
            return count;
        }
        private void omplirInOrdre(Queue<E> cua){
            //ESQ, ARREL, DRET
            if(this.esq!=null) this.esq.omplirInOrdre(cua);

            cua.add(this.info);

            if(this.dret!=null) this.dret.omplirInOrdre(cua);
        }
        private void omplirReverseInOrdre(Queue<E> cua){
            //DRET, ARREL, ESQ
            if(this.dret!=null)this.dret.omplirReverseInOrdre(cua);
            cua.add(this.info);
            if(this.esq!=null)this.esq.omplirReverseInOrdre(cua);
        }
    }
    public AcbEnll(){
        this.arrel=null;
    }
    public AcbEnll(E element){
        this.arrel=new NodeA(element);
    }

    public void inserir(E element) throws ArbreException{
        if(this.arrel==null){
            System.out.println("arbre buit");
            this.arrel=new NodeA(element);//solo en el caso de que esté vacío se hace directo
        }else{
            this.arrel.inserirRecursive(element);
        }
    }
    // Insereix un element a l’arbre. Si l’element ja
    //existeix, llança una excepció EstructuraArbre.ArbreException.

    public void esborrar(E element) throws ArbreException{
        if(arrel==null){
            throw new ArbreException("Arbre buit");
        }
        arrel.esborrarRecursive(element);
    }
    // Esborra un element de l’arbre. Llança una excepció si
    //l’arbre és buit o si l’element no es troba a l’arbre.
    public boolean membre(E element){
        if(arrel==null){
            return false;
        }
        return arrel.membreRecursive(element);
    }
    // true si l’element està a l’arbre, fals en cas contrari

    public E arrel() throws ArbreException{
        if(arrel==null)throw new ArbreException("La arrel el buida!");
        return arrel.info;
    }
    // Si no és buit, retorna el contingut de l’arrel, en cas
    //contrari llança una excepció EstructuraArbre.ArbreException
    public Acb<E> fillEsquerre() throws CloneNotSupportedException {
        if(this.arrel==null||this.arrel.esq==null){
            System.out.println("arrel o esq NULL");
            return new AcbEnll<>();
        }

        NodeA subArrelEsquerre = (NodeA) this.arrel.esq.clone();
        AcbEnll<E> arbreEsquerre = new AcbEnll<>();
        arbreEsquerre.arrel = subArrelEsquerre;
        return arbreEsquerre;
    }

    public Acb<E> fillDret() throws CloneNotSupportedException {
        if(this.arrel==null||this.arrel.dret== null){
            System.out.println("arrel o dret NULL");
            return new AcbEnll<>();
        }
        NodeA subArrelDret = (NodeA) this.arrel.dret.clone();
        AcbEnll<E> arbreDret = new AcbEnll<>();
        arbreDret.arrel = subArrelDret;
        return arbreDret;
    }
    // retorna una còpia del subarbre dret.
    // en cas que l’arbre estigui buit o si no té fill dret
    //retorna un arbre buit.

    public boolean arbreBuit(){
        return (this.arrel==null);
    }
    public void buidar(){
        this.arrel=null;
    }

    public int cardinalitat(){
        //ez solo contar nodos
        if(arrel==null){
            return 0;
        }
        return arrel.cardinalitatRecursive();
    }
    public Queue <E> getAscendentList(){
        Queue<E> cua=new LinkedList<>();
        if(arrel!=null){
            arrel.omplirInOrdre(cua);
        }
        return cua;
    }
    public Queue <E> getDescendentList(){
        Queue<E> cua=new LinkedList<>();
        if(arrel!=null){
            arrel.omplirReverseInOrdre(cua);
        }
        return cua;
    }
}
