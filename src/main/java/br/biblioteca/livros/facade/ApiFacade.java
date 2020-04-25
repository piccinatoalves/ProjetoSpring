package br.biblioteca.livros.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.biblioteca.livros.conversor.AvaliacaoConverter;
import br.biblioteca.livros.dto.AvaliacaoDTO;
import br.biblioteca.livros.model.Avaliacao;
import br.biblioteca.livros.model.Livro;
import br.biblioteca.livros.service.AvaliacaoService;
import br.biblioteca.livros.service.LivrosService;

@Service
public class ApiFacade {
	
	@Autowired
	LivrosService livroService;

	@Autowired
	AvaliacaoService avaliacaoService;
	
	public List<Livro> listaTodosLivros(){
		return livroService.listaTodosLivros();
	}
	
	
	public Long salvarAvaliacao(Long idLivro, AvaliacaoDTO avaliacaoDTO) {
		Livro livro = livroService.buscaLivro(idLivro);
		
		Avaliacao avaliacao = AvaliacaoConverter.toModel(avaliacaoDTO, livro);
		avaliacaoService.salvaAvaliacao(avaliacao);
		return 1l;
	}
	
	

}
