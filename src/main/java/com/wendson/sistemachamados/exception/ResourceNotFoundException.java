package com.wendson.sistemachamados.exception;

//exceção de tempo de execução, resultado em um codigo de status HTTP 404
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
