package pl.edu.pja.sri.ewcislo.zad06Corba.server;

import pl.edu.pja.sri.ewcislo.zad06_corba_arytmetyka.ArytmetykaPOA;
import pl.edu.pja.sri.ewcislo.zad06_corba_arytmetyka.ArytmetykaPackage.DzieleniePrzezZero;


public class ArytmetykaServant extends ArytmetykaPOA {

    private double s1, s2, wynik;

    @Override
    public double s1() {
        return s1;
    }

    @Override
    public double s2() { return s2; }

    @Override
    public double wynik() { return wynik; }

    @Override
    public void s1(double arg) { this.s1 = arg; }

    @Override
    public void s2(double arg) { this.s2 = arg; }

    @Override
    public void suma() { this.wynik = s1 + s2; }

    @Override
    public void roznica() {
        this.wynik = s1 - s2;
    }

    @Override
    public void iloczyn() { this.wynik = s1 * s2; }

    @Override
    public void iloraz() throws DzieleniePrzezZero {
        if (s2 == 0) {
            throw new DzieleniePrzezZero();
        }
        this.wynik = s1 / s2;
    }
}
