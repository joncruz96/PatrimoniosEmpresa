package br.com.patrimoniosEmpresa.marca.controller.dto;

import org.springframework.data.domain.Page;

import br.com.patrimoniosEmpresa.marca.domain.Marca;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@ApiModel(description = "Informações para cadastro de uma nova marca.")
public class MarcaDto {

	private Long id;
	private String nome;
	
	public MarcaDto(Marca marca) {
		this.id = marca.getId();
		this.nome = marca.getNome();
	}
	
	public static Page<MarcaDto> buscarMarcas(Page<Marca> marcas) {
		return marcas.map(MarcaDto::new);
	}
	
}
