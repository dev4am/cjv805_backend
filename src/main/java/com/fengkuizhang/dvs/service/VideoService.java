package com.fengkuizhang.dvs.service;

import com.fengkuizhang.dvs.dto.VideoDto;
import com.fengkuizhang.dvs.exception.AppException;
import com.fengkuizhang.dvs.model.Video;
import com.fengkuizhang.dvs.repository.VideoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<VideoDto> findAllMovies(){
        List<Video> list = mongoTemplate.find(Query.query(Criteria.where("type").is("movie")), Video.class);
        List<VideoDto> videoDtoList = list.stream()
                .map(e->modelMapper.map(e, VideoDto.class))
                .collect(Collectors.toList());
        return videoDtoList;
    }

    public List<VideoDto> findAllTv(){
        List<Video> list = mongoTemplate.find(Query.query(Criteria.where("type").is("tv")), Video.class);
        List<VideoDto> videoDtoList = list.stream()
                .map(e->modelMapper.map(e, VideoDto.class))
                .collect(Collectors.toList());
        return videoDtoList;
    }

    public VideoDto findOne(String id){
        Optional<Video> video = videoRepository.findById(id);
        if(!video.isPresent()){
            throw new AppException("video not exists");
        }
        return modelMapper.map(video.get(), VideoDto.class);
    }

    public List<Video> findFeaturedMovies(){
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is("movie"));
        query.addCriteria(Criteria.where("featured").is(true));
        List<Video> list = mongoTemplate.find(query, Video.class);
        return list;
    }

    public List<Video> findFeaturedTv(){
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is("tv"));
        query.addCriteria(Criteria.where("featured").is(true));
        List<Video> list = mongoTemplate.find(query, Video.class);
        return list;
    }

    public VideoDto addVideo(VideoDto videoDto){
        Video video = modelMapper.map(videoDto, Video.class);
        video.setId(null);
        videoRepository.save(video);
        videoDto.setId(video.getId());
        return videoDto;
    }

    public List<VideoDto> findVideo(String type, String q){
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is(type));
        query.addCriteria(Criteria.where("name").regex(".*"+q+".*"));
        List<Video> list = mongoTemplate.find(query, Video.class);
        List<VideoDto> videoDtoList = list.stream()
                .map(e->modelMapper.map(e, VideoDto.class))
                .collect(Collectors.toList());
        return videoDtoList;
    }

    public void updateVideo(VideoDto videoDto){
        Optional<Video> vo = videoRepository.findById(videoDto.getId());
        if(!vo.isPresent()){
            throw new AppException("video not exists");
        }
        Video video = vo.get();
        modelMapper.map(videoDto, video);
        videoRepository.save(video);
    }

    public void deleteVideo(String id){
        Optional<Video> vo = videoRepository.findById(id);
        if(!vo.isPresent()){
            throw new AppException("video not exists");
        }
        videoRepository.deleteById(id);
    }

}
