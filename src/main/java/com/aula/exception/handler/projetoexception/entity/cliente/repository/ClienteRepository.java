package com.aula.exception.handler.projetoexception.entity.cliente.repository;

import com.aula.exception.handler.projetoexception.entity.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByNome(String nome);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}
