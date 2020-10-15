package com.xujin.fakereddit.repository;

import com.xujin.fakereddit.model.Comment;
import com.xujin.fakereddit.model.Post;
import com.xujin.fakereddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
