package org.generation.blogPessoal.repository;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.model.tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemaRepository extends JpaRepository< tema, Long>{
	public List<tema> findAllByDescricaoContainingIgnoreCase(String descricao);
	
	Optional<tema> findByDescricao(String descricao);
}
