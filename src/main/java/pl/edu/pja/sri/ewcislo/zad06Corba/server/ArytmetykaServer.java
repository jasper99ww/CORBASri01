package pl.edu.pja.sri.ewcislo.zad06Corba.server;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class ArytmetykaServer {
    public static void main(String[] args) throws Exception {
        // Inicjalizacja ORB
        org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);

        // Inicjalizacja POA (Portable Object Adapter) - adaptera obiektów Java do obiektów CORBA
        POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        poa.the_POAManager().activate();

        // Inicjalizacja obiektu implementującego usługę
        ArytmetykaServant arytmetykaServant = new ArytmetykaServant();

        // Utworzenie referencji CORBA do obiektu implementującego usługę
        org.omg.CORBA.Object o = poa.servant_to_reference(arytmetykaServant);

        // Zapisanie referencji do pliku
//        saveRefAsFile(orb, o, "ref.ior");

        //zapisanie referencji do usługi nazwowej
        saveRefInNamingService(orb, o, "Arytmetyka");

        // kod potrzymujacy dzialanie serwera w oczekiwaniu na wywolania klientów
        java.lang.Object sync = new java.lang.Object();
        synchronized (sync) {
            sync.wait();
        }
    }

    /**
     * Metoda pomocnicza umożliwiająca zapisanie referencji obiektu CORBA do pliku,
     * w celu jej późniejszego odczytu przez klienta usługi
     */
//    private static void saveRefAsFile(ORB orb, org.omg.CORBA.Object ref, String fileName) throws Exception {
//        try (PrintWriter ps = new PrintWriter(new FileOutputStream(new File(fileName)));) {
//            ps.println(orb.object_to_string(ref));
//        }
//    }

    private static void saveRefInNamingService(ORB orb, org.omg.CORBA.Object ref, String refName) throws Exception {
        //uzyskanie referencji do usługi nazwowej
        org.omg.CORBA.Object o = orb.resolve_initial_references("NameService");

        //rzutowanie obiektu CORBA (usługi nazwowej) na obiekt Java
        NamingContextExt rootContext = NamingContextExtHelper.narrow( o );

        //tworzenie komponentu nazwowego
        NameComponent nc = new NameComponent(refName, "");

        //tworzenie ścieżki nazwowej
        NameComponent path[] = {nc};

        //rejestracja obiektu w usłudze nazwowej pod daną ścieżką
        rootContext.rebind(path, ref);
    }
}
