package com.Kee.V2C.specifications;

import com.Kee.V2C.dto.VendorProfileResponse;
import com.Kee.V2C.entity.Credential;
import com.Kee.V2C.entity.Vendor;
import com.Kee.V2C.enums.UserStatus;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public final class VendorSpecs {

    private VendorSpecs(){}

    public static Specification<Vendor> hasName(String name){
        return (root, query, criteriaBuilder) -> {
          if(name==null || name.isEmpty())return null;
          return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%"+name+"%");
        };
    }

    public static Specification<Vendor> hasDescription(String description){
        return (root, query, criteriaBuilder) -> {
            if(description==null || description.isEmpty())return null;
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),"%"+description+"%");
        };
    }

    public static Specification<Vendor> hasAddress(String address){
        return (root, query, criteriaBuilder) -> {
            if(address==null || address.isEmpty())return null;
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("address")),"%"+address+"%");
        };
    }

    public static Specification<Vendor> hasRatingBetween(Float lowerRating,Float higherRating){
        return (root, query, criteriaBuilder) -> {
            if(lowerRating==null && higherRating==null) return null;
            if(lowerRating!=null &&higherRating==null){
                return criteriaBuilder.between(root.get("rating"),lowerRating,5f);
            }
            if(lowerRating==null &&higherRating!=null){
                return criteriaBuilder.between(root.get("rating"),0f,higherRating);
            }
            return criteriaBuilder.between(root.get("rating"),lowerRating,higherRating);
        };
    }

    public static Specification<Vendor> hasStatus(UserStatus status){
        return (root, query, criteriaBuilder) -> {
            if(status==null ) return null;
            Join<Vendor, Credential> vendorCredentialJoin=root.join("credential");
            return criteriaBuilder.equal(vendorCredentialJoin.get("status"),status);
        };

    }

    public static Specification<Vendor> fetchCredentials(){
        return (root, query, criteriaBuilder) -> {

            Class<?> resultType=query.getResultType();
            if(resultType!=Long.class && resultType!=long.class){
                root.fetch("credential", JoinType.LEFT);
            }
            return null;
        };
    }




}
