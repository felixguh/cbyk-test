package br.com.cbyk.contas.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.cbyk.contas.application.mapper.ContaMapper;
import br.com.cbyk.contas.application.response.ContaResponse;
import br.com.cbyk.contas.domain.model.ContaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class PesquisaContaRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public Page<ContaResponse> search(LocalDate dataVencimento, String descricao, Pageable pageable) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<ContaEntity> cq = cb.createQuery(ContaEntity.class);

		Root<ContaEntity> root = cq.from(ContaEntity.class);

		Predicate predicate = cb.conjunction();

		if (dataVencimento != null) {
			predicate = cb.and(predicate, cb.equal(root.get("data_vencimento"), dataVencimento));
		}

		if (descricao != null && !descricao.isEmpty()) {
			predicate = cb.and(predicate, cb.like(root.get("descricao"), "%" + descricao + "%"));
		}

		cq.where(predicate);
		cq.orderBy(cb.asc(root.get("id")));

		List<ContaEntity> entities = entityManager.createQuery(cq).setFirstResult((int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize()).getResultList();

		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);

		Root<ContaEntity> countRoot = countQuery.from(ContaEntity.class);

		countQuery.select(cb.count(countRoot)).where(predicate);

		Long total = entityManager.createQuery(countQuery).getSingleResult();

		List<ContaResponse> response = entities.stream().map(ContaMapper::toResponse).toList();

		return new PageImpl<>(response, pageable, total);
	}
}
