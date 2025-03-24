package hu.szamalk.modell;

import java.io.Serializable;
import java.text.Collator;
import java.util.*;

public class Tantargy implements Serializable{

    private String nev;
    private int kredit;
    private List<String> tanarok;
    private int felev;
    private boolean csakVizsga;

    public Tantargy(){
        this(" ;1; ; ;1;nem");
    }

    public Tantargy(String sor){
        String[] sorLista = sor.split(";");
        String[] tanarok = sorLista[3].equals("-") ? new String[]{sorLista[2]} : new String[]{sorLista[2], sorLista[3]};
        if (Objects.equals(tanarok[0], tanarok[1])){
            tanarok[1] = "-";
        }
        this.nev = sorLista[0];
        setKredit(Integer.parseInt(sorLista[1]));
        this.tanarok = new ArrayList<>(Arrays.asList(tanarok));
        this.felev = Integer.parseInt(sorLista[4]);
        this.csakVizsga = sorLista[5].equals("igen");
    }

    public void setKredit(int kredit) {
        if (kredit < 1 || kredit > 5){
            throw new NemJoKreditException("A kredit nem lehet kisebb mint 1, vagy nagyobb mitn 5");
        }
        this.kredit = kredit;
    }

    public int getFelev() {
        return felev;
    }

    public String getNev() {
        return nev;
    }

    public int getKredit() {
        return kredit;
    }

    public List<String> getTanarok() {
        return Collections.unmodifiableList(tanarok);
    }

    public boolean isCsakVizsga() {
        return csakVizsga;
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
