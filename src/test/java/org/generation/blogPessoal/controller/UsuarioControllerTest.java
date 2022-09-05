package org.generation.blogPessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.generation.blogPessoal.Model.UsuarioModel;
import org.generation.blogPessoal.Service.UsuarioService;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance (TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired 
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll 
	void start () {
		usuarioRepository.deleteAll();
		usuarioService.cadastrarUsuarioModel(new UsuarioModel(0L, "Root", "root@root.com","rootroot"," "));	
	}
	@Test
	@DisplayName("Cadastrar Um Usuário")
	public void deveCriarUmUsuario() {
	
		HttpEntity<UsuarioModel> corpoRequisicao= new HttpEntity<UsuarioModel>(new UsuarioModel (0L,
				"Jorge Santos", "jorgesantos@email.com","1093838348039","unhusbdfusbudundoi.jpg"));
		
		ResponseEntity<UsuarioModel> corpoResposta = testRestTemplate
				.exchange("/usuario/cadastrar",HttpMethod.POST,corpoRequisicao, UsuarioModel.class);
	
		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
		assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
		assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());
	}
	@Test
	@DisplayName ("Não deve permitir duplicação do Usuário")
	public void naoDeveDuplicarUsuario() {
		usuarioService.cadastrarUsuarioModel(new UsuarioModel(0L,"Xuxa Menegel", "xuxa@email.coml", "12345678","nidhiddsidn.jpg"));
		
		HttpEntity<UsuarioModel> corpoRequisicao= new HttpEntity<UsuarioModel>(new UsuarioModel (0L,"Xuxa Menegel", "xuxa@email.coml", "12345678","nidhiddsidn.jpg"));
		
		ResponseEntity<UsuarioModel> corpoResposta = testRestTemplate
				.exchange("/usuario/cadastrar",HttpMethod.POST,corpoRequisicao, UsuarioModel.class);
		assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
	}
	@Test
	@DisplayName("Atualizar um Usuario")
	public void deveAtualizarUmUsuario() {
		
		Optional<UsuarioModel>usuarioCadastrado = usuarioService.cadastrarUsuarioModel(new UsuarioModel(0L,
				"Maria da Silva", "maria_silva@email.com.br", "13465278", "https://i.imgur.com/T12NIp9.jpg"));
		
		UsuarioModel usuarioUpdate = new UsuarioModel(usuarioCadastrado.get().getId(),
				"Maria da Silva", "maria_silva@email.com.br", "13465278", "https://i.imgur.com/T12NIp9.jpg");
		
		HttpEntity<UsuarioModel> corpoRequisicao= new HttpEntity<UsuarioModel>(usuarioUpdate);
		
		ResponseEntity<UsuarioModel> corpoResposta = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot" )
				.exchange("/usuario/atualizar",HttpMethod.PUT,corpoRequisicao, UsuarioModel.class);
		
		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
		assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
		assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());	
	}
	@Test
	@DisplayName("Listar todos os Usuarios")
	public void deveMostrarTodosUsuarios() {
		
		usuarioService.cadastrarUsuarioModel(new UsuarioModel(0L,
				"Fabiana Santos","fabianasantos@email.com", "9817863792", "uubyedbciesiknd.jpg"));
		usuarioService.cadastrarUsuarioModel(new UsuarioModel(0L,
				"Pedro José", "pedrojose@email.com", "092483485","hfndubvusbdubn.jpg"));
		
		ResponseEntity<String> resposta = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/usuario/all",HttpMethod.GET,null, String.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	
}