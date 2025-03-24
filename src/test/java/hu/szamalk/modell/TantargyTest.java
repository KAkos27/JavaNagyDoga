package hu.szamalk.modell;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TantargyTest {

    @Test
    void testUgyanazATanarNevKonst(){
        Tantargy t = new Tantargy("informatika;3;Kovács Imre;Kovács Imre;1;nem");

        Assertions.assertEquals("-", t.getTanarok().get(1));
    }

    @Test
    void testMinKredit(){
        Tantargy t = new Tantargy("informatika;3;Kovács Imre;Turán Lajos;1;nem");

        Assertions.assertThrows(NemJoKreditException.class, ()->{
            t.setKredit(0);
        });
    }

    @Test
    void testMinKreditKonstruktor(){
        Assertions.assertThrows(NemJoKreditException.class, ()->{
            new Tantargy("informatika;0;Kovács Imre;Turán Lajos;0;nem");
        });
    }

    @Test
    void testMaxKredit(){
        Tantargy t = new Tantargy("informatika;2;Kovács Imre;Turán Lajos;1;nem");

        Assertions.assertThrows(NemJoKreditException.class, ()->{
            t.setKredit(9);
        });
    }

    @Test
    void testMaxKreditKonstruktor(){
        Assertions.assertThrows(NemJoKreditException.class, ()->{
            new Tantargy("informatika;9;Kovács Imre;Turán Lajos;10;nem");
        });
    }
}