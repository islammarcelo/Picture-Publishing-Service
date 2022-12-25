package com.example.picture.image;


import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;
    private String status;
    private String url;
    private byte[] image;
    @OneToOne
    @JoinColumn(name = "image_info_id", referencedColumnName = "id")
    private ImageInfo imageInfo;
}
