package br.com.patrimoniosEmpresa.patrimonio.exception;

public class PatrimonioMarcaIdNaoExisteException extends Exception {

	private static final long serialVersionUID = 1053372744607918497L;

	public PatrimonioMarcaIdNaoExisteException(Long errorMessage) {
        super("Não existe a marca informada. Por favor cadastre a Marca "
        		+ "antes de prosseguir com o cadastro de Patrimonio. MarcaId: " + errorMessage);
    }
	
}