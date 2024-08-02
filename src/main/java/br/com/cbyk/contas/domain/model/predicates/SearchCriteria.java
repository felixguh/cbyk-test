package br.com.cbyk.contas.domain.model.predicates;

import java.io.Serializable;

import br.com.cbyk.contas.domain.enums.SearchOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria implements Serializable {

	private static final long serialVersionUID = 5490818395602884398L;

	private String key;
	private Object value;
	private SearchOperation operation;

}
