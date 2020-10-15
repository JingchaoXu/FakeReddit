package com.xujin.fakereddit.service;

import com.xujin.fakereddit.dto.CommentsDto;
import com.xujin.fakereddit.exception.PostNotFoundException;
import com.xujin.fakereddit.mapper.CommentMapper;
import com.xujin.fakereddit.model.Comment;
import com.xujin.fakereddit.model.NotificationEmail;
import com.xujin.fakereddit.model.Post;
import com.xujin.fakereddit.model.User;
import com.xujin.fakereddit.repository.CommentRepository;
import com.xujin.fakereddit.repository.PostRepository;
import com.xujin.fakereddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentService mailContentService;
    private final MailService mailService;

    @Autowired
    public CommentService(PostRepository postRepository, UserRepository userRepository, AuthService authService, CommentMapper commentMapper, CommentRepository commentRepository, MailContentService mailContentService, MailService mailService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.authService = authService;
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
        this.mailContentService = mailContentService;
        this.mailService = mailService;
    }

    public void save(CommentsDto commentsDto){
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(()-> new PostNotFoundException("There is post match is id: "+
                        commentsDto.getId().toString()));

        User currentUser = authService.getCurrentUser();
        Comment commentMap = commentMapper.map(commentsDto,post,currentUser);
        commentRepository.save(commentMap);

        String message = mailContentService.build(post.getUser().getUsername() + " Posted a comment on your post.");
        sendCommentNotificationMessage(message, post.getUser());
    }

    private void sendCommentNotificationMessage(String message, User user){
        mailService.sendMail(new NotificationEmail(user.getUsername() + "Commented on your post", user.getEmail(), message));

    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post =postRepository.findById(postId)
                .orElseThrow(()->new PostNotFoundException(postId.toString()));
       List<CommentsDto> returnCommentList =  commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
        return returnCommentList;
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(()->
                new UsernameNotFoundException(userName));
        List<CommentsDto> returnCommentList = commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
        return returnCommentList;

    }
}
