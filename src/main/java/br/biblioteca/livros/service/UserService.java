package br.biblioteca.livros.service;

import java.util.List;

import org.springframework.stereotype.Service;

import  br.biblioteca.livros.model.User;
@Service
public interface UserService {
	
	void save(User user);

	User findByUsername(String username);

	List<User> findAll();
	
}