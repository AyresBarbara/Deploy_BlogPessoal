package org.generation.blogPessoal.Model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity //uma entidade do jpa
@Table (name = "tema") // 
public class TemaModel {
	
	@Id //id BIGINT AUTO_INCREMENT
	@GeneratedValue (strategy =GenerationType.IDENTITY)
	private Long id;
	
	//ATRIBUTOS DA TABELA
	@NotBlank (message = "O atributo descrição é obrigatorio e não pode utilizar espaço em branco")
	private String descricao;
	
	@OneToMany (mappedBy = "tema", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("tema")
	private List <Postagem> postagem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Postagem> getPostagem() {
		return postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}
	
	

	}
