package es.udc.pojo.model.sesion;

import java.util.Comparator;

public class SesionsDateComparator implements Comparator<Sesion> {

    @Override
    public int compare(Sesion s1, Sesion s2) {
        return s1.getHorayFecha().compareTo(s2.getHorayFecha());
    }
}
