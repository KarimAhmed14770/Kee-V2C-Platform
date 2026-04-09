package com.Kee.V2C.specifications;

import com.Kee.V2C.Repository.CategoryRepository;
import com.Kee.V2C.entity.Category;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public final class CategorySpecs {
    private CategorySpecs(){}

    public static Specification<Category> hasName(String name){
        return (root, query, criteriaBuilder) -> {
          if(name==null ||name.isEmpty()) return null;
          return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%"+name+"%" );
        };
    }
    public static Specification<Category> hasDescription(String description){
        return (root, query, criteriaBuilder) -> {
            if(description==null ||description.isEmpty()) return null;
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),"%"+description+"%" );
        };
    }
    public static Specification<Category> hasActive(Boolean active){
        return (root, query, criteriaBuilder) -> {
            if(active==null) return null;
            return criteriaBuilder.equal(root.get("active"),active );
        };
    }
}
