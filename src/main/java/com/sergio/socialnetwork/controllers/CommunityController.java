package com.sergio.socialnetwork.controllers;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import com.sergio.socialnetwork.dto.ImageDto;
import com.sergio.socialnetwork.dto.MessageDto;
import com.sergio.socialnetwork.dto.UserDto;
import com.sergio.socialnetwork.services.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/community")
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/messages")
    public ResponseEntity<List<MessageDto>> getCommunityMessages(
            @AuthenticationPrincipal UserDto user,
            @RequestParam(value = "page", defaultValue = "0") int page) {
        return ResponseEntity.ok(communityService.getCommunityMessages(user, page));
    }

    @PostMapping("/messages")
    public ResponseEntity<MessageDto> postMessage(@AuthenticationPrincipal UserDto user,
                                                  @RequestBody MessageDto messageDto) {
        return ResponseEntity.created(URI.create("/v1/community/messages"))
                .body(communityService.postMessage(user, messageDto));
    }

    @PostMapping("/images")
    public ResponseEntity<ImageDto> postImage(
            @AuthenticationPrincipal UserDto userDto,
            @RequestParam MultipartFile file,
            @RequestParam(value = "title") String title
    ) throws IOException {
        return ResponseEntity.created(URI.create("/community/images"))
                .body(communityService.postImage(userDto, file, title));
    }

    @GetMapping("/images")
    public ResponseEntity<List<ImageDto>> getCommunityImages(
            @AuthenticationPrincipal UserDto userDto,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        return ResponseEntity.ok(communityService.getCommunityImages(userDto, page));
    }
}
