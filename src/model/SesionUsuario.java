package model;

public class SesionUsuario {
    private static SesionUsuario instancia;
    private Usuario usuarioLogueado;

    private SesionUsuario() {} // Constructor privado para patrón Singleton

    public static SesionUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SesionUsuario();
        }
        return instancia;
    }

    public void iniciarSesion(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void cerrarSesion() {
        usuarioLogueado = null;
    }
}
