package br.com.patrimoniosEmpresa.usuario.exception;

public class UsuarioEmailDuplicadoException extends Exception { 
    
	private static final long serialVersionUID = -4355088986867782736L;

	public UsuarioEmailDuplicadoException(String errorMessage) {
        super("Já existe este email para usuário cadastrado: " + errorMessage);
    }
	
}
