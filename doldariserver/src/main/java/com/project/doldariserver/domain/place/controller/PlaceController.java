package com.project.doldariserver.domain.place.controller;

import com.project.doldariserver.domain.place.dto.RegionCreationRequest;
import com.project.doldariserver.domain.place.dto.RegionDto;
import com.project.doldariserver.domain.place.dto.SiteCreationRequest;
import com.project.doldariserver.domain.place.dto.SiteDto;
import com.project.doldariserver.domain.place.service.PlaceService;
import com.project.doldariserver.domain.user.dto.UserCreationRequest;
import com.project.doldariserver.domain.user.dto.UserDto;
import com.project.doldariserver.domain.user.dto.TeamMemberTrainingStatusDto;
import com.project.doldariserver.domain.user.entity.User;
import com.project.doldariserver.domain.user.entity.UserRole;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<List<SiteDto>> getLocations() {
        List<SiteDto> locations = placeService.findLocations();
        return ResponseEntity.ok(locations);
    }

    @PostMapping("/regions")
    public ResponseEntity<RegionDto> createRegion(@RequestBody RegionCreationRequest request) {
        RegionDto newRegion = placeService.createRegion(request);
        return new ResponseEntity<>(newRegion, HttpStatus.CREATED);
    }

    @PostMapping("/sites")
    public ResponseEntity<SiteDto> createSite(@RequestBody SiteCreationRequest request) {
        SiteDto newSite = placeService.createSite(request);
        return new ResponseEntity<>(newSite, HttpStatus.CREATED);
    }

    @DeleteMapping("/regions/{regionId}")
    public ResponseEntity<Void> deleteRegion(@PathVariable Long regionId) {
        placeService.deleteRegion(regionId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/sites/{siteId}")
    public ResponseEntity<Void> deleteSite(@PathVariable Long siteId) {
        placeService.deleteSite(siteId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sites/{siteId}/users")
    public ResponseEntity<List<UserDto>> getUsersBySite(@PathVariable Long siteId) {
        return ResponseEntity.ok(placeService.getUsersBySite(siteId));
    }

    @GetMapping("/sites/{siteId}/training-status")
    public ResponseEntity<?> getTeamTrainingStatus(@PathVariable Long siteId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != UserRole.leader || !user.getSite().getId().equals(siteId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
        }

        List<TeamMemberTrainingStatusDto> teamStatus = placeService.getTeamMembersTrainingStatus(siteId);
        return ResponseEntity.ok(teamStatus);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> addUser(@RequestBody UserCreationRequest request) {
        UserDto newUser = placeService.addUser(request);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        placeService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}