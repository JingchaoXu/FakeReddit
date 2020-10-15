package com.xujin.fakereddit.mapper;

import com.xujin.fakereddit.dto.PostRequest;
import com.xujin.fakereddit.dto.PostResponse;
import com.xujin.fakereddit.model.Post;
import com.xujin.fakereddit.model.Subreddit;
import com.xujin.fakereddit.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    //@Mapping(target = "subreddit", source = "subreddit") //this can be removed because it's a pair
    //@Mapping(target = "description", source = "postRequest.description") //same here a pair
    @Mapping(target = "user", source = "user") //same a pair
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    Post map(PostRequest postRequest, Subreddit subreddit, User user);


//    @Mapping(target = "postName", source = "postName")
//    @Mapping(target = "description", source = "description")
//    @Mapping(target = "url", source = "url")
    //above can be removed since they are paired.
    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    PostResponse mapToDto(Post post);
}
