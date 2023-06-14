package fr.benjaminbrehier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Logic {
    public static void main(String[] args) throws IOException {
        ArrayList<Photo> photos = new ArrayList<Photo>();
        //Créer les diapos
        ArrayList<Diapo> diapos = new ArrayList<Diapo>();
        
        
        //Lire le fichier d'entrée a_example.json
        String fileName = "a_example";
        System.out.println("Lecture du fichier " + fileName + ".json");
        File file = new File("src/main/java/fr/benjaminbrehier/datasets_map/" + fileName + ".json");
        // Créer l'objet File Reader
        FileReader fr = new FileReader(file);

        try (FileReader reader = new FileReader(file)) {
            // read the json file
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            for (Object key : jsonObject.keySet()) {
                String imageKey = (String) key;
                JSONObject imageObject = (JSONObject) jsonObject.get(imageKey);

                // Récupération des valeurs
                String style = (String) imageObject.get("style");
                JSONArray tagsArray = (JSONArray) imageObject.get("tags");

                Photo photo = new Photo(style.equals("V"), Integer.parseInt(imageKey));
                for (Object tag : tagsArray) {
                    photo.addTag((String) tag);
                }
                photos.add(photo);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        fr.close();


        // // Classer les photos par nombre de tags en commun
        // ArrayList<Photo> photosVerticales = new ArrayList<Photo>();
        // ArrayList<Photo> photosHorizontales = new ArrayList<Photo>();
        // for (Photo p : photos) {
        //     if (p.isVertical()) {
        //         photosVerticales.add(p);
        //     } else {
        //         photosHorizontales.add(p);
        //     }
        // }

        // photosVerticales.sort((p1, p2) -> p1.getNbCommonTags(p2) - p2.getNbCommonTags(p1));
        // photosHorizontales.sort((p1, p2) -> p1.getNbCommonTags(p2) - p2.getNbCommonTags(p1));

        // photosVerticales.addAll(photosHorizontales);
        // photos = photosVerticales;

        // photos.sort((p1, p2) -> p1.getNbCommonTags(p2) - p2.getNbCommonTags(p1));
        // photos.addAll(photosHorizontales);

        // Créer les diapos
        for (Photo p : photos) {
            if (p.isVertical()) {
                if (p.isUsed()) {
                    continue;
                }
                Photo bestMatch = null;
                for (Photo p2 : photos) {
                    if (p2.isVertical() && !p2.isUsed() && p2.getId() != p.getId()) {
                        if (p.getNbCommonTags(p2) == 0) {
                            bestMatch = p2;
                        }
                    }
                }
                if (bestMatch != null) {
                    p.setUsed(true);
                    bestMatch.setUsed(true);
                    Diapo d = new Diapo(new Photo[] {p, bestMatch});
                    diapos.add(d);
                }
            } else {
                if (p.isUsed()) {
                    continue;
                }
                p.setUsed(true);
                Diapo d = new Diapo(new Photo[] {p});
                diapos.add(d);
            }
        }

        // Trier le diaporama
        sortDiaporama(diapos);

        // Ecrire le fichier de sortie

        String output = fileName + "\n";
        output += diapos.size() + "\n";
        for (Diapo d : diapos) {
            output += d.toString() + "\n";
        }
        
        try {
            FileWriter fw = new FileWriter("/Users/benjaminbrehier/Documents/Dev/scientific-day-2023-challenge-evaluator/outputs/"+fileName+".txt", false);
            BufferedWriter bw = new BufferedWriter(fw);
            // Enregistrer le score dans le fichier ainsi que la date et le nom
            bw.write(output);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }

    public static void sortDiaporama(ArrayList<Diapo> diapos) {
        for (int i = 0; i < diapos.size(); i++) {
            Diapo d = diapos.get(i);
            Diapo bestMatch = null;
            int bestMatchScore = 0;
            for (Diapo d2 : diapos) {
                if (!d.equals(d2)) {
                    int score = d.getNbCommonTags(d2);
                    if (score > bestMatchScore) {
                        bestMatch = d2;
                        bestMatchScore = score;
                    }
                }
            }
            if (bestMatch != null) {
                diapos.remove(d);
                diapos.add(diapos.indexOf(bestMatch) + 1, d);
            } else {
                System.out.println("No match");
                diapos.add(d);
            }
        }





        // List<Diapo> diaporama = new ArrayList<Diapo>();
        // for (Diapo d : diapos) {
        //     if (d.isUsed()) {
        //         continue;
        //     }
        //     Diapo bestMatch = null;
        //     int bestMatchScore = 0;
        //     for (Diapo d2 : diapos) {
        //         if (!d2.isUsed() && !d.equals(d2)) {
        //             if (d.getNbCommonTags(d2) > bestMatchScore) {
        //                 bestMatch = d2;
        //                 bestMatchScore = d.getNbCommonTags(d2);
        //             }
        //         }
        //     }
        //     if (bestMatch != null && diaporama.size() > 0 && diaporama.contains(bestMatch)) {
        //         d.setUsed(true);
        //         diaporama.add(diaporama.indexOf(bestMatch), d);
        //     } else if (bestMatch != null) {
        //         diaporama.add(d);
        //         d.setUsed(true);
        //         diaporama.add(bestMatch);
        //         bestMatch.setUsed(true);
        //         bestMatch.setUsed(true);
        //     } else {
        //         System.out.println("No match");
        //         diaporama.add(d);
        //         d.setUsed(true);
        //     }
        // }
        // return diaporama;
    }
}
