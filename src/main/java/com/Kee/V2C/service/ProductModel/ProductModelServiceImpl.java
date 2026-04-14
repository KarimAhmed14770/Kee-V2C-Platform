package com.Kee.V2C.service.ProductModel;

import com.Kee.V2C.Repository.ProductModelRepository;
import com.Kee.V2C.dto.product.ProductModelResponse;
import com.Kee.V2C.entity.ProductModel;
import com.Kee.V2C.enums.ProductModelStatus;
import com.Kee.V2C.exception.ResourceNotFoundException;
import com.Kee.V2C.specifications.ProductModelSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ProductModelServiceImpl implements ProductModelService{

    private final ProductModelRepository productModelRepository;

    @Autowired
    public ProductModelServiceImpl(ProductModelRepository productModelRepository){
        this.productModelRepository=productModelRepository;
    }

    @Override
    public ProductModel getProductModelById(Long id){
        return productModelRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("product model with id: "+id+" not found."));
    }

    @Override
    public Page<ProductModel> getActiveProductModels(Pageable page){
        return getProductModelsByAttributes(null,null,null,null,null
        ,null,ProductModelStatus.ACTIVE,page);
    }

    @Override
    public Page<ProductModel> getProductModelsByAttributes(String name, String description,
                                                    Long ownerId, Long subCategoryId, Long brandId,
                                                    Boolean isGlobal, ProductModelStatus status,
                                                    Pageable page){
        Specification<ProductModel> spec=Specification
                .where((root, query, cb) -> cb.conjunction() );

        if(name!=null && !(name.isEmpty()))spec=spec.and(ProductModelSpecs.hasName(name));
        if(description!=null && !(description.isEmpty()))spec=spec.and(ProductModelSpecs.hasDescription(description));
        if(ownerId!=null)spec=spec.and(ProductModelSpecs.hasVendor(ownerId));
        if(subCategoryId!=null)spec=spec.and(ProductModelSpecs.hasSubCategory(subCategoryId));
        if(brandId!=null)spec=spec.and(ProductModelSpecs.hasBrand(brandId));
        if(isGlobal!=null)spec=spec.and(ProductModelSpecs.isGlobal(isGlobal));
        if(status!=null)spec=spec.and(ProductModelSpecs.hasStatus(status));

        Page<ProductModel> productModels=productModelRepository.findAll(spec,page);

        return productModels;
    }


    public ProductModelResponse convertProductModelToDto(ProductModel productModel){
        return new ProductModelResponse(
                productModel.getId(),
                (productModel.getBrand()==null?null:productModel.getBrand().getId()),
                productModel.getSubCategory().getId(),
                (productModel.getVendor()==null)?null:productModel.getVendor().getId(),
                productModel.isGlobal(),
                productModel.getName(),
                productModel.getDescription(),
                productModel.getImageUrl(),
                productModel.getStatus()
        );
    }
}
