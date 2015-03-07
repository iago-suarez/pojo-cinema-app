package es.udc.pojo.web.util;

import es.udc.pojo.model.userprofile.TipoUsuario;

public class UserSession {

    private Long        userProfileId;
    private String      firstName;
    private TipoUsuario tipoUsuario;

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public boolean isEspectador() {
        return tipoUsuario.equals(TipoUsuario.ESPECTADOR);
    }

    public boolean isTaquillero() {
        return tipoUsuario.equals(TipoUsuario.TAQUILLERO);
    }

    public boolean isAdministrador() {
        return tipoUsuario.equals(TipoUsuario.ADMINISTRADOR);
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}
