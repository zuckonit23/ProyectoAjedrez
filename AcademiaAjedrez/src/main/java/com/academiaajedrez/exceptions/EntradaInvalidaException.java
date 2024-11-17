package com.academiaajedrez.exceptions;

/**
 * Excepción lanzada cuando se proporciona una entrada inválida.
 */
public class EntradaInvalidaException extends Exception {
    public EntradaInvalidaException(String mensaje) {
        super(mensaje);
    }
}