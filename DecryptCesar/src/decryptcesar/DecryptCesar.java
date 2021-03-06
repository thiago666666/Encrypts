package decryptcesar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thiago
 */
public class DecryptCesar {

    /*------------------------------------------------------------------------*/
    //Populando Array com alfabeto
    /*------------------------------------------------------------------------*/
    public List<Character> criarAlfabeto() {
        List<Character> alfabeto = new ArrayList<>();

        for (char ch = 'Z'; ch >= 'A'; ch--) {
            alfabeto.add(ch);
        }

        for (char ch = 'z'; ch >= 'a'; ch--) {
            alfabeto.add(ch);
        }
        
        return alfabeto;
    }
    
    /*------------------------------------------------------------------------*/

    /*------------------------------------------------------------------------*/
    //Lendo a mensagem
    /*------------------------------------------------------------------------*/
    public String getMensagem(String input) throws FileNotFoundException, IOException {
        FileReader reader = new FileReader(input);
        BufferedReader bf = new BufferedReader(reader);

        String index = bf.readLine();
        String mnsgm = "";
        
        while (index != null) {
            mnsgm += index;
            index = bf.readLine();
        }
        
        return mnsgm;
    }

    /*------------------------------------------------------------------------*/

    /*------------------------------------------------------------------------*/
    //Cifrando a mensagem
    /*------------------------------------------------------------------------*/
    public String decifrarMensagem(String mnsgm, int chave) {
        char mnsgmChar = ' ';
        String mnsgmOutput = "";
        List<Character> alfabeto = criarAlfabeto();
        for (int i = 0; i < mnsgm.length(); i++) {
            mnsgmChar = mnsgm.charAt(i);

            for (int j = 0; j < alfabeto.size(); j++) {

                if (mnsgmChar == ' ') {
                    mnsgmOutput += mnsgmChar;
                    break;

                } else if (mnsgmChar == alfabeto.get(j)) {
                    int chCrypto = j + chave;

                    if (j <= 25 && chCrypto > 25) {
                        chCrypto %= 26;
                    } else if (j <= 51 && chCrypto > 51) {
                        chCrypto %= 51;
                        chCrypto += 25;
                    }
                    mnsgmOutput += alfabeto.get(chCrypto);
     
                    break;
                }
            }
        }
        
        mnsgmOutput += "\n";
        return mnsgmOutput;
    }

    /*------------------------------------------------------------------------*/

    /*------------------------------------------------------------------------*/
    //Escrevendo a mensagem decifrada no output 
    /*------------------------------------------------------------------------*/
    public void setMensagem(String output, String mnsgmOutput, boolean naoSobr)
            throws IOException {
        FileWriter writer = new FileWriter(output, naoSobr);
        try (BufferedWriter bw = new BufferedWriter(writer)) {

            bw.write(mnsgmOutput);
            bw.newLine();
            bw.close();
        }
    }

    /*------------------------------------------------------------------------*/

    /*------------------------------------------------------------------------*/
    //Criando array de chaves quando o valor for all
    /*------------------------------------------------------------------------*/

    public List<String> setKeys() {
        List<String> keys = new ArrayList<String>();
        for (int i = 0; i < 26; i++) {
            keys.add(String.valueOf(i));
        }
        return keys;
    }
    
    /*------------------------------------------------------------------------*/

    /*------------------------------------------------------------------------*/
    //Verificando o valor da chave inserida
    /*------------------------------------------------------------------------*/
    
    public int setKey(String key) {
        int chave = 0;
        try {
            chave = Integer.parseInt(key);
        } catch (NumberFormatException exception) {
            System.out.println("Por favor insira uma chave válida de 1 à 25");
            System.exit(0);
        }
        if (chave < 0 || chave > 25) {
            System.out.println("Por favor insira uma chave válida de 1 à 25");
            System.exit(0);
        }
        return chave;
    }
    
    /*não era necessario criar metodos(o codigo poderia ser criado no psvm),
    mas acreditei que seria melhor assim.*/ 

    /*------------------------------------------------------------------------*/

    public static void main(String[] args) throws FileNotFoundException,
            IOException {

    /*------------------------------------------------------------------------*/
    //especificando parametros da aplicação
    /*------------------------------------------------------------------------*/
        String input = args[0];
        String output = args[1];
        String key = args[2];
//        String input = "C:\\Users\\Thiago\\textoCrypt.txt";
//        String output = "C:\\Users\\Thiago\\textoNovo.txt";
//        String key = "all";
        int chave = 0;
        List<String> keys = new ArrayList<String>();
        DecryptCesar aux = new DecryptCesar();
        String mnsgm = aux.getMensagem(input);
        String mnsgmOutput = "";

    /*------------------------------------------------------------------------*/
    //Aqui é onde a magica acontece
    /*------------------------------------------------------------------------*/
        
        if (key.equals("")) {
            key = "3";
        }
        
        if (key.equalsIgnoreCase("ALL")) {
        
            keys = aux.setKeys();
            aux.setMensagem(output, "", false);
            for (int i = 0; i < keys.size(); i++) {
               
                mnsgmOutput = aux.decifrarMensagem(mnsgm, i);
                aux.setMensagem(output, mnsgmOutput, true);
        
            }
        } else {
            
            chave = aux.setKey(key);
            mnsgmOutput = aux.decifrarMensagem(mnsgm, chave);
            aux.setMensagem(output, mnsgmOutput,false);
        
        }
    
    }

}
    //Fim do arquivo
    /*------------------------------------------------------------------------*/
