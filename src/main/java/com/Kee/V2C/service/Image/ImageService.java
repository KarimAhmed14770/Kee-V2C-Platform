package com.Kee.V2C.service.Image;

import com.Kee.V2C.enums.PathFolder;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    public String saveImage(MultipartFile img, PathFolder pathFolder);
}