package com.xujin.fakereddit.service;

import com.xujin.fakereddit.dto.VoteDto;
import com.xujin.fakereddit.exception.PostNotFoundException;
import com.xujin.fakereddit.exception.SpringRedditException;
import com.xujin.fakereddit.model.Post;
import com.xujin.fakereddit.model.User;
import com.xujin.fakereddit.model.Vote;
import com.xujin.fakereddit.repository.PostRepository;
import com.xujin.fakereddit.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.xujin.fakereddit.model.VoteType.UPVOTE;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Autowired
    public VoteService(VoteRepository voteRepository, PostRepository postRepository, AuthService authService) {
        this.voteRepository = voteRepository;
        this.postRepository = postRepository;
        this.authService = authService;
    }

    public void vote(VoteDto voteDto){
        Post post =postRepository.findById(voteDto.getPostId())
                .orElseThrow(()->new PostNotFoundException("Post Not Found with ID - "
                        + voteDto.getPostId().toString()));
        User currentUser = authService.getCurrentUser();
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,currentUser);
        if(voteByPostAndUser.isPresent()
                && voteByPostAndUser
                .get()
                .getVoteType()
                .equals(voteDto.getVoteType())){
            throw new SpringRedditException("You have already "
            + voteDto.getVoteType() + "'d for this post");
        }
        if(UPVOTE.equals((voteDto.getVoteType()))){
            post.setVoteCount(post.getVoteCount() +1);
        }else{
            post.setVoteCount(post.getVoteCount() -1);
        }

        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);

    }
    private Vote mapToVote(VoteDto voteDto, Post post){
        return Vote.builder()
                .post(post)
                .user(authService.getCurrentUser())
                .voteType(voteDto.getVoteType())
                .build();
    }
}
