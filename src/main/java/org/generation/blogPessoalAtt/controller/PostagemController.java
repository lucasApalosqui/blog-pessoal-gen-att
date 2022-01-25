package org.generation.blogPessoalAtt.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generation.blogPessoalAtt.model.postagem;
import org.generation.blogPessoalAtt.model.tema;
import org.generation.blogPessoalAtt.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/v1/postagem")
public class PostagemController {

	@Autowired
	private PostagemRepository repository;

	/** findAllTema */
	@GetMapping("/all")
	public ResponseEntity<List<postagem>> getAll() {
		List<postagem> list = repository.findAll();
		if (list.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(list);
		}
	}

	/** postPostagem */
	@PostMapping("/save")
	public ResponseEntity<postagem> save(@Valid @RequestBody postagem postagem) {
		return ResponseEntity.status(201).body(repository.save(postagem));
	}
	
	/** findByIDPostagem */
	@GetMapping("/{id}")
	public ResponseEntity<postagem> findById(@PathVariable(value = "id") Long id) {
		Optional<postagem> optional = repository.findById(id);

		if (optional.isPresent()) {
			return ResponseEntity.status(200).body(optional.get());
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id não existe!");
		}
	}
	
	/** findByTextoPostagem */
	@GetMapping("/texto/{texto}")
	public ResponseEntity<List<postagem>> GetByTexto (@PathVariable String texto) {
		return ResponseEntity.ok(repository.findAllByTextoContainingIgnoreCase(texto));
	}
	
	/** putPostagem */
	@PutMapping("/update")
	public postagem update(@Valid @RequestBody postagem newPostagem) {
		return repository.save(newPostagem);
	}
	
	/** deletePostagem */
	@DeleteMapping("delete/{id}")
	public void delete(@PathVariable(value = "id")Long id) {
		repository.deleteById(id);
	}
	

}
