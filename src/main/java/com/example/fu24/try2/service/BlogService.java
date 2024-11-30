package com.example.fu24.try2.service;



import com.example.fu24.try2.model.Blog;
import com.example.fu24.try2.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogRepository repo;

    public List<Blog> listAll(String keyword) {
        if (keyword != null) {
            return repo.search(keyword);
        }
        return repo.findAll();
    }

    public List<Blog> listAllWithoutKeyWord() {
        return repo.findAll();
    }

    public void save(Blog blog) {
        repo.save(blog);
    }

    public Blog get(Long id) {
        return repo.findById(id).get();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<Blog> search(String name, String text, LocalDateTime date) {
        if (name != null && !name.isEmpty() && text != null && !text.isEmpty() && date != null) {
            return repo.findByNameContainingAndTextContainingAndDate(name, text, date);
        } else if (name != null && !name.isEmpty() && text != null && !text.isEmpty()) {
            return repo.findByNameContainingAndText(name, text);
        } else if (name != null && !name.isEmpty() && date != null) {
            return repo.findByNameContainingAndDate(name, date);
        } else if (text != null && !text.isEmpty() && date != null) {
            return repo.findByTextContainingAndDate(text, date);
        } else if (name != null && !name.isEmpty()) {
            return repo.findByNameContaining(name);
        } else if (text != null && !text.isEmpty()) {
            return repo.findByTextContaining(text);
        } else if (date != null) {
            return repo.findByDate(date);
        } else {
            return repo.findAll();
        }
    }
}
