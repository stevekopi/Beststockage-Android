package cd.sklservices.com.Beststockage.Outils;

public class Converter {
    public static String getTypeOperation(String type){
        switch (type){
            case "Vente avec bonus":
                return "Vente_avec_bonus";
            case "Vente en détail":
                return "Vente_detail";
            case "Entrée":
                return "Entree";
            default:
                return type;
        }
    }

    public static String setTypeOperation(String type){
        switch (type){
            case "Vente_avec_bonus":
                return "Vente avec bonus";
            case "Vente_detail":
                return "Vente en détail";
            case "Entree":
                return "Entrée";
            default:
                return type;
        }
    }
}
