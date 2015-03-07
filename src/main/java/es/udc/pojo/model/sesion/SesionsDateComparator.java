package es.udc.pojo.model.sesion;

import java.util.Comparator;

/**
 * The Class SesionsDateComparator.
 */
public class SesionsDateComparator implements Comparator<Sesion> {

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Sesion s1, Sesion s2) {
        return s1.getHorayFecha().compareTo(s2.getHorayFecha());
    }
}
