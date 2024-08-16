package com.blog_pakicetus.blog_pakicetus.services;

import com.blog_pakicetus.blog_pakicetus.dto.PostDTO;
import com.blog_pakicetus.blog_pakicetus.models.Post;
import com.blog_pakicetus.blog_pakicetus.models.User;
import com.blog_pakicetus.blog_pakicetus.repositories.PostRepository;
import com.blog_pakicetus.blog_pakicetus.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public Post createPost(PostDTO postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        Long userId = postDto.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        post.setUser(user);
        return postRepository.save(post);
    }

    public Post likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (post.getLikedUsers().contains(user)) {
            throw new RuntimeException("User has already liked this post");
        }

        if (post.getDislikedUsers().contains(user)) {
            throw new RuntimeException("User has already disliked this post");
        }

        post.getLikedUsers().add(user);
        post.setLikes(post.getLikes() + 1);
        return postRepository.save(post);
    }

    public Post dislikePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (post.getDislikedUsers().contains(user)) {
            throw new RuntimeException("User has already disliked this post");
        }

        if (post.getLikedUsers().contains(user)) {
            throw new RuntimeException("User has already liked this post");
        }

        post.getDislikedUsers().add(user);
        post.setDislikes(post.getDislikes() + 1);
        return postRepository.save(post);
    }

    public boolean deletePost(Long postId, Long userId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent() && optionalPost.get().getUser().getId().equals(userId)) {
            postRepository.deleteById(postId);
            return true;
        }
        return false;
    }

    public Post updatePost(Long postId, PostDTO postDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        return postRepository.save(post);
    }
}
