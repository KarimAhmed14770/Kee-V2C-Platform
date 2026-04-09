package com.Kee.V2C.specifications;

import com.Kee.V2C.entity.Credential;
import com.Kee.V2C.entity.Customer;
import com.Kee.V2C.enums.UserStatus;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public final class CustomerSpecs {//final keyword to prevent extending of this class

    private CustomerSpecs(){}

    //logic for searching by firstName
    public static Specification<Customer> hasFirstName(String name){
        return (root, query, criteriaBuilder) ->
        {
            if (name == null || name.isEmpty()) return null;
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),
            name.toLowerCase()+"%");
        };
    }

    public static Specification<Customer> hasLastName(String name){
        return (root, query, criteriaBuilder) -> {
            if(name==null || name.isEmpty())return null;
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")),
                    name.toLowerCase()+"%");
        };
    }

    public static Specification<Customer> hasAddress(String address){
        return (root, query, criteriaBuilder) -> {
            if(address==null || address.isEmpty())return null;
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("shippingAddress")),"%"+
                    address.toLowerCase()+"%");
        };
    }

    public static Specification<Customer> hasUserName(String userName){
        return (root, query, criteriaBuilder) -> {
            if(userName==null || userName.isEmpty()) return null;
            Join<Customer, Credential> customerCredentialJoin=root.join("credential");
            return criteriaBuilder.equal(customerCredentialJoin.get("userName"),userName);
        };
    }

    public static Specification<Customer> hasEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            if (email == null || email.isEmpty()) return null;
            Join<Customer, Credential> customerCredentialJoin = root.join("credential");
            return criteriaBuilder.equal(customerCredentialJoin.get("email"), email);
        };
    }

        public static Specification<Customer> hasStatus(UserStatus status) {
            return (root, query, criteriaBuilder) -> {
                if (status == null) return null;
                Join<Customer, Credential> customerCredentialJoin = root.join("credential");
                return criteriaBuilder.equal(customerCredentialJoin.get("status"), status);
            };
        }
        public static Specification<Customer> fetchCredential() {
                return (root, query, criteriaBuilder) -> {

                    Class<?> resultType=query.getResultType();
                    if(resultType!=Long.class && resultType!=long.class){
                        root.fetch("credential", JoinType.LEFT);
                    }
                    return null;
                };

}





}
