package com.Kee.V2C.service.Brand;

import com.Kee.V2C.dto.brand.BrandResponse;
import com.Kee.V2C.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BrandService {
    Brand getBrandById(Long id);
    Page<Brand> getAllBrands(Pageable page);
    BrandResponse convertBrandToDto(Brand brand);

}
