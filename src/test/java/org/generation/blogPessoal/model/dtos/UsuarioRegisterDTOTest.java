package org.generation.blogPessoal.model.dtos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.generation.blogPessoalAtt.model.dtos.UsuarioRegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UsuarioRegisterDTOTest {
	
	private UsuarioRegisterDTO dto1; UsuarioRegisterDTO dto2;
	private @Autowired ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();
	
	@BeforeEach
	public void start() {
		dto1 = new UsuarioRegisterDTO("", "bianca@email.com", "bibi123");
		dto2 = new UsuarioRegisterDTO("qwe","biancaemail.com","bibi123");
		
	}
 
	@Test
	void testUsuarioRegisterDTOWithNameNullReturnException() {
		
		Set<ConstraintViolation<UsuarioRegisterDTO>> validation =  validator.validate(dto1);
		
		assertFalse(validation.isEmpty());
	}
	
	@Test
	void testUsuarioRegisterDTOWithEmailOfOutRangeReturnException() {
		Set<ConstraintViolation<UsuarioRegisterDTO>> validation2 =  validator.validate(dto2);
		
		assertFalse(validation2.isEmpty());
	}

}
