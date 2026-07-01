package com.oceantech.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String gender;

    private Double rating;

    private String img;

    @JsonIgnoreProperties({"category"})
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;
}