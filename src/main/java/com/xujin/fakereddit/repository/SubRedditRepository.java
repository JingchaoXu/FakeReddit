package com.xujin.fakereddit.repository;

import com.xujin.fakereddit.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubRedditRepository extends JpaRepository<Subreddit, Long> {
}
