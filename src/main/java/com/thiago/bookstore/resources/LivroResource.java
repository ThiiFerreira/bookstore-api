package com.thiago.bookstore.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.thiago.bookstore.domain.Livro;
import com.thiago.bookstore.dtos.LivroDTO;
import com.thiago.bookstore.service.LivroService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/livros")
public class LivroResource {

	@Autowired
	private LivroService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Livro> findById(@PathVariable Integer id) {
		Livro obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	// listagem dos livros por cartegoria
	@GetMapping
	public ResponseEntity<List<LivroDTO>> findAll(
			@RequestParam(value = "categoria", defaultValue = "0") Integer id_cat) {
		List<Livro> list = service.findAll(id_cat);
		List<LivroDTO> listDTO = list.stream().map(obj -> new LivroDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	// atualização dos dados do livro
	@PutMapping(value = "/{id}")
	public ResponseEntity<Livro> update(@PathVariable Integer id, @Valid @RequestBody Livro obj) {
		Livro newObj = service.update(id, obj);
		return ResponseEntity.ok().body(newObj);
	}

	// atualização de um dado unico do livro
	@PatchMapping(value = "/{id}")
	public ResponseEntity<Livro> updatePatch(@PathVariable Integer id, @Valid @RequestBody Livro obj) {
		Livro newObj = service.update(id, obj);
		return ResponseEntity.ok().body(newObj);
	}

	// criando um novo livro
	@PostMapping
	public ResponseEntity<Livro> create(@RequestParam(value = "categoria", defaultValue = "0") Integer id_cat,
			@Valid @RequestBody Livro obj) {
		Livro newObj = service.create(id_cat, obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/livros/{id}")
				.buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();

	}

	// metodo para exluir livro
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
