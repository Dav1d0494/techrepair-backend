package com.techrepair.backend.controller;

import com.techrepair.backend.dto.NewsDTO;
import com.techrepair.backend.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:8088"})
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/evidencia-noticias")
    public ResponseEntity<Map<String, Object>> getEvidenciaNoticias() {
        List<NewsDTO> list = newsService.getAllNews();
        List<Map<String, Object>> noticias = new ArrayList<>();
        for (NewsDTO n : list) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", n.getId());
            item.put("titulo", n.getTitle());
            item.put("contenido", n.getContent());
            item.put("fecha", n.getCreatedAt() != null ? n.getCreatedAt().toString() : null);
            noticias.add(item);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("noticias", noticias);
        response.put("total", noticias.size());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/evidencia-noticias")
    public ResponseEntity<Map<String, Object>> saveEvidenciaNoticia(@RequestBody Map<String, Object> noticia) {
        NewsDTO dto = new NewsDTO();
        dto.setTitle((String) noticia.get("titulo"));
        dto.setContent((String) noticia.get("contenido"));
        NewsDTO saved = newsService.saveNews(dto);
        Map<String, Object> response = new HashMap<>();
        response.put("id", saved.getId());
        response.put("titulo", saved.getTitle());
        response.put("contenido", saved.getContent());
        response.put("fecha", saved.getCreatedAt() != null ? saved.getCreatedAt().toString() : null);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/evidencia-noticias/{id}")
    public ResponseEntity<Map<String, Object>> updateNoticia(@PathVariable Long id, @RequestBody Map<String, Object> noticia) {
        NewsDTO dto = new NewsDTO();
        dto.setTitle((String) noticia.get("titulo"));
        dto.setContent((String) noticia.get("contenido"));
        NewsDTO updated = newsService.updateNews(id, dto);
        Map<String, Object> response = new HashMap<>();
        response.put("id", updated.getId());
        response.put("titulo", updated.getTitle());
        response.put("contenido", updated.getContent());
        response.put("fecha", updated.getCreatedAt() != null ? updated.getCreatedAt().toString() : null);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/evidencia-noticias/{id}")
    public ResponseEntity<Map<String, Object>> deleteNoticia(@PathVariable Long id) {
        newsService.deleteNews(id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Noticia eliminada correctamente");
        return ResponseEntity.ok(response);
    }

    // ✅ NUEVO: Endpoint con /api para compatibilidad con apiClient
    @DeleteMapping("/api/evidencia-noticias/{id}")
    public ResponseEntity<Map<String, Object>> deleteNoticiaApi(@PathVariable Long id) {
        return deleteNoticia(id);
    }

    @GetMapping("/api/evidencia-noticias")
    public ResponseEntity<Map<String, Object>> getEvidenciaNoticiasApi() {
        return getEvidenciaNoticias();
    }

    @GetMapping("/api/noticias-tecnicas")
    public ResponseEntity<List<Map<String, Object>>> getNoticiasTecnicas() {
        List<NewsDTO> list = newsService.getAllNews();
        List<Map<String, Object>> response = new ArrayList<>();
        for (NewsDTO n : list) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", n.getId());
            item.put("titulo", n.getTitle());
            item.put("contenido", n.getContent());
            item.put("fecha", n.getCreatedAt() != null ? n.getCreatedAt().toString() : null);
            response.add(item);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/noticias-tecnicas")
    public ResponseEntity<Map<String, Object>> saveNoticiaTecnica(@RequestBody Map<String, Object> noticia) {
        return saveEvidenciaNoticia(noticia);
    }

    @GetMapping("/api/news/list")
    public ResponseEntity<List<NewsDTO>> list() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    @PostMapping("/api/news/save")
    public ResponseEntity<NewsDTO> save(@RequestBody NewsDTO dto) {
        return ResponseEntity.ok(newsService.saveNews(dto));
    }
}