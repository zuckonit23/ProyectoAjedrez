package com.academiaajedrez.exceptions;

/**
 * Excepción lanzada cuando se intenta registrar un usuario que ya existe.
 */
public class UsuarioExistenteException extends Exception {
    public UsuarioExistenteException(String mensaje) {
        super(mensaje);
    }
}
