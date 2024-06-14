package pl.edu.pja.sri.ewcislo.zad06Corba.client;

import org.omg.CORBA.ORB;
import org.omg.CORBA.PolicyListHolder;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import pl.edu.pja.sri.ewcislo.zad06_corba_arytmetyka.Arytmetyka;
import pl.edu.pja.sri.ewcislo.zad06_corba_arytmetyka.ArytmetykaHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ArytmetykaClient {
    public static void main(String[] args) throws Exception {
        //inicjalizacja ORB
        ORB orb = ORB.init(args, null);

        //odczyt referencji do obiektu usługi z pliku
//        org.omg.CORBA.Object obj = readRefFromFile(orb, "ref.ior");

        //odczyt referencji z usługi nazwowej
        org.omg.CORBA.Object obj = readRefFromNamingService(orb, "Arytmetyka");

        //rzutowanie obiektu CORBA na obiekt Java (używamy interfejsu Arytmetyka)
        Arytmetyka arytmetyka = ArytmetykaHelper.narrow(obj);

        //ustawienie parametrów operacji
        double s1 = 2;
        double s2 = 5;
        arytmetyka.s1(s1);
        arytmetyka.s2(s2);

        //wywołanie metody liczącej
        arytmetyka.suma();

        //pobranie wyniku
        double suma = arytmetyka.wynik();

        //wyświetlenie wyniku
        System.out.printf("Wynik %.2f + %.2f = %.2f", s1, s2, suma);
    }

    /**
     * Metoda pomocnicza umożliwiająca odczyt referencji do obiektu CORBA z pliku
     */

//    private static org.omg.CORBA.Object readRefFromFile(ORB orb, String fileName) throws Exception {
//        File f = new File(fileName);
//        try (BufferedReader br = new BufferedReader(new FileReader(f));) {
//            org.omg.CORBA.Object obj = orb.string_to_object(br.readLine());
//            obj._validate_connection(new PolicyListHolder());
//            return obj;
//        }
//    }

    private static org.omg.CORBA.Object readRefFromNamingService(ORB orb, String refName) throws Exception {
        //uzyskanie referencji do usługi nazwowej
        org.omg.CORBA.Object o = orb.resolve_initial_references("NameService");

        //rzutowanie obiektu CORBA (usługi nazwowej) na obiekt Java
        NamingContextExt rootContext = NamingContextExtHelper.narrow( o );

        //tworzenie komponentu nazwowego
        NameComponent nc = new NameComponent(refName, "");

        //tworzenie ściezki nazwowej
        NameComponent path[] = {nc};

        //pobranie obiektu z usługi nazwowej
        return rootContext.resolve(path);
    }
}
