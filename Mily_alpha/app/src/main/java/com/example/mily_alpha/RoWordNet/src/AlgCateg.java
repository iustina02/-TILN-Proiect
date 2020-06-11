package com.example.mily_alpha.RoWordNet.src;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.mily_alpha.RoWordNet.src.data.Literal;
import com.example.mily_alpha.RoWordNet.src.data.RoWordNet;
import com.example.mily_alpha.RoWordNet.src.data.Synset;
import com.example.mily_alpha.RoWordNet.src.io.XMLRead;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class AlgCateg {
//    RoWordNet ro_wordnet = RoWordNet.deserializeFromFile("C:\\Users\\iusti\\OneDrive\\Desktop\\-TILN-Proiect\\Mily_alpha\\app\\src\\main\\java\\com\\example\\mily_alpha\\RoWordNet\\res\\RoWordNet.data");
    RoWordNet ro_wordnet = new RoWordNet(XMLRead.read("res\\rown.xml"));
    public String FindCateg(String arg1){
        String[] argSplit = arg1.split("\n");

        List<String> list_cuv_schimba = Arrays.asList("branza", "smantana","mar", "para", "spaghetti");
        List<String> list_cuv_diacritice = Arrays.asList("brânză","smântână","măr", "pară", "spaghete");
        List<String>Categorii = Arrays.asList("lactate", "ulei", "fruct", "legumă","rădăcină","cereală","făină","condiment","mirodenie","alcool","fasole","tubercul","paste","sos","carne");

        Map<String, String> Produs_Categ = new HashMap<String, String>();
//        map.put("dog", "type of animal");
//        System.out.println(map.get("dog"));
        for (String produ: argSplit) {
            String[] argSplit2 = produ.split(" ");

            List<String> Non_Products = Arrays.asList("sls", "kaufland", "str.", "nr.", "jud.", "mun.", "cod",
                    "identificare", "fiscala", "total", "lei", "tva", "tua", "punga",
                    "cash", "kgx", "mn.", "romania", "anbalaj", "ambalaj", "cocd", "tdentificare", "pentru", "prosop", "glade");

            boolean pass = false;
            for (String non_product: argSplit2) {
                if(Non_Products.contains(non_product)){
                    pass = true;
                }
            }
            if(pass == false){
                for (String wrd : argSplit2){
                    if(isNumeric(wrd)==true || hasNumbers(wrd) == true|| wrd.contains("BUC") || wrd== "x" || wrd == "X" || wrd.length()<=2)
                    {
                        continue;
                    }
                    else {
                        for(String cuv : list_cuv_schimba){
                            if(wrd.toLowerCase() ==cuv){
                                wrd = list_cuv_diacritice.get(list_cuv_diacritice.indexOf(wrd));
                                System.out.println("Diacritice: " + wrd);
                            }
                        }

                        List<String> list_carne = Arrays.asList("pui", "tocata", "os","pulpe");
                        List<String> list_faina_gris_malai = Arrays.asList("faina","gris","malai");
                        List<String> list_alcool = Arrays.asList("ciuc","radler","ursus","salitos");
                        List<String> list_suc = Arrays.asList("cola");
                        List<String> list_gustari = Arrays.asList("lay s","chio","lays","chips","krax");

                        if(list_carne.contains(wrd)){
                            Produs_Categ.put(produ,"carne,");
                        }
                        else if(list_faina_gris_malai.contains(wrd)){
                            Produs_Categ.put(produ,"cereale,");
                        }
                        else if(list_alcool.contains(wrd)){
                            Produs_Categ.put(produ,"alcool,");
                        }
                        else if(list_suc.contains(wrd)){
                            Produs_Categ.put(produ,"suc,");
                        }
                        else if(list_gustari.contains(wrd)){
                            Produs_Categ.put(produ,"gustari,");
                        }

                        Literal literal = new Literal(wrd.toLowerCase());
                        Synset synset_ids = ro_wordnet.getSynsetFromLiteral(literal);
                        System.out.println(synset_ids.toString());
                    }
                }
            }

        }
        return "AIci categ";
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static boolean hasNumbers(String str) {
        if(str.contains("0") || str.contains("1") || str.contains("2") || str.contains("3") || str.contains("4") || str.contains("5")
            || str.contains("6") || str.contains("7") ||str.contains("8")||str.contains("9")){
            return true;
        }
        return false;
    }

    public AlgCateg() throws Exception {
    }
}
