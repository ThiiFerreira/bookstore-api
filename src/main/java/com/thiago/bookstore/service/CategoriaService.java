package com.thiago.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.thiago.bookstore.domain.Categoria;
import com.thiago.bookstore.dtos.CategoriaDTO;
import com.thiago.bookstore.repositories.CategoriaRepository;
import com.thiago.bookstore.service.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	// faz a busca da categoria pelo id e verifica se a categoria existe
	public Categoria findById(Integer id) {

		Optional<Categoria> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrato! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	//retornar uma lista das categorias
	public List<Categoria> findAll() {
		return repository.findAll();
	}

	// metodo para retornar uma nova categoria
	public Categoria create(Categoria obj) {
		obj.setId(null);
		return repository.save(obj);
	}

	// metodo para retornar a categoria atualizada para a CategoriaResouce
	public Categoria update(Integer id, CategoriaDTO objDto) {
		Categoria obj = findById(id);
		obj.setNome(objDto.getNome());
		obj.setDescricao(objDto.getDescricao());
		return repository.save(obj);
	}

	// metodo para deletar uma categoria
	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new com.thiago.bookstore.service.exceptions.DataIntegrityViolationException(
					"Categoria não pode ser deletada! Possui livros associados");
		}
	}
}
