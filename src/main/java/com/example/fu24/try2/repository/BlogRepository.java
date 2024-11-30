package com.example.fu24.try2.repository;

import com.example.fu24.try2.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    @Query("SELECT p FROM Blog p WHERE CONCAT(p.name, ' ', p.text, ' ', p.date, ' ', p.author) LIKE %?1%")
    List<Blog> search(String keyword);

    @Query("SELECT p FROM Blog p WHERE p.name LIKE %?1%")
    List<Blog> findByNameContaining(String name);

    @Query("SELECT p FROM Blog p WHERE p.text LIKE %?1%")
    List<Blog> findByTextContaining(String text);

    @Query("SELECT p FROM Blog p WHERE p.date = ?1")
    List<Blog> findByDate(LocalDateTime date);

    @Query("SELECT p FROM Blog p WHERE p.name LIKE %?1% AND p.text LIKE %?2%")
    List<Blog> findByNameContainingAndText(String name, String text);

    @Query("SELECT p FROM Blog p WHERE p.name LIKE %?1% AND p.date = ?2")
    List<Blog> findByNameContainingAndDate(String name, LocalDateTime date);

    @Query("SELECT p FROM Blog p WHERE p.text LIKE %?1% AND p.date = ?2")
    List<Blog> findByTextContainingAndDate(String text, LocalDateTime date);

    @Query("SELECT p FROM Blog p WHERE p.name LIKE %?1% AND p.text LIKE %?2% AND p.date = ?3")
    List<Blog> findByNameContainingAndTextContainingAndDate(String name, String text, LocalDateTime date);
}


