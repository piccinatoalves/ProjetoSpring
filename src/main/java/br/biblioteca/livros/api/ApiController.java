package br.biblioteca.livros.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.biblioteca.livros.conversor.LivroConverter;
import br.biblioteca.livros.dto.AvaliacaoDTO;
import br.biblioteca.livros.dto.LivroDTO;
import br.biblioteca.livros.facade.ApiFacade;
import br.biblioteca.livros.model.Livro;
import br.biblioteca.livros.service.LivrosService;

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	LivrosService livrosService;
	
	@Autowired
	ApiFacade apiController;
	
	@GetMapping("/livros/list")
	public ResponseEntity<List<LivroDTO>> livros() {
		
		List<Livro> listaLivros = livrosService.listaTodosLivros();
		return ResponseEntity.ok(LivroConverter.toDTO(listaLivros));
	}
	
	@PostMapping("/livro/avaliacao/{id}")
	public ResponseEntity<Long> comentario(@PathVariable("id") Long id, @RequestBody AvaliacaoDTO avaliacaoDTO) {

		try {
			return ResponseEntity.ok(apiController.salvarAvaliacao(id, avaliacaoDTO));		
		}catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
		
		
	}
}
