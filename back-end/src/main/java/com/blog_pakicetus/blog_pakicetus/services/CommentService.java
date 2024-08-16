package com.blog_pakicetus.blog_pakicetus.services;

import com.blog_pakicetus.blog_pakicetus.models.Comment;
import com.blog_pakicetus.blog_pakicetus.models.Post;
import com.blog_pakicetus.blog_pakicetus.models.User;
import com.blog_pakicetus.blog_pakicetus.repositories.CommentRepository;
import com.blog_pakicetus.blog_pakicetus.repositories.PostRepository;
import com.blog_pakicetus.blog_pakicetus.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Comment> getCommentsByPost(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public Comment createComment(Long postId, Long userId, Comment comment) {
        // Buscar el post por ID
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + postId));

        // Buscar el usuario por ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        // Asociar el post y el usuario con el comentario
        comment.setPost(post);
        comment.setUser(user);

        // Guardar el comentario en la base de datos
        return commentRepository.save(comment);
    }

    public boolean deleteComment(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            commentRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public Comment updateComment(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id " + commentId));
        comment.setContent(content);
        return commentRepository.save(comment);
    }
}
