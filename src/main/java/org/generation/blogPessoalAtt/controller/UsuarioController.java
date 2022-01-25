package org.generation.blogPessoalAtt.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generation.blogPessoalAtt.model.usuario;
import org.generation.blogPessoalAtt.model.dtos.UserCredentialsDTO;
import org.generation.blogPessoalAtt.model.dtos.UserLoginDTO;
import org.generation.blogPessoalAtt.model.dtos.UsuarioRegisterDTO;
import org.generation.blogPessoalAtt.repository.UsuarioRepository;
import org.generation.blogPessoalAtt.services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private UsuarioServices services;

	@PostMapping("/save")
	public ResponseEntity<usuario> save(@Valid @RequestBody UsuarioRegisterDTO newUsuario) {
		return services.registerUser(newUsuario);
	}

	@PutMapping("/credentials")
	public ResponseEntity<UserCredentialsDTO> credentials(@Valid @RequestBody UserLoginDTO user) {
		return services.getCredentials(user);
	}

	@GetMapping("/{id}")
	public ResponseEntity<usuario> findById(@PathVariable(value = "id") Long id) {
		Optional<usuario> optional = repository.findById(id);

		if (optional.isPresent()) {
			return ResponseEntity.status(200).body(optional.get());
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id não existe!");
		}
	}

	@GetMapping("/{nome}")
	public ResponseEntity<List<usuario>> findByNome(@PathVariable(value = "nome") String nome) {
		List<usuario> list = repository.findAllByNomeContainingIgnoreCase(nome);

		if (list.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(list);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<List<usuario>> getAll() {
		List<usuario> list = repository.findAll();
		if (list.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(list);
		}
	}

	@GetMapping("/{token}")
	public ResponseEntity<usuario> getProfileByToken(@PathVariable String token) {
		return ResponseEntity.status(200).body(repository.findByToken(token).get());
	}

	@DeleteMapping("delete/{id}")
	public void delete(@PathVariable(value = "id") Long id) {
		repository.deleteById(id);
	}
	
	@PostMapping("/logar") 
	public ResponseEntity<UserLoginDTO> Autentication(@RequestBody Optional<UserLoginDTO> user){
		return services.Logar(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
				
	}

}
