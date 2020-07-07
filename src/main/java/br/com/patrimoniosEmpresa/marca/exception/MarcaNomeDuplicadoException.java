package br.com.patrimoniosEmpresa.marca.exception;

public class MarcaNomeDuplicadoException extends Exception {

	private static final long serialVersionUID = -2799268908911592429L;

	public MarcaNomeDuplicadoException(String errorMessage) {
        super("Já existe este nome para marca já cadastrada: " + errorMessage);
    }
	
}
