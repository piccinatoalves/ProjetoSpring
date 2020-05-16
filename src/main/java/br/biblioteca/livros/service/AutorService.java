package br.biblioteca.livros.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.biblioteca.livros.model.Autor;
import br.biblioteca.livros.repository.AutorRepository;

@Service
public class AutorService {

	@Autowired
	AutorRepository autorRepository;
	
	public List<Autor> listarTodosAutores() {
		return autorRepository.findAll();
	}
	
	public void salvar(Autor autor) {
		autorRepository.save(autor);
	}
	
	public Autor buscaAutor(Long id) {
		return autorRepository.findById(id).orElseThrow(() -> new RuntimeException());
	}
	
	public void excluirAutor(Long id) {
		autorRepository.deleteById(id);
	}
}