package com.xujin.fakereddit.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.xujin.fakereddit.dto.PostRequest;
import com.xujin.fakereddit.dto.PostResponse;
import com.xujin.fakereddit.model.Post;
import com.xujin.fakereddit.model.Subreddit;
import com.xujin.fakereddit.model.User;
import com.xujin.fakereddit.repository.CommentRepository;
import com.xujin.fakereddit.repository.VoteRepository;
import com.xujin.fakereddit.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;

    //@Mapping(target = "subreddit", source = "subreddit") //this can be removed because it's a pair
    //@Mapping(target = "description", source = "postRequest.description") //same here a pair
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "voteCount", constant = "0") //Default vote count is 0
    @Mapping(target = "user", source = "user") //same a pair
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);


//    @Mapping(target = "postName", source = "postName")
//    @Mapping(target = "description", source = "description")
//    @Mapping(target = "url", source = "url")
    //above can be removed since they are paired.
    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post){
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post){
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
}
