package es.udc.pojo.model.util;

/**
 * The Class BadArgumentException.
 */
@SuppressWarnings("serial")
public class BadArgumentException extends Exception {

    /**
     * Instantiates a new bad argument exception.
     *
     * @param msg
     *            the msg
     */
    public BadArgumentException(String msg) {
        super(msg);
    }
}
