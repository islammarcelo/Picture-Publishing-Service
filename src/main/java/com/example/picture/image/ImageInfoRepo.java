package com.example.picture.image;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageInfoRepo extends JpaRepository<ImageInfo, Long> {
}
