package hu.szamalk.modell;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Szak implements Serializable {

    private String nev;
    private List<Tantargy> targyak;

    private transient UUID id;

    public Szak(String nev){
        this.nev = nev;
        fileBeolvasas();
        idGen();
    }

    public List<Tantargy> getTargyakNevSzerint(){
        this.targyak.sort(Tantargy.getNevComparator());
        return Collections.unmodifiableList(targyak);
    }

    public List<Tantargy> getTargyakKredSzerint(){
        this.targyak.sort(Tantargy.getKreditComparator());
        return Collections.unmodifiableList(targyak);
    }

    public void statisztika(){
        String nev = "statisztika.txt";

    }

    public void szakKiirasa(){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("targyak.dat"))){
            oos.writeObject(targyak);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void szakBeolvasasa(){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("targyak.dat"))){
            this.targyak = (List<Tantargy>) ois.readObject();
            idGen();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fileBeolvasas(){
        String nev = "tantargyak.txt";
        this.targyak = new ArrayList<>();

        try {
            List<String> targyak = Files.readAllLines(Path.of(nev));
            targyak.forEach(s -> {
                if (!s.contains("NÉV")){
                    this.targyak.add(new Tantargy(s));
                }
            });
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void idGen(){
        id = UUID.randomUUID();
    }
}
