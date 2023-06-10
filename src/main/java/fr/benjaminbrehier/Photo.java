package fr.benjaminbrehier;

import java.util.ArrayList;

public class Photo {
    private boolean vertical;
    private ArrayList<String> tags;
    private int id;
    private boolean used;

    public Photo(boolean vertical, int id) {
        this.vertical = vertical;
        this.tags = new ArrayList<String>();
        this.id = id;
        this.used = false;
    }

    public boolean isVertical() {
        return vertical;
    }

    public boolean isUsed() {
        return used;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public int getId() {
        return id;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }



    public String toString() {
        String s = "";
        s += vertical ? "V" : "H";
        s += " " + id;
        for (String tag : tags) {
            s += " " + tag;
        }
        return s;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public void addTags(ArrayList<String> tags) {
        this.tags.addAll(tags);
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    public int getNbCommonTags(Photo p) {
        int nbCommonTags = 0;
        for (String tag : tags) {
            if (p.getTags().contains(tag)) {
                nbCommonTags++;
            }
        }
        return nbCommonTags;
    }
}