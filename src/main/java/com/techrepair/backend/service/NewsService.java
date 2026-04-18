package com.techrepair.backend.service;

import com.techrepair.backend.dto.NewsDTO;
import com.techrepair.backend.entity.News;
import com.techrepair.backend.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public List<NewsDTO> getAllNews() {
        return newsRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public NewsDTO saveNews(NewsDTO newsDTO) {
        News news = new News();
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        News saved = newsRepository.save(news);
        return convertToDTO(saved);
    }

    public NewsDTO updateNews(Long id, NewsDTO newsDTO) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada con ID: " + id));
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        News updated = newsRepository.save(news);
        return convertToDTO(updated);
    }

    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }

    private NewsDTO convertToDTO(News news) {
        NewsDTO dto = new NewsDTO();
        dto.setId(news.getId());
        dto.setTitle(news.getTitle());
        dto.setContent(news.getContent());
        dto.setCreatedAt(news.getCreatedAt());
        return dto;
    }
}