package br.com.cbyk.contas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cbyk.contas.domain.model.ContaEntity;

@Repository
public interface ContaRepository extends JpaRepository<ContaEntity, Long> {

}
