package similitudtextos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 *
 * @author ernt
 */
public class SimilitudTextos {

    static ArrayList<String> palabrasTexto1, palabrasTexto2;

    public static void main(String[] args) {
        try {
            File archivo1 = new File("prueba1.txt");
            File archivo2 = new File("prueba2.txt");

            similitudDelCoseno(archivo1, archivo2);

        } catch (Exception e) {
        }
    }

    private static void similitudDelCoseno(File archivo1, File archivo2) throws FileNotFoundException, IOException {
        ArrayList<String> vocabularioTexto;

        vocabularioTexto = getVocabulario(archivo1, archivo2);

        int v1[] = getVectorPeso(vocabularioTexto, palabrasTexto1);
        int v2[] = getVectorPeso(vocabularioTexto, palabrasTexto2);

        double simCos = getSimilitudCoseno(v1,v2);
        System.out.println(simCos);
    }

    public static double getSimilitudCoseno(int v1[], int v2[]) {
        double simCos;
        int sumaNumerador = 0, sumaDenX = 0, sumaDenY = 0;

        for (int i = 0; i < v1.length; i++) {
            sumaNumerador += (v1[i] * v2[i]);
            sumaDenX += Math.pow(v1[i], 2);
            sumaDenY += Math.pow(v2[i], 2);
        }

        simCos = sumaNumerador / (Math.sqrt(sumaDenX * sumaDenY));

        return simCos;
    }

    public static int[] getVectorPeso(ArrayList<String> vocabulario, ArrayList<String> palabrasTexto) {
        int v[] = new int[vocabulario.size()];
        int j = 0;

        Iterator it = vocabulario.iterator();
        while (it.hasNext()) {
            String palabra = it.next().toString();
            int contador = 1;
            //Busca cuántas veces aparece el término en el texto y los guarda
            Iterator i = palabrasTexto.iterator();
            while (i.hasNext()) {
                String palabraVeces = i.next().toString();
                if (palabra.equals(palabraVeces)) {
                    v[j] = contador++;
                }
            }
            j++;
        }

        return v;
    }

    public static ArrayList<String> getVocabulario(File f1, File f2) throws FileNotFoundException, IOException {
        BufferedReader br1 = new BufferedReader(new FileReader(f1));

        String lineaTexto = "", palabra;
        ArrayList<String> vocabulario = new ArrayList<>();
        palabrasTexto1 = new ArrayList<>();
        palabrasTexto2 = new ArrayList<>();

        //Para obtiene las palabras
        StringTokenizer st;

        //Empieza a recorrer el primer texto para hacer el vocabulario
        while ((lineaTexto = br1.readLine()) != null) {
            lineaTexto = lineaTexto.toLowerCase(); //Convierte las líneas en minúsculas

            st = new StringTokenizer(lineaTexto, " .,:;¿?¡!-_"); //Elimina espacios, signos de puntuación

            while (st.hasMoreTokens()) {
                palabra = st.nextToken();
                //Guardamos la palabra en una lista, esto con el fin de que posteriormente
                //se pueda obtener el pesado TF
                palabrasTexto1.add(palabra);

                //Comprobamos que no haya repeticiones de palabras
                boolean palabraEncontrada = false;
                for (int i = 0; i < vocabulario.size(); i++) {
                    String pVocabulario = vocabulario.get(i);
                    if (pVocabulario.equals(palabra)) {
                        palabraEncontrada = true;
                    }
                }

                if (!palabraEncontrada) {
                    vocabulario.add(palabra);
                }
            }
        }

        br1 = new BufferedReader(new FileReader(f2));

        while ((lineaTexto = br1.readLine()) != null) {
            lineaTexto = lineaTexto.toLowerCase(); //Convierte las líneas en minúsculas

            st = new StringTokenizer(lineaTexto, " .,:;¿?¡!-_"); //Elimina espacios, signos de puntuación

            while (st.hasMoreTokens()) {
                palabra = st.nextToken();
                palabrasTexto2.add(palabra);
                //Comprobamos que no haya repeticiones de palabras
                boolean palabraEncontrada = false;
                for (int i = 0; i < vocabulario.size(); i++) {
                    String pVocabulario = vocabulario.get(i);
                    if (pVocabulario.equals(palabra)) {
                        palabraEncontrada = true;
                    }
                }

                if (!palabraEncontrada) {
                    vocabulario.add(palabra);
                }
            }
        }

        return vocabulario;
    }

}
