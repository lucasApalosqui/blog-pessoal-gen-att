package org.generation.blogPessoalAtt.repository;

import java.util.List;

import org.generation.blogPessoalAtt.model.postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostagemRepository extends JpaRepository <postagem, Long>{
	public List<postagem> findAllByTextoContainingIgnoreCase(String texto);
	
}