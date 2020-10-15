package com.xujin.fakereddit.repository;

import com.xujin.fakereddit.model.Post;
import com.xujin.fakereddit.model.User;
import com.xujin.fakereddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUse);
}
