package com.xujin.fakereddit.service;

import com.xujin.fakereddit.dto.SubredditDto;
import com.xujin.fakereddit.exception.SpringRedditException;
import com.xujin.fakereddit.mapper.SubredditMapper;
import com.xujin.fakereddit.model.Subreddit;
import com.xujin.fakereddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto){
        Subreddit safe = subredditRepository.save (subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(safe.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll(){
       List<SubredditDto> returnBody = subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(Collectors.toList());
       return  returnBody;
    }

    @Transactional(readOnly = true)
    public SubredditDto getSubreddit(Long id){
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(()->new SpringRedditException("No subreddit found with this id: "+id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }
    /*The last two mapping functions were defined to make conversion between DB model Subreddit and presenting
    Obj SubredditDto, and these can be replaced by MapStruct to do it automatically
     */

//    private SubredditDto mapToDto(Subreddit subreddit){
//        return SubredditDto.builder().name(subreddit.getName())
//                .id(subreddit.getId())
//                .numberOfPosts(subreddit.getPosts().size())
//                .build();
//
//    }
//
//    private Subreddit mapSubredditDto(SubredditDto subredditDto) {
//        return Subreddit.builder().name(subredditDto.getName())
//                .description(subredditDto.getDescription())
//                .build();
//    }


}
