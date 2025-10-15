package com.mesaverde.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mesaverde.entity.ClienteEntity;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity,Long>{
	

}
