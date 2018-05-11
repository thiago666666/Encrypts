package encryptpoli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import sun.util.locale.provider.AuxLocaleProviderAdapter;

/**
 *
 * @author Thiago
 */
public class EncryptPoli {

    /*------------------------------------------------------------------------*/
    //Populando Array com alfabeto
    /*------------------------------------------------------------------------*/
    public char[][] criarAlfabeto() {
        char[][] alfabeto = new char[26][26];

        for (int chLinha = 0; chLinha <= 25; chLinha++) {
            char chIndex = (char) 65;
            chIndex += chLinha;

            for (int chColuna = 0; chColuna <= 25; chColuna++) {
                char chAtual = (char) (chIndex + chColuna);
                if (chAtual > 90) {
                    chAtual -= 26;

                }
                alfabeto[chLinha][chColuna] = chAtual;

            }

        }

        return alfabeto;
    }

    /*sim, eu pesquisei na internet se tinha como fazer isso de uma forma 
        mais pratica e com o código mais limpo, mas não foi ctrl+c e ctrl+v(e
        ironicamente eu demorei mais assim do que fazendo manualmente :P)
     */
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
    public String cifrarMensagem(String mnsgm, String key) {

        int mnsgmChar = ' ';
        String chaveCompleta = setKey(mnsgm, key);
        String mnsgmOutput = "";
        char[][] alfabeto = criarAlfabeto();
        for (int i = 0; i < mnsgm.length(); i++) {
            if (mnsgm.charAt(i) == ' ') {
                mnsgmOutput += ' ';
            } else {
                if (Character.isUpperCase(mnsgm.charAt(i))) {
                    for (int j = 0; j < alfabeto.length; j++) {
                        if (alfabeto[0][j] == mnsgm.charAt(i)) {
                            for (char[] alfabeto1 : alfabeto) {
                                if (alfabeto1[0] == chaveCompleta.charAt(i)) {
                                    mnsgmOutput += alfabeto1[j];
                                }
                            }
                        }
                    }
                } else {
                    for (int j = 0; j < alfabeto.length; j++) {
                        if (Character.toLowerCase(alfabeto[0][j])
                                == mnsgm.charAt(i)) {
                            for (char[] alfabeto1 : alfabeto) {
                                if (alfabeto1[0] == chaveCompleta.charAt(i)) {
                                    mnsgmOutput
                                            += Character.toLowerCase(alfabeto1[j]);
                                }
                            }
                        }
                    }
                }
            }
        }
        mnsgmOutput += "\n";
        return mnsgmOutput;
    }

    /*------------------------------------------------------------------------*/

 /*------------------------------------------------------------------------*/
    //Escrevendo a mensagem cifrada no output 
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
    public String setKey(String mnsgm, String key) {

        if (key.equals("")) {
            key = "senaiblumenausc";
        } else {

            int i = 0;
            if (key.length() < mnsgm.length()) {

                for (int j = key.length(); j < mnsgm.length(); j++) {
                    key += key.charAt(i);
                    if (i == key.length()) {
                        i = 0;
                    } else {
                        i++;
                    }
                }
            }
        }
        return key;

    }

    /*------------------------------------------------------------------------*/
 /*------------------------------------------------------------------------*/
    public static void main(String[] args) throws FileNotFoundException,
            IOException {

        /*------------------------------------------------------------------------*/
        //especificando parametros da aplicação
        /*------------------------------------------------------------------------*/
//        String input = args[0];
//        String output = args[1];
//        String key = args[2];
        String input = "C:\\Users\\Thiago\\Desktop\\texto.txt";
        String output = "C:\\Users\\thiago\\Desktop\\textoCrypt.txt";

        EncryptPoli aux = new EncryptPoli();
        try {
            String key = "abc";
            //String key = args[2];
        } catch (RuntimeException e) {
            System.out.println("Insira uma chave valida");
            System.exit(0);
        }
        String key = "ABC";
        //  String key = args[2];
        String chave = aux.setKey(input, key);
        String mnsgm = aux.getMensagem(input);
        String mnsgmOutput = "";

        /*------------------------------------------------------------------------*/
        //Aqui é onde a magica acontece
        /*------------------------------------------------------------------------*/
        mnsgmOutput = aux.cifrarMensagem(mnsgm, chave);
        aux.setMensagem(output, mnsgmOutput, false);

    }

}

//}
//Fim do arquivo
/*------------------------------------------------------------------------*/
