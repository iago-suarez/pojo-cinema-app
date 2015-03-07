package es.udc.pojo.web.util;

import es.udc.pojo.model.userprofile.TipoUsuario;

/**
 * The Class UserSession.
 */
public class UserSession {

    /** The user profile id. */
    private Long        userProfileId;

    /** The first name. */
    private String      firstName;

    /** The tipo usuario. */
    private TipoUsuario tipoUsuario;

    /**
     * Gets the tipo usuario.
     *
     * @return the tipo usuario
     */
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * Sets the tipo usuario.
     *
     * @param tipoUsuario
     *            the new tipo usuario
     */
    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    /**
     * Checks if is espectador.
     *
     * @return true, if is espectador
     */
    public boolean isEspectador() {
        return tipoUsuario.equals(TipoUsuario.ESPECTADOR);
    }

    /**
     * Checks if is taquillero.
     *
     * @return true, if is taquillero
     */
    public boolean isTaquillero() {
        return tipoUsuario.equals(TipoUsuario.TAQUILLERO);
    }

    /**
     * Checks if is administrador.
     *
     * @return true, if is administrador
     */
    public boolean isAdministrador() {
        return tipoUsuario.equals(TipoUsuario.ADMINISTRADOR);
    }

    /**
     * Gets the user profile id.
     *
     * @return the user profile id
     */
    public Long getUserProfileId() {
        return userProfileId;
    }

    /**
     * Sets the user profile id.
     *
     * @param userProfileId
     *            the new user profile id
     */
    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName
     *            the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}
