package br.com.patrimoniosEmpresa.usuario.controller.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data(staticConstructor = "of")
public class UsuarioForm {

	@NotNull @NotEmpty
	private String nome;
	
	@NotNull @NotEmpty @Email
	private String email;
	
	@NotNull @NotEmpty @Length(min = 8)
	private String senha;
	
}
