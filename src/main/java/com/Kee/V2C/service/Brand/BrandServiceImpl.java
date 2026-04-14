package com.Kee.V2C.service.Brand;

import com.Kee.V2C.Repository.BrandRepository;
import com.Kee.V2C.dto.brand.BrandResponse;
import com.Kee.V2C.entity.Brand;
import com.Kee.V2C.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService{
    private final BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository){
        this.brandRepository=brandRepository;
    }

    @Override
    public Brand getBrandById(Long id){
        return brandRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("brand with id: "+id+" not found."));
    }

    @Override
    public Page<Brand> getAllBrands(Pageable page){
        return brandRepository.findByActiveTrue(page);
    }

    public BrandResponse convertBrandToDto(Brand brand){
        return new BrandResponse(brand.getId(), brand.getName(), brand.getDescription(), brand.getImageUrl(),brand.getActive());
    }
}
