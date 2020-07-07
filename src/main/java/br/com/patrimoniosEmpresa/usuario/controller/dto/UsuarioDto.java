package br.com.patrimoniosEmpresa.usuario.controller.dto;

import org.springframework.data.domain.Page;

import br.com.patrimoniosEmpresa.usuario.domain.Usuario;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@ApiModel(description = "Informações para cadastro de um novo usuário.")
public class UsuarioDto {

	private Long id;
	private String nome;
	private String email;
	private String senha;

	public UsuarioDto(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.senha = usuario.getSenha();
	}

	public static Page<UsuarioDto> buscarUsuarios(Page<Usuario> usuarios) {
		return usuarios.map(UsuarioDto::new);  //.map(UsuarioDto::new);
	}
	
}
