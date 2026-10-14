package tn.esprit.autoloc;

/**
 * Classe d'utilitaires de l'application.
 */
public final class Utils {

    private Utils() {
        // Classe utilitaire : ne doit pas être instanciée
    }

    /**
     * Teste si une chaîne de caractères est non vide.
     * Une chaîne nulle ou ne contenant que des espaces est considérée comme non valide.
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
