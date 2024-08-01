package br.com.cbyk.contas.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cbyk.contas.domain.model.ContaEntity;

@Repository
public interface ContaRepository extends JpaRepository<ContaEntity, Long> {

	Optional<ContaEntity> findById(Long id);

	Page<ContaEntity> findAll(Specification<ContaEntity> specification, Pageable pageable);

}
