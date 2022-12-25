package com.example.picture;

import com.example.picture.image.*;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ImageServiceTest {
    @InjectMocks
    private ImageService service;

    @Mock
    private ImageRepo imageRepo;
    @Mock
    private ImageInfoRepo imageInfoRepo;

    @Test
    void shouldUploadImage() throws IOException {


    }

    @Test
    void shouldFindAndReturnUploadedImages() {
        // Arrange
        Image image1 =Image.builder().status("waiting").build();
        Image image2 =Image.builder().status("waiting").build();
        when(imageRepo.findByStatusEquals(anyString())).thenReturn(List.of(image1, image1));

        // Act & Assert
        assertThat(service.getUploadedImages()).hasSize(2).extracting(Image::getStatus).containsExactly("waiting","waiting");

    }

    @Test
    void shouldFindAndReturnImageDetails(){
        // Arrange
        ImageInfo expectedImageInfo = ImageInfo.builder().status("waiting").category("image").description("image").id(1L).build();
        imageInfoRepo.save(expectedImageInfo);
        when(imageInfoRepo.findById(anyLong())).thenReturn(Optional.of(expectedImageInfo));

        // Act
        final var actual = service.getImageDetails(1L);

        // Assert
        assertThat(actual).usingRecursiveComparison().isEqualTo(expectedImageInfo);
    }

    @Test
    void shouldFindAndReturnImage(){
        // Arrange
        Image expectedImage = Image.builder().id(1L).name("image").build();
        imageRepo.save(expectedImage);
        when(imageRepo.findById(anyLong())).thenReturn(Optional.of(expectedImage));

        // Act
        final var actual = service.getImage(1L);

        // Assert
        assertThat(actual).usingRecursiveComparison().isEqualTo(expectedImage);
    }

    @Test
    void shouldFindAndReturnAcceptedImages() {
        // Arrange
        Image image1 =Image.builder().status("accepted").build();
        Image image2 =Image.builder().status("accepted").build();
        when(imageRepo.findByStatusEquals(anyString())).thenReturn(List.of(image1, image1));

        // Act & Assert
        assertThat(service.getUploadedImages()).hasSize(2).extracting(Image::getStatus).containsExactly("accepted","accepted");

    }



}
