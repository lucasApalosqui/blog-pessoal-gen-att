package org.generation.blogPessoal.services;

import java.nio.charset.Charset;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.generation.blogPessoal.model.usuario;
import org.generation.blogPessoal.model.dtos.UserCredentialsDTO;
import org.generation.blogPessoal.model.dtos.UserLoginDTO;
import org.generation.blogPessoal.model.dtos.UsuarioRegisterDTO;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioServices {
	private @Autowired UsuarioRepository repository;
	private UserCredentialsDTO credentials;

	private static String criptoPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);

	}

	private static String generatorToken(String email, String senha) {
		String structure = email + ":" + senha; // lucas@email.com:123456
		byte[] structureBase64 = Base64.encodeBase64(structure.getBytes(Charset.forName("US-ASCII")));
		return new String(structureBase64);
	}

	private static String generatorTokenBasic(String email, String senha) {
		String structure = email + ":" + senha; // lucas@email.com:123456
		byte[] structureBase64 = Base64.encodeBase64(structure.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(structureBase64);
		
	}

	public ResponseEntity<usuario> registerUser(@Valid UsuarioRegisterDTO newUsuario) {
		Optional<usuario> optional = repository.findByEmail(newUsuario.getEmail());

		if (optional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario já existente, cadastre com outro email");
		} else {

			usuario user = new usuario();

			user.setUsuario(newUsuario.getUsuario());
			user.setEmail(newUsuario.getEmail());
			user.setToken(generatorToken(newUsuario.getEmail(), newUsuario.getSenha()));
			user.setSenha(criptoPassword(newUsuario.getSenha()));
			user.setTokenBasic(generatorTokenBasic(newUsuario.getEmail(), newUsuario.getSenha()));
			user.setNome(newUsuario.getNome());
			return ResponseEntity.status(201).body(repository.save(user));
		}
	}

	public ResponseEntity<UserCredentialsDTO> getCredentials(@Valid UserLoginDTO userDto) {
		return repository.findByEmail(userDto.getEmail()).map(resp -> {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			if (encoder.matches(userDto.getSenha(), resp.getSenha())) {
				credentials = new UserCredentialsDTO();
				credentials.setId(resp.getId());
				credentials.setEmail(resp.getEmail());
				credentials.setToken(resp.getToken());
				credentials.setTokenBasic(generatorTokenBasic(userDto.getEmail(), userDto.getSenha()));

				return ResponseEntity.status(200).body(credentials);

			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha incorreta");
			}

		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email, não registrado no sistema"));
	}

	
	public Optional<UserLoginDTO> Logar(Optional<UserLoginDTO> user){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<usuario> usuario = repository.findByUsuario(user.get().getUsuario());
		
		if(usuario.isPresent()) {
			if(encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {
				
				String auth = user.get().getUsuario() + ":" + user.get().getSenha();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "basic " + new String(encodedAuth);
				
				user.get().setToken(authHeader);
				user.get().setId(usuario.get().getId());
				user.get().setNome(usuario.get().getNome());
				user.get().setFoto(usuario.get().getFoto());
				user.get().setTipo(usuario.get().getTipo());
				
				
				
				return user;
			}
		}
		return null;
				
		
		
	}
}
