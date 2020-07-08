package br.com.patrimoniosEmpresa.marca.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.patrimoniosEmpresa.marca.controller.dto.MarcaDto;
import br.com.patrimoniosEmpresa.marca.controller.form.MarcaForm;
import br.com.patrimoniosEmpresa.marca.domain.Marca;
import br.com.patrimoniosEmpresa.marca.exception.MarcaNomeDuplicadoException;
import br.com.patrimoniosEmpresa.marca.exception.MarcaVinculadaPatrimonioException;
import br.com.patrimoniosEmpresa.marca.repository.MarcaRepository;
import br.com.patrimoniosEmpresa.patrimonio.domain.Patrimonio;
import br.com.patrimoniosEmpresa.patrimonio.repository.PatrimonioRepository;

@RestController
@RequestMapping("/marcas")
public class MarcaController {

	@Autowired
	private MarcaRepository marcaRepository;

	@Autowired
	private PatrimonioRepository patrimonioRepository;
	
	@GetMapping
	@Cacheable(value = "listaDeMarcas")
	public Page<MarcaDto> lista(@PageableDefault(direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
		Page<Marca> marcas = marcaRepository.findAll(paginacao);
		return MarcaDto.buscarMarcas(marcas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MarcaDto> detalhar( @PathVariable Long id ) {
		
		Optional<Marca> marca = marcaRepository.findById(id);
		if (marca.isPresent()) {
			return ResponseEntity.ok(new MarcaDto(marca.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaDeMarcas", allEntries = true)
	public ResponseEntity<MarcaDto> cadastrar(@RequestBody @Valid MarcaForm form, UriComponentsBuilder uriBuilder) throws MarcaNomeDuplicadoException {
		
		if (this.marcaRepository.findByNome(form.getNome()).isPresent())
			throw new MarcaNomeDuplicadoException(form.getNome());
		
		Marca marca = Marca.from(form.getNome());
		marcaRepository.save(marca);
		
		URI uri = uriBuilder.path("/marcas/{id}").buildAndExpand(marca.getId()).toUri();
		return ResponseEntity.created(uri).body(new MarcaDto(marca));
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeMarcas", allEntries = true)
	public ResponseEntity<MarcaDto> atualizar(@PathVariable Long id, @RequestBody @Valid MarcaForm form) {
		Optional<Marca> optional = marcaRepository.findById(id);
		if (optional.isPresent()) {
			Marca marca = form.atualizar(id, marcaRepository);
			return ResponseEntity.ok(new MarcaDto(marca));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeMarcas", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) throws MarcaVinculadaPatrimonioException {
		
		Optional<Marca> marca = marcaRepository.findById(id);
		Optional<Patrimonio> patrimonio = patrimonioRepository.findByMarcaId(id);
		
		/* Realizar exclusão se existir id de Marca */
		if (marca.isPresent()) {
			/* Verifica se esta marca está vinculada a algum Patrimonio cadastrado */
			if(patrimonio.isPresent()) {
				throw new MarcaVinculadaPatrimonioException(id.toString());
			}
			marcaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
