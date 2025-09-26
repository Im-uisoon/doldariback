package com.project.doldariserver.domain.place.service;

import com.project.doldariserver.domain.place.dto.RegionCreationRequest;
import com.project.doldariserver.domain.place.dto.RegionDto;
import com.project.doldariserver.domain.place.dto.SiteCreationRequest;
import com.project.doldariserver.domain.place.dto.SiteDto;
import com.project.doldariserver.domain.place.entity.Region;
import com.project.doldariserver.domain.place.entity.Site;
import com.project.doldariserver.domain.place.repository.RegionRepository;
import com.project.doldariserver.domain.place.repository.SiteRepository;
import com.project.doldariserver.domain.task.entity.DailyTraining;
import com.project.doldariserver.domain.user.dto.UserCreationRequest;
import com.project.doldariserver.domain.user.dto.UserDto;
import com.project.doldariserver.domain.user.entity.User;
import com.project.doldariserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import com.project.doldariserver.domain.task.repository.DailyTrainingRepository;
import com.project.doldariserver.domain.user.dto.DailyTrainingStatusDto;
import com.project.doldariserver.domain.user.dto.TeamMemberTrainingStatusDto;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceService {

    private final SiteRepository siteRepository;
    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private final DailyTrainingRepository dailyTrainingRepository;

    public List<TeamMemberTrainingStatusDto> getTeamMembersTrainingStatus(Long siteId) {
        List<User> users = userRepository.findBySiteId(siteId);
        List<TeamMemberTrainingStatusDto> result = new ArrayList<>();

        for (User user : users) {
            List<DailyTraining> trainings = dailyTrainingRepository.findByUser_IdAndTask_TaskDate(user.getId(), LocalDate.now());
            List<DailyTrainingStatusDto> trainingDtos = trainings.stream()
                    .map(t -> new DailyTrainingStatusDto(t.getTask().getTaskType().getName(), t.getTrainingCode(), t.isDone()))
                    .collect(Collectors.toList());
            result.add(new TeamMemberTrainingStatusDto(user.getId(), user.getName(), trainingDtos));
        }

        return result;
    }

    public List<SiteDto> findLocations() {
        List<Site> sites = siteRepository.findAll();
        return sites.stream()
                .map(SiteDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public RegionDto createRegion(RegionCreationRequest request) {
        Region region = new Region();
        region.setName(request.getName());
        Region savedRegion = regionRepository.save(region);
        return new RegionDto(savedRegion);
    }

    @Transactional
    public SiteDto createSite(SiteCreationRequest request) {
        Region region = regionRepository.findById(request.getRegionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid region Id:" + request.getRegionId()));
        Site site = new Site();
        site.setName(request.getName());
        site.setRegion(region);
        Site savedSite = siteRepository.save(site);
        return new SiteDto(savedSite);
    }

    @Transactional
    public void deleteRegion(Long regionId) {
        regionRepository.deleteById(regionId);
    }

    @Transactional
    public void deleteSite(Long siteId) {
        siteRepository.deleteById(siteId);
    }

    public List<UserDto> getUsersBySite(Long siteId) {
        return userRepository.findBySiteId(siteId).stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto addUser(UserCreationRequest request) {
        Site site = siteRepository.findById(request.getSiteId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid site Id:" + request.getSiteId()));
        User user = new User();
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        user.setSite(site);
        User savedUser = userRepository.save(user);
        return new UserDto(savedUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
