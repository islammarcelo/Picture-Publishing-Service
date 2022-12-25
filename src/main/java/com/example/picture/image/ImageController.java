package com.example.picture.image;

import com.example.picture.imageUtility.ImageUtility;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api")
public class ImageController {
    @Autowired
    private final ImageService imageService;

    @PostMapping("image/upload")
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam("image") MultipartFile file,
                                                     @RequestParam("description") String description,
                                                     @RequestParam("category") String category) throws IOException {
        imageService.uploadImage(description, category, file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageResponse("Image uploaded successfully: " + file.getOriginalFilename()));
    }

    @GetMapping("/images/uploaded")
    public ResponseEntity<List<Image>> getUploadedImages() {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.getUploadedImages());
    }

    @GetMapping(path = {"/image/info/{id}"})
    public ImageInfo getImageDetails(@PathVariable("id") Long id) throws IOException {
        return imageService.getImageDetails(id);
    }

    @GetMapping(path = {"/image/view/{id}"})
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) throws IOException {
        Image image = imageService.getImage(id);
        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(image.getType()))
                .body(ImageUtility.decompressImage(image.getImage()));
    }

    @GetMapping("/images/accepted")
    public ResponseEntity<List<ImageUrl>> getAcceptedImages() throws IOException {
        List<String> urls = imageService.getAcceptedImages();
        List<ImageUrl> imageUrls = new ArrayList<>();
        for (String url : urls)
            imageUrls.add(new ImageUrl(url));

        return ResponseEntity.status(HttpStatus.OK).body(imageUrls);
    }

    @PutMapping(path = {"/image/update/{id}"})
    ResponseEntity<ImageResponse> updateImage(@PathVariable("id") Long id,
                                              @RequestParam("status") String status) throws IOException {
        imageService.updateImage(id, status);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageResponse("Image updated successfully"));
    }


}




