package org.example.Web4.controller;

import org.example.Web4.dto.PointRequest;
import org.example.Web4.dto.PointResponse;
import org.example.Web4.model.PointResult;
import org.example.Web4.model.User;
import org.example.Web4.repository.UserRepository;
import org.example.Web4.service.PointCheckService;
import org.example.Web4.repository.PointResultRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/points")
public class PointController {

    private final PointCheckService pointCheckService;
    private final PointResultRepository pointResultRepository;
    private final UserRepository userRepository;

    public PointController(PointCheckService pointCheckService,
                           PointResultRepository pointResultRepository,
                           UserRepository userRepository) {
        this.pointCheckService = pointCheckService;
        this.pointResultRepository = pointResultRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PointResponse> addPoint(@Valid @RequestBody PointRequest pointRequest,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        User user = getCurrentUser(userDetails);
        long startTime = System.nanoTime();
        boolean hit = pointCheckService.checkHit(pointRequest.getX(), pointRequest.getY(), pointRequest.getR());
        long executionTime = System.nanoTime() - startTime;

        PointResult pointResult = new PointResult(
                pointRequest.getX(),
                pointRequest.getY(),
                pointRequest.getR(),
                hit,
                executionTime,
                user
        );

        pointResultRepository.save(pointResult);

        return ResponseEntity.ok(new PointResponse(pointResult));
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<PointResponse>> getAllPoints(@AuthenticationPrincipal UserDetails userDetails) {
        User user = getCurrentUser(userDetails);
        List<PointResult> points = pointResultRepository.findByUser(user);
        List<PointResponse> responses = points.stream()
                .map(PointResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> deleteAllPoints(@AuthenticationPrincipal UserDetails userDetails) {
        User user = getCurrentUser(userDetails);
        pointResultRepository.deleteByUser(user);
        return ResponseEntity.noContent().build();
    }

    private User getCurrentUser(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}