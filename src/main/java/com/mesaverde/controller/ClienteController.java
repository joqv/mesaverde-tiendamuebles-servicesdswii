package com.mesaverde.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mesaverde.entity.ClienteEntity;
import com.mesaverde.service.ClienteService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/cliente")
@AllArgsConstructor
public class ClienteController {



	@Autowired
	private ClienteService service;
	@GetMapping("/listartodo")
	public List<ClienteEntity> getListCliente(){
		return service.getListCliente();
	}
	
	@GetMapping("/buscar/{clienteId}")
	public ClienteEntity getBuscarCliente(@PathVariable Long clienteId) {
		return service.getBuscarCliente(clienteId);
	}
	@PutMapping("/actualizar")
	public ClienteEntity updateCliente(@RequestBody ClienteEntity cliente) {
		return service.updateCliente(cliente);
	}
	@DeleteMapping("/{clienteId}")
	public void deleteCliente (@PathVariable Long clienteId) {
		service.deleteCliente(clienteId);
	}
	@PostMapping("/registrar")
	public ClienteEntity registarCliente(@RequestBody ClienteEntity cliente) {
		return service.registrarCliente(cliente);
	}
	
}
