package hu.szamalk;

import hu.szamalk.modell.Szak;

public class Main {

    public static void main(String[] args) {
        Szak sz = new Szak("Szak neve");
        sz.szakKiirasa();
        sz.szakBeolvasasa();
        sz.statisztika();
    }
}