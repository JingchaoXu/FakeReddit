package com.xujin.fakereddit.service;

import com.xujin.fakereddit.exception.SpringRedditException;
import com.xujin.fakereddit.model.RefreshToken;
import com.xujin.fakereddit.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }
    @Transactional
    public RefreshToken generateRefreshToken(){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }
    @Transactional
    public void validateRefreshToken(String token){
        refreshTokenRepository.findByToken(token)
                .orElseThrow(()->new SpringRedditException("Invalid refresh Token"));

    }
    @Transactional
    public void deleteRefreshToken(String token){
        refreshTokenRepository.deleteByToken(token);
   }
}
