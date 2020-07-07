package br.com.patrimoniosEmpresa.marca.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.patrimoniosEmpresa.marca.domain.Marca;
import br.com.patrimoniosEmpresa.marca.repository.MarcaRepository;
import lombok.Data;

@Data(staticConstructor = "of")
public class MarcaForm {

	@NotNull @NotEmpty
	private String nome;
	
	public Marca atualizar(Long id, MarcaRepository marcaRepository) {
		Marca marca = marcaRepository.getOne(id);
		
		marca.setNome(nome);
		
		return marca;
	}
	
}
