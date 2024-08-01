package br.com.cbyk.contas.domain.model.predicates;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.com.cbyk.contas.domain.enums.SearchOperation;
import br.com.cbyk.contas.domain.model.ContaEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ContaSpecification implements Specification<ContaEntity> {

	private static final long serialVersionUID = -3424290178284809742L;

	private List<SearchCriteria> list;

	public ContaSpecification() {
		this.list = new ArrayList<>();
	}

	public ContaSpecification(List<SearchCriteria> list) {
		this.list = new ArrayList<>();

		if ((list != null) && (!list.isEmpty())) {
			this.list.addAll(list);
		}
	}

	public void add(SearchCriteria criteria) {
		list.add(criteria);
	}

	public List<SearchCriteria> getCriterias() {
		return this.list;
	}

	@Override
	public Predicate toPredicate(Root<ContaEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Predicate> predicates = new ArrayList<>();

		// add criteria to predicate
		for (SearchCriteria criteria : list) {
			if ((criteria.getValue() instanceof LocalDate)) {

				if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
					predicates.add(builder.greaterThan(root.get(criteria.getKey()), (LocalDate) criteria.getValue()));
				} else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
					predicates.add(builder.lessThan(root.get(criteria.getKey()), (LocalDate) criteria.getValue()));
				} else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
					predicates.add(
							builder.greaterThanOrEqualTo(root.get(criteria.getKey()), (LocalDate) criteria.getValue()));
				} else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
					predicates.add(
							builder.lessThanOrEqualTo(root.get(criteria.getKey()), (LocalDate) criteria.getValue()));
				} else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
					predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
				}
			} else {
				if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
					predicates.add(builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
				} else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
					predicates.add(builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
				} else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
					predicates.add(
							builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
				} else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
					predicates.add(
							builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
				} else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
					predicates.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
				} else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
					predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
				} else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
					predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
							"%" + criteria.getValue().toString().toLowerCase() + "%"));
				} else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
					predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
							criteria.getValue().toString().toLowerCase() + "%"));
				} else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
					predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
							"%" + criteria.getValue().toString().toLowerCase()));
				} else if (criteria.getOperation().equals(SearchOperation.IN)) {
					predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
				} else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
					predicates.add(builder.not(root.get(criteria.getKey())).in(criteria.getValue()));
				}
			}
		}

		return builder.and(predicates.toArray(new Predicate[0]));
	}

}
