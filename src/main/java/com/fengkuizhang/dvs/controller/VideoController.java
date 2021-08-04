package com.fengkuizhang.dvs.controller;

import com.fengkuizhang.dvs.controller.request.SearchRequest;
import com.fengkuizhang.dvs.dto.VideoDto;
import com.fengkuizhang.dvs.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("/video")
public class VideoController extends BaseController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/movie")
    public ResponseEntity movie(){
        return success(videoService.findAllMovies());
    }
    @GetMapping("/tv")
    public ResponseEntity tv(){
        return success(videoService.findAllTv());
    }

    @GetMapping("/featured/movie")
    public ResponseEntity featuredMovie(){
        return success(videoService.findFeaturedMovies());
    }

    @GetMapping("/featured/tv")
    public ResponseEntity featuredTv(){
        return success(videoService.findFeaturedTv());
    }

    @PostMapping(value = "/search")
    public ResponseEntity search(@RequestBody @Valid SearchRequest searchRequest){
        List<VideoDto> videoDtoList = videoService.findVideo(searchRequest.getType(), searchRequest.getQ());
        return success(videoDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity detail(@PathVariable @NotEmpty String id){
        return success(videoService.findOne(id));
    }

    @PostMapping("")
    public ResponseEntity addVideo(@RequestBody @Valid VideoDto video){
        VideoDto videoDto = videoService.addVideo(video);
        return success(videoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable @NotEmpty String id, @RequestBody @Valid VideoDto videoDto){
        videoDto.setId(id);
        videoService.updateVideo(videoDto);
        return success();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable @NotEmpty String id){
        videoService.deleteVideo(id);
        return success();
    }
}
