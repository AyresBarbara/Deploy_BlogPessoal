package org.generation.blogPessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.Model.UsuarioModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		usuarioRepository.deleteAll();
		usuarioRepository.save(new UsuarioModel (0L, "Xuxa Menegel", "xuxa@email.coml", "12345678","nidhiddsidn.jpg"));
		usuarioRepository.save(new UsuarioModel (0L, "Julio Silva", "juliocezar@email.aom", "12312345","edchbasdhyba.jpg"));
		usuarioRepository.save(new UsuarioModel (0L, "Addonis Ayres", "addonisayres@email.com", "345565678","kiiajsbudadc.jpg"));
		usuarioRepository.save(new UsuarioModel (0L, "Robson Silva", "robsonkamila@email.com", "145675678","pqhnsgdhjsdfdv.jpg"));

	}
	
	@Test
	@DisplayName ("Retorna 1 usuario")
	public void deveRetornarUmUsuario() {
		Optional <UsuarioModel>usuario = usuarioRepository.findByUsuario("xuxa@email.com");
		assertTrue(usuario.get().getUsuario().equals("xuxa@email.com"));
	}
	@Test
	@DisplayName ("Retorna 3 usuario")
	public void deveRetornarTresUsuario() {
		List<UsuarioModel> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
		assertEquals(3,listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("Xuxa Menegel"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Julio Silva"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Addonis Ayres"));
	}
	@AfterAll
	public void end() {
		usuarioRepository.deleteAll();
	}
}