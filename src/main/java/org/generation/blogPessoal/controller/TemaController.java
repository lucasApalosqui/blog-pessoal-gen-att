package org.generation.blogPessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generation.blogPessoal.model.tema;
import org.generation.blogPessoal.repository.TemaRepository;
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
@RequestMapping("/api/v1/tema")
public class TemaController {

	@Autowired
	private TemaRepository repository;

	/** findAllTema */
	@GetMapping("/all")
	public ResponseEntity<List<tema>> getAll() {
		List<tema> list = repository.findAll();
		if (list.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(list);
		}
	}

	/** findByIDTema */
	@GetMapping("/{id}")
	public ResponseEntity<tema> findById(@PathVariable(value = "id") Long id) {
		Optional<tema> optional = repository.findById(id);

		if (optional.isPresent()) {
			return ResponseEntity.status(200).body(optional.get());
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id não existe!");
		}
	}

	/** findByDescricaoPostagem */
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<tema>> GetByTitulo (@PathVariable String descricao) {
		return ResponseEntity.ok(repository.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	
	/** postTema */
	@PostMapping("/save")
	public ResponseEntity<tema> save(@Valid @RequestBody tema tema) {
		return ResponseEntity.status(201).body(repository.save(tema));
	}
	
	/** putTema */
	@PutMapping("/update")
	public tema update(@Valid @RequestBody tema newTema) {
		return repository.save(newTema);
	}
	
	/** deleteTema */
	@DeleteMapping("delete/{id}")
	public void delete(@PathVariable(value = "id")Long id) {
		repository.deleteById(id);
	}
	
}
