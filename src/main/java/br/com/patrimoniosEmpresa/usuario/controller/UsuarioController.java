package br.com.patrimoniosEmpresa.usuario.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.patrimoniosEmpresa.usuario.controller.dto.UsuarioDto;
import br.com.patrimoniosEmpresa.usuario.controller.form.UsuarioForm;
import br.com.patrimoniosEmpresa.usuario.domain.Usuario;
import br.com.patrimoniosEmpresa.usuario.exception.UsuarioEmailDuplicadoException;
import br.com.patrimoniosEmpresa.usuario.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping
	@Cacheable(value = "listaDeUsuarios")
	public Page<UsuarioDto> lista(@PageableDefault(direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
		Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
		return UsuarioDto.buscarUsuarios(usuarios);
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaDeUsuarios", allEntries = true)
	public ResponseEntity<UsuarioDto> cadastrar(@RequestBody @Valid UsuarioForm form, UriComponentsBuilder uriBuilder) throws UsuarioEmailDuplicadoException {
		
		if (this.usuarioRepository.findByEmail(form.getEmail()).isPresent())
			throw new UsuarioEmailDuplicadoException(form.getEmail());
		
		Usuario usuario = Usuario.of(form.getNome(), form.getEmail(), form.getSenha());
		usuarioRepository.save(usuario);
		
		URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeUsuarios", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Usuario> optional = usuarioRepository.findById(id);
		if (optional.isPresent()) {
			usuarioRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
