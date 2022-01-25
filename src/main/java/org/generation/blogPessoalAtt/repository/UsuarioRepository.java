package org.generation.blogPessoalAtt.repository;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoalAtt.model.usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<usuario, Long>{
	public Optional<usuario> findByUsuario(String usuario);
	
	public Optional<usuario> findByEmail(String email);

	public Optional<usuario> findByToken(String token);
	
	public List<usuario> findAllByNomeContainingIgnoreCase(String nome);
	
	
}
