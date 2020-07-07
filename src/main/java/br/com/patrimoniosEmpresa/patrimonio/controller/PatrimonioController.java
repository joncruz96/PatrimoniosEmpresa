package br.com.patrimoniosEmpresa.patrimonio.controller;

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

import br.com.patrimoniosEmpresa.marca.repository.MarcaRepository;
import br.com.patrimoniosEmpresa.patrimonio.controller.dto.PatrimonioDto;
import br.com.patrimoniosEmpresa.patrimonio.controller.form.PatrimonioForm;
import br.com.patrimoniosEmpresa.patrimonio.domain.Patrimonio;
import br.com.patrimoniosEmpresa.patrimonio.domain.RandomString;
import br.com.patrimoniosEmpresa.patrimonio.exception.PatrimonioMarcaIdNaoExisteException;
import br.com.patrimoniosEmpresa.patrimonio.repository.PatrimonioRepository;

@RestController
@RequestMapping("/patrimonios")
public class PatrimonioController {

	@Autowired
	private PatrimonioRepository patrimonioRepository;
	
	@Autowired
	private MarcaRepository marcaRepository;
	
	@GetMapping
	@Cacheable(value = "listaDePatrimonios")
	public Page<PatrimonioDto> lista(@PageableDefault(direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
		Page<Patrimonio> patrimonios = patrimonioRepository.findAll(paginacao);
		return PatrimonioDto.buscarPatrimonios(patrimonios);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PatrimonioDto> detalhar( @PathVariable Long id ) {
		
		Optional<Patrimonio> patrimonio = patrimonioRepository.findById(id);
		if (patrimonio.isPresent()) {
			return ResponseEntity.ok(new PatrimonioDto(patrimonio.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaDePatrimonios", allEntries = true)
	public ResponseEntity<PatrimonioDto> cadastrar(@RequestBody @Valid PatrimonioForm form, UriComponentsBuilder uriBuilder) throws PatrimonioMarcaIdNaoExisteException {
		
		String numTombo;
		
		/* Se não encontrar MarcaId na tabela Marca, não deve criar Patrimonio */
		if (!this.marcaRepository.findById(form.getMarcaId()).isPresent())
			throw new PatrimonioMarcaIdNaoExisteException(form.getMarcaId());
		
		/* Se já existir numTombo cadastrado, gera um novo numero aleatorio */
		do{
			numTombo = new RandomString().nextString();
		}while(this.patrimonioRepository.findByNumTombo(numTombo).isPresent());
		
		Patrimonio patrimonio = Patrimonio.builder()
										  .nome(form.getNome())
										  .marcaId(form.getMarcaId())
										  .descricao(form.getDescricao())
										  .numTombo(numTombo)
										  .build();
		patrimonioRepository.save(patrimonio);
		
		URI uri = uriBuilder.path("/patrimonios/{id}").buildAndExpand(patrimonio.getId()).toUri();
		return ResponseEntity.created(uri).body(new PatrimonioDto(patrimonio));
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDePatrimonios", allEntries = true)
	public ResponseEntity<PatrimonioDto> atualizar(@PathVariable Long id, @RequestBody @Valid PatrimonioForm form) throws PatrimonioMarcaIdNaoExisteException {
		
		/* Se não encontrar MarcaId na tabela Marca, não deve criar Patrimonio */
		if (!this.marcaRepository.findById(form.getMarcaId()).isPresent())
			throw new PatrimonioMarcaIdNaoExisteException(form.getMarcaId());
		
		Optional<Patrimonio> optional = patrimonioRepository.findById(id);
		if (optional.isPresent()) {
			Patrimonio patrimonio = form.atualizar(id, patrimonioRepository);
			return ResponseEntity.ok(new PatrimonioDto(patrimonio));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDePatrimonios", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		
		
		
		Optional<Patrimonio> optional = patrimonioRepository.findById(id);
		if (optional.isPresent()) {
			patrimonioRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
