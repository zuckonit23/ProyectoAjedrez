package com.academiaajedrez.exceptions;

/**
 * Excepción lanzada cuando no se encuentra un usuario durante la autenticación.
 */
public class UsuarioNoEncontradoException extends Exception {
    public UsuarioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
