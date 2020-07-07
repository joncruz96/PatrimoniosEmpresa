package br.com.patrimoniosEmpresa.patrimonio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.patrimoniosEmpresa.patrimonio.domain.Patrimonio;

public interface PatrimonioRepository extends JpaRepository<Patrimonio, Long> {

	Optional<Patrimonio> findByMarcaId(Long marcaId);
	
	Optional<Patrimonio> findByNumTombo(String numTombo);
		
}