package org.generation.blogPessoal.Controller;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.Model.TemaModel;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping ("tema")
@CrossOrigin ("*")
public class TemaController {
	@Autowired
	private TemaRepository temaRepository;
	
	@GetMapping
	public ResponseEntity <List<TemaModel>> GetAll(){
		return ResponseEntity.ok(temaRepository.findAll());
		
	}
	
	@GetMapping("/{id}")//ENDPOINT
	public ResponseEntity<TemaModel> GetById(@PathVariable Long id){
		return temaRepository.findById(id)
				.map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}
	//ATRIBUTO / ENDPOINT
	@GetMapping ("/tema/{tema}")//PathVariable pega um caminho dentro da URL
	public ResponseEntity <List<TemaModel>> GetByDescricao(@PathVariable String descricao){ // ResponseEntity é uma lista
		return ResponseEntity.ok(temaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	@PostMapping
	public ResponseEntity <TemaModel> post (@RequestBody TemaModel descricao){ //RequestBody pega o que tem no corpo da requisição
		return ResponseEntity.status(HttpStatus.CREATED).body(temaRepository.save(descricao));	
	}
	@PutMapping
	public ResponseEntity <TemaModel> put (@RequestBody TemaModel descricao){ 
		return ResponseEntity.status(HttpStatus.OK).body(temaRepository.save(descricao));
	}
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping ("/{id}")
	public void delete (@PathVariable Long id) {
	Optional<TemaModel> tema = temaRepository.findById(id);
		if(tema.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	temaRepository.deleteById(id);
	}

}
