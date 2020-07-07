package br.com.patrimoniosEmpresa.marca.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.patrimoniosEmpresa.marca.domain.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Long	> {
	
	Optional<Marca> findById(Long id);
	
	Optional<Marca> findByNome(String nome);
}
