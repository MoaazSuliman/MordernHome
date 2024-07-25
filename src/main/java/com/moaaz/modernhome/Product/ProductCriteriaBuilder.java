package com.moaaz.modernhome.Product;

import com.moaaz.modernhome.Category.Category;
import com.moaaz.modernhome.repository.CriteriaService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import static com.moaaz.modernhome.Validation.CustomValidation.nullChecker;

public class ProductCriteriaBuilder {

	public static Specification<Product> getProductPredicate(ProductSearch productSearch) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (nullChecker(productSearch.getName())) {
				String text = CriteriaService.getSearchText(productSearch.getName());
				Predicate namePredicate = criteriaBuilder.like(root.get("name"), text);
				predicates.add(namePredicate);
			}


			if (nullChecker(productSearch.getPriceLessThan())) {
				Predicate priceLessThanPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("price"), productSearch.getPriceLessThan());
				predicates.add(priceLessThanPredicate);
			}

			if (nullChecker(productSearch.getPriceGreaterThan())) {
				Predicate priceGreaterThanPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), productSearch.getPriceGreaterThan());
				predicates.add(priceGreaterThanPredicate);
			}

			if (nullChecker(productSearch.getLastNumberOfDays())) {
				LocalDate lastDate = LocalDate.now().minusDays(productSearch.getLastNumberOfDays());
				Predicate previousNumberOfDaysPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("creationDate"), lastDate);
				predicates.add(previousNumberOfDaysPredicate);
			}

			if (nullChecker(productSearch.getCategoryId())) {
				Join<Product, Category> productCategoryJoin = root.join("category");
				Predicate categoryPredicate = criteriaBuilder.equal(productCategoryJoin.get("id"), productSearch.getCategoryId());
				predicates.add(categoryPredicate);
			}

			if (nullChecker(productSearch.getSort()) && nullChecker(productSearch.getSortBy())) {
				if (productSearch.getSort() == ProductSort.ASC) {
					query.orderBy(criteriaBuilder.asc(root.get(productSearch.getSortBy().name())));
				} else {
					query.orderBy(criteriaBuilder.desc(root.get(productSearch.getSortBy().name())));
				}
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
