package com.mesaverde.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mesaverde.entity.ClienteEntity;
import com.mesaverde.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClienteService {
	
	@Autowired 
	private ClienteRepository clienteRepository;
	//guardar cliente
	public void createCliente(ClienteEntity cliente) {
		clienteRepository.save(cliente);
		
	}
	//listar todo
	public List<ClienteEntity> getListCliente(){
		return clienteRepository.findAll();
	}
	//buscar al cliente
	public ClienteEntity getBuscarCliente(Long id) {
		return clienteRepository.findById(id).get();
	}
	//actualizar cliente
	public ClienteEntity updateCliente( ClienteEntity cliente) {
		return clienteRepository.save(cliente);
	}
	//registrar
	public ClienteEntity registrarCliente(ClienteEntity cliente) {
		return clienteRepository.save(cliente);
	}
	//eliminar cliente
	public void deleteCliente(Long id) {
		clienteRepository.deleteById(id);
	}
}

