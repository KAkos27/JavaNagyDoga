package hu.szamalk.modell;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Szak implements Serializable {

    private String nev;
    protected List<Tantargy> targyak;

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

    private Map<String, List<String>> melyikTargyKi() {
        Map<String, List<String>> hm = new HashMap<>();
        targyak.forEach(targy -> {
            String kulcs = targy.getNev();
            if(hm.containsKey(kulcs)){
                List<String> tanarok = hm.get(kulcs);
                targy.getTanarok().forEach(tanar -> {
                    if (!tanarok.contains(tanar)){
                        tanarok.add(tanar);
                    }
                });
                hm.put(kulcs, tanarok);
            }else{
                hm.put(kulcs, targy.getTanarok());
            }
        });
        return hm;
    }

    public void statisztika(){
        StringBuilder vizsgaTargyak = getStringBuilder();
        int kreditek = targyak.stream().mapToInt(Tantargy::getKredit).sum();
        int min = getMin();
        int max = getMax();

        String nev = "statisztika.txt";
        String ki = "Különböző tárgyak: " + targyak.size() + "\n"
                + "Vizsga tágyak:\n" + vizsgaTargyak
                + "Összes kredit:" + kreditek + "\n"
                + "Min kredit: " + targyak.get(min).getNev() + ", " + targyak.get(min).getKredit() + "\n"
                + "Max kredit: " + targyak.get(max).getNev() + ", " + targyak.get(max).getKredit() + "\n"
                + melyikTargyKi();

        try {
            Files.write(Path.of(nev), ki.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Tantargy> getTargyak() {
        return Collections.unmodifiableList(targyak);
    }

    private int getMax() {
        int max = 0;
        for (int i = 0; i < targyak.size(); i++) {
            if (targyak.get(max).getKredit() < targyak.get(i).getKredit()){
                max = i;
            }
        }
        return max;
    }

    private int getMin() {
        int min = 0;
        for (int i = 0; i < targyak.size(); i++) {
            if (targyak.get(min).getKredit() > targyak.get(i).getKredit()){
                min = i;
            }
        }
        return min;
    }

    private StringBuilder getStringBuilder() {
        StringBuilder vizsgaTargyak = new StringBuilder();
        for (Tantargy tantargy : targyak) {
            if (tantargy.isCsakVizsga()){
                vizsgaTargyak.append(tantargy.getNev()).append(", ").append(tantargy.getKredit()).append(", ").append(tantargy.getFelev());
                vizsgaTargyak.append("\n");
            }
        }
        return vizsgaTargyak;
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
