package br.com.patrimoniosEmpresa.patrimonio.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
public class Patrimonio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private Long marcaId;
	private String descricao;
	private String numTombo;
	
	@Builder
	public Patrimonio(final String nome,
					  final Long marcaId,
					  final String descricao,
					  final String numTombo) {
		
		this.nome = nome;
		this.marcaId = marcaId;
		this.descricao = descricao;
		this.numTombo = numTombo;
	}

}
