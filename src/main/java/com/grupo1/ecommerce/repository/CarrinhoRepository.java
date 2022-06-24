package com.grupo1.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.grupo1.ecommerce.model.Carrinho;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long>{
	
	public List<Carrinho>findAllByStatusContainingIgnoreCase(@Param("status")String status);

	
}
