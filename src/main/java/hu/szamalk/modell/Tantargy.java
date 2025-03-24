package hu.szamalk.modell;

import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Tantargy implements Serializable{

    private String nev;
    private int kredit;
    private List<String> tanarok;
    private boolean csakVizsga;

    public Tantargy(String sor){
        String[] sorLista = sor.split(";");
        String[] tanarok = sorLista[3].equals("-") ? new String[]{sorLista[2]} : new String[]{sorLista[2], sorLista[3]};
        this.nev = sorLista[0];
        this.kredit = Integer.parseInt(sorLista[1]);
        this.tanarok = new ArrayList<>(Arrays.asList(tanarok));
        this.csakVizsga = sorLista[5].equals("igen");
    }

    @Override
    public String toString() {
        return "Tantargy{" +
                "nev='" + nev + '\'' +
                ", kredit=" + kredit +
                ", tanarok=" + tanarok +
                ", csakVizsga=" + csakVizsga +
                '}';
    }

    public static NevComparator getNevComparator(){
        return new NevComparator();
    }

    public static KreditComparator getKreditComparator(){
        return new KreditComparator();
    }

    private static class NevComparator implements Comparator<Tantargy> {
        @Override
        public int compare(Tantargy o1, Tantargy o2) {
            Collator coll = Collator.getInstance();
            return coll.compare(o1.nev, o2.nev);
        }
    }

    private static class KreditComparator implements Comparator<Tantargy> {
        @Override
        public int compare(Tantargy o1, Tantargy o2) {
            return o1.kredit - o2.kredit;
        }
    }
}
