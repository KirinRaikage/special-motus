
public class clientProcessor {
    private String nom;
    private int codeLicence;
    private int nombreMotTrouve = 0;

    public void ajouterMotTrouve() {
        nombreMotTrouve++;
    }

    public int getNombreMotTrouve() {
        return nombreMotTrouve;
    }


    public void setNombreMotTrouve(int nombreMotTrouve) {
        this.nombreMotTrouve = nombreMotTrouve;
    }

    public clientProcessor() {
        super();
    }

    public clientProcessor(String nom, int codeLicence) {
        super();
        this.nom = nom;
        this.codeLicence = codeLicence;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getCodeLicence() {
        return codeLicence;
    }

    public void setCodeLicence(int codeLicence) {
        this.codeLicence = codeLicence;
    }


}
