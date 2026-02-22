package com.example.demo.repository;

import com.example.demo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    List<Comment> findAllByOrderByCreatedAtDesc();

    Optional<Comment> findCommentsByIdAndUserId(Long id, Long userId);
}
