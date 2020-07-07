package br.com.patrimoniosEmpresa.patrimonio.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.patrimoniosEmpresa.patrimonio.domain.Patrimonio;
import br.com.patrimoniosEmpresa.patrimonio.repository.PatrimonioRepository;
import lombok.Data;

@Data(staticConstructor = "of")
public class PatrimonioForm {

	@NotNull @NotEmpty
	private String nome;
	
	@NotNull
	private Long marcaId;
	
	private String descricao;

	public Patrimonio atualizar(Long id, PatrimonioRepository patrimonioRepository) {
		Patrimonio patrimonio = patrimonioRepository.getOne(id);
		
		patrimonio.setNome(nome);
		patrimonio.setMarcaId(id);
		patrimonio.setDescricao(descricao);
		
		return patrimonio;
	}
	
}
