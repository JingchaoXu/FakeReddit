package com.xujin.fakereddit.service;

import com.xujin.fakereddit.dto.PostRequest;
import com.xujin.fakereddit.dto.PostResponse;
import com.xujin.fakereddit.exception.PostNotFoundException;
import com.xujin.fakereddit.exception.SubredditNotFoundException;
import com.xujin.fakereddit.mapper.PostMapper;
import com.xujin.fakereddit.model.Post;
import com.xujin.fakereddit.model.Subreddit;
import com.xujin.fakereddit.model.User;
import com.xujin.fakereddit.repository.PostRepository;
import com.xujin.fakereddit.repository.SubredditRepository;
import com.xujin.fakereddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(()->new SubredditNotFoundException(postRequest.getSubredditName()));
        User currentUser =authService.getCurrentUser();

        postRepository.save(postMapper.map(postRequest,subreddit,currentUser));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id){
        Post post= postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(){
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId){
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(()->new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(username));
        List<PostResponse> posts = postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
        return posts;
    }
}
