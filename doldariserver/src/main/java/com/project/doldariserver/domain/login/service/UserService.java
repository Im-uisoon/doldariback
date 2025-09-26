package com.project.doldariserver.domain.login.service;

import com.project.doldariserver.domain.user.entity.User;
import com.project.doldariserver.domain.user.repository.UserRepository;
import com.project.doldariserver.domain.place.entity.Site;
import com.project.doldariserver.domain.place.repository.SiteRepository;
import com.project.doldariserver.domain.login.dto.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final SiteRepository siteRepository;

    // 로그인 기능
    @Transactional
    public User login(UserLoginRequest request) {
        // 현장 조회
        Optional<Site> siteOptional = siteRepository.findById(request.getSiteId());

        // 현장 정보 없음
        if (siteOptional.isEmpty()) {
            return null;
        }
        Site site = siteOptional.get();

        // 현장-팀 매칭 확인
        if (!site.getRegion().getId().equals(request.getRegionId())) {
            return null;
        }

        // 매칭 후 로그인 사용자 탐색
        return userRepository.findByNameAndPhoneAndSite_Id(request.getName(), request.getPhone(), site.getId())
                .orElse(null);
    }
}
