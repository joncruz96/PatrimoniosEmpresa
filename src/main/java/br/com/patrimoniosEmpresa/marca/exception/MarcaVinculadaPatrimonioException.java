package br.com.patrimoniosEmpresa.marca.exception;

public class MarcaVinculadaPatrimonioException extends Exception {

	private static final long serialVersionUID = -852444680623454382L;

	public MarcaVinculadaPatrimonioException(String errorMessage) {
        super("Marca já vinculada em Patrimonio cadastrado: " + errorMessage);
    }
	
}
