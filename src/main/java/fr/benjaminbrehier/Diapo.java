package fr.benjaminbrehier;

import java.util.ArrayList;

public class Diapo {
    private Photo[] photos;
    private ArrayList<String> tags;
    private boolean used;

    public Diapo(Photo[] photos) {
        this.photos = photos;
        this.tags = new ArrayList<String>();
        this.used = false;
        for (Photo p : photos) {
            for (String tag : p.getTags()) {
                if (!tags.contains(tag)) {
                    tags.add(tag);
                }
            }
        }
    }

    public Photo[] getPhotos() {
        return photos;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String toString() {
        String s = "";
        for (Photo p : photos) {
            s += p.getId() + " ";
        }

        // s += " tags : ";

        // for (String tag : tags) {
        //     s += tag + " ";
        // }

        // Trouver et Afficher les tags en communs entre les photos
        // if (photos.length != 1) {
        //     ArrayList<String> commonTags = new ArrayList<String>();
        //     for (String tag : photos[0].getTags()) {
        //         if (photos[1].getTags().contains(tag)) {
        //             commonTags.add(tag);
        //         }
        //     }
        //     s += " tags communs : ";
        //     for (String tag : commonTags) {
        //         s += tag + " ";
        //     }
        // }
        return s;
    }

    public int getNbCommonTags(Diapo d1) {
        int nbCommonTags = 0;
        for (String tag : tags) {
            if (d1.getTags().contains(tag)) {
                nbCommonTags++;
            }
        }
        return nbCommonTags;
    }
}
