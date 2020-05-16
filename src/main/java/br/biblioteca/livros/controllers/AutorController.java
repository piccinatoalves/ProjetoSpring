package br.biblioteca.livros.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.biblioteca.livros.model.Autor;
import br.biblioteca.livros.service.AutorService;

@Controller
@RequestMapping("autores")
public class AutorController {

	@Autowired
	AutorService autorService;
	
	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView("autor/list");
		List<Autor> listaAutores = autorService.listarTodosAutores();
		modelAndView.addObject("autores", listaAutores);
		
		return modelAndView;
	}
	
	@GetMapping("/new")
	public ModelAndView add(@ModelAttribute Autor autor) {		
		ModelAndView modelAndView = new ModelAndView("autor/form");		
		return modelAndView;
	}
	
	@PostMapping(value = "/create")
	public ModelAndView create(Autor autor) {
		autorService.salvar(autor);
		
		return new ModelAndView("redirect:/autores/list");
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		Autor autor = autorService.buscaAutor(id);
		
		ModelAndView modelAndView = new ModelAndView("autor/form");
		modelAndView.addObject("autor", autor);
		return modelAndView;
	}
	
	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		autorService.excluirAutor(id);
		
		return new ModelAndView("redirect:/autores/list");
	}
}