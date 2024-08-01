package br.com.cbyk.contas.domain.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cbyk.contas.domain.model.ContaEntity;

@Repository
public interface ContaRepository extends JpaRepository<ContaEntity, Long> {

	Optional<ContaEntity> findById(Long id);

	Page<ContaEntity> findAll(Specification<ContaEntity> specification, Pageable pageable);

	@Query("SELECT SUM(v.valor) FROM ContaEntity v WHERE v.dataVencimento BETWEEN :dataInicio AND :dataFim")
	BigDecimal findTotalByDateRange(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);

}
