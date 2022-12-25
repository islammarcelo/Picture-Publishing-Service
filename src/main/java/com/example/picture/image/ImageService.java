package com.example.picture.image;

import com.example.picture.imageUtility.ImageUtility;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ImageService {
    @Autowired
    private final ImageInfoRepo imageInfoRepo;
    private final ImageRepo imageRepo;

    public void uploadImage(String description, String category, MultipartFile file) throws IOException {
        if(description.length() == 0 || description == null || category.length() == 0 || category == null || file.isEmpty()){
            throw new IllegalStateException("Failed, please write the date right :) ");
        }
        if(file.getContentType().contains("png") || file.getContentType().contains("jpg") || file.getContentType().contains("gif")){

            String fullPath = System.getProperty("user.dir") + file.getOriginalFilename();
            Files.write(Path.of(fullPath), file.getBytes());
            ImageInfo imageInfo = ImageInfo.builder().description(description).category(category).status(Level.waiting.toString()).build();
            imageInfoRepo.save(imageInfo);
            imageRepo.save(Image.builder().name(file.getOriginalFilename()).type(file.getContentType()).status(Level.waiting.toString()).url(fullPath)
                    .image(ImageUtility.compressImage(file.getBytes()))
                    .imageInfo(imageInfo)
                    .build());
        }else{
            throw new IllegalStateException("Failed, the type of image should be png, jpg or gif :) ");
        }

    }

    public List<Image> getUploadedImages() {
        return imageRepo.findByStatusEquals(Level.waiting.toString());
    }

    public ImageInfo getImageDetails(Long id) {
        ImageInfo image = imageInfoRepo.findById(id).orElseThrow(() -> new IllegalStateException(
                "Image with this id " + id + " dose not exists"));

        return image;
    }

    public Image getImage(Long id) {
        Image image = imageRepo.findById(id).orElseThrow(() -> new IllegalStateException(
                "Image with this id " + id + " dose not exists"));

        return image;

    }

    public List<String> getAcceptedImages() {
        List<String> imagesUrl = new ArrayList<>();
        List<Image> images = imageRepo.findByStatusEquals(Level.accepted.toString());
        for (int i = 0; i < images.size(); i++)
            imagesUrl.add(images.get(i).getUrl());
        return imagesUrl;

    }


    @Transactional
    public void updateImage(Long id, String status) throws IOException {
        Image image = imageRepo.findById(id).orElseThrow(() -> new IllegalStateException(
                "Image with this id " + id + " dose not exists"));

        if (status.equals(Level.accepted.toString())){
            image.setStatus(Level.accepted.toString());
            image.getImageInfo().setStatus(Level.accepted.toString());
        }else if (status.equals(Level.rejected.toString())){
            image.getImageInfo().setStatus(Level.rejected.toString());
            imageRepo.delete(image);
            Files.delete(Path.of(image.getUrl()));
        }else{
            throw new IllegalStateException("Wrong status, please write the status right :) ");
        }

    }


}
enum Level {
    waiting,
    accepted,
    rejected
}

