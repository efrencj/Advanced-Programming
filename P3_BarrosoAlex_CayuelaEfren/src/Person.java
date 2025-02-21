public class Person {
    public static final int WIDOWED=1;
    public static final int DIVORCED=2;
    public static final int MARRIED=3;
    public static final int SINGLE=4;
    private int maritalStatus;
    private String placeOfOrigin;
    private String name;

    public Person(String name, String placeOfOrigin, int maritalStatus){
        if(name==null || placeOfOrigin==null || maritalStatus<1 || maritalStatus>4){
            throw new IllegalArgumentException("Valor incorrecte");
        }
        this.name=name;
        this.placeOfOrigin=placeOfOrigin;
        this.maritalStatus=maritalStatus;
    }

    public Person(String data) {

        if (data.endsWith(";")) {
            data = data.substring(0, data.length() - 1).trim();
        }

        try {
            String[] parts = data.split(", ");
            this.name = parts[0].split(": ")[1].trim();
            this.placeOfOrigin = parts[1].split(": ")[1].trim();

            String status = parts[2].split(": ")[1].trim();
            switch (status) {
                case "Single":
                    this.maritalStatus = SINGLE;
                    break;
                case "Married":
                    this.maritalStatus = MARRIED;
                    break;
                case "Divorced":
                    this.maritalStatus = DIVORCED;
                    break;
                case "Widowed":
                    this.maritalStatus = WIDOWED;
                    break;
                default:
                    throw new IllegalArgumentException("Estado civil no válido: " + status);
            }
        } catch (Exception e) {
            //System.out.println("Error al procesar los datos de Person: " + data + e.getMessage());
            System.out.print("");
        }
    }

    public String getName(){
        return name;
    }

    public int getMaritalStatus(){
        return maritalStatus;
    }

    public String getMaritalStatusString(){
        switch (this.maritalStatus){
            case MARRIED:return("casat/da");
            case SINGLE:return("solter/a");
            case DIVORCED:return("divorciat/da");
            case WIDOWED:return("viut/da");
            default:return null;
        }
    }

    public String getPlaceOfOrigin(){
        return placeOfOrigin;
    }
    public String toString(){
        return "Name: "+name+", place of Origin: "+placeOfOrigin+", marital status: "+getMaritalStatus();
    }

}
