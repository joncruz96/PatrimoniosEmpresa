package br.com.patrimoniosEmpresa.patrimonio.controller.dto;

import org.springframework.data.domain.Page;

import br.com.patrimoniosEmpresa.patrimonio.domain.Patrimonio;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@ApiModel(description = "Informações para cadastro de um novo patrimonio.")
public class PatrimonioDto {

	private Long id;
	private String nome;
	private Long marcaId;
	private String descricao;
	private String numTombo;

	public PatrimonioDto(Patrimonio patrimonio) {
		this.id = patrimonio.getId();
		this.nome = patrimonio.getNome();
		this.marcaId = patrimonio.getMarcaId();
		this.descricao = patrimonio.getDescricao();
		this.numTombo = patrimonio.getNumTombo();
	}
	
	public static Page<PatrimonioDto> buscarPatrimonios(Page<Patrimonio> patrimonios) {
		return patrimonios.map(PatrimonioDto::new);
	}
	
}
