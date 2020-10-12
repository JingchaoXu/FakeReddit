package com.xujin.fakereddit.repository;

import com.xujin.fakereddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
