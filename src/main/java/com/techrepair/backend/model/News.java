package com.techrepair.backend.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity 
@Table(name = "technical_news") 
public class News {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(name = "title") 
    private String title; // Cambiado: 'titulo' por 'title'

    @Column(name = "content") 
    private String content; // Cambiado: 'contenido' por 'content'

    @Column(name = "published_at")
    private LocalDateTime createdAt; // Cambiado: 'fechaPublicacion' por 'createdAt'
}