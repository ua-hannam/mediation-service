package com.uahannam.mediation.controller;

import com.uahannam.mediation.dto.MediationRequestDto;
import com.uahannam.mediation.dto.MediationResponseDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 주문 중개 컨트롤러
 *
 * @author swlee
 * @since 2023. 01. 25
 */
@Slf4j(topic = "MediationController")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/mediation")
public class MediationController {

    @PostMapping("")
    public ResponseEntity<MediationResponseDto> sendOrderResult(MediationRequestDto mediationRequestDto) {
        return ResponseEntity.ok().body(MediationResponseDto.builder().build());
    }

}
