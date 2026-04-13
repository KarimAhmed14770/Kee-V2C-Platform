package com.Kee.V2C.service;

import com.Kee.V2C.dto.product.ProductModelRequest;
import com.Kee.V2C.dto.product.ProductUpdateRequest;
import com.Kee.V2C.enums.PathFolder;
import com.Kee.V2C.exception.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ImageServiceImpl implements ImageService{
    @Value("${uploads_directory}")
    private String uploadDir;

    @Value("${v2c.upload.allowed-types}")
    private List<String> allowedTypes;

    private static final Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Override
    public String saveImage(MultipartFile img, PathFolder pathFolder){
        if(img==null ||img.isEmpty() ) return null;
        try(InputStream is=img.getInputStream()){//holds the binary data of the image in a pipe

            String name=img.getOriginalFilename();//holds the original name of the file
            name= StringUtils.cleanPath(name);//secures the name from any malicious path traversal attempts
            String uniqueName=UUID.randomUUID().toString()+"_"+name;//generates a unique name

            String contentType = img.getContentType();
            if (contentType == null || !allowedTypes.contains(contentType)) {
                throw new FileUploadException("Invalid file type. Only JPEG, PNG, GIF, and WEBP are allowed.");
            }

            // 2. Define the Relative and Absolute Paths
            String relativeFolder = pathFolder.getPath();
            Path absoluteFolderPath = Paths.get(uploadDir).resolve(relativeFolder);
            // 3. Physical Directory Check
            if (!Files.exists(absoluteFolderPath)) {
                Files.createDirectories(absoluteFolderPath);
            }
            // 4. Resolve the absolute path for the WRITE operation
            Path absoluteSavePath = absoluteFolderPath.resolve(uniqueName);
            Files.copy(is, absoluteSavePath, StandardCopyOption.REPLACE_EXISTING);

            // 5. Return only the RELATIVE path for the DB
            return relativeFolder + uniqueName;
        }
        catch (IOException e){
            log.error("Failed to save image to disk", e);

            // Wrap it in a RuntimeException so the Controller/Handler can catch it
            throw new FileUploadException("Could not store the image. Please try again.");
        }

    }
}
