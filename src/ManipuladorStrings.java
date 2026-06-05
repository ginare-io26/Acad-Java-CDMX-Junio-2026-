public class ManipuladorStrings {

    public static String invertir(String s) {
        // Usar StringBuilder.reverse()
        return new StringBuilder(s).reverse().toString();
    }

    public static boolean esPalindromo(String s) {
        // Limpiar (toLowerCase, eliminar espacios)
        String limpia = s.toLowerCase().replaceAll("\\s+", "");
        // Comparar con su versión invertida
        return limpia.equals(new StringBuilder(limpia).reverse().toString());
    }

    public static int contarVocales(String s) {
        int count = 0;
        String vocales = "aeiouAEIOU";
        // Recorrer cada caracter, verificar si es vocal
        for (int i = 0; i < s.length(); i++) {
            if (vocales.indexOf(s.charAt(i)) != -1) {
                count++;
            }
        }
        return count;
    }

    public static String construirPiramide(int niveles) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= niveles; i++) {
            // Agregar espacios (niveles - i)
            for (int j = 0; j < niveles - i; j++) {
                sb.append(" ");
            }
            // Agregar asteriscos (2*i - 1)
            for (int j = 0; j < (2 * i - 1); j++) {
                sb.append("*");
            }
            // Agregar salto de linea
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("Invertir 'Hola Mundo': "
                + invertir("Hola Mundo"));
        System.out.println("'Anita lava la tina' es palindromo: "
                + esPalindromo("Anita lava la tina"));
        System.out.println("Vocales en 'Murcielago': "
                + contarVocales("Murcielago"));
        System.out.println("Piramide de 5 niveles:");
        System.out.println(construirPiramide(5));
    }
}
