package me.kevinntech.onlineshop.domain.warehousing.controller;

import lombok.RequiredArgsConstructor;
import me.kevinntech.onlineshop.domain.warehousing.service.WarehousingService;
import me.kevinntech.onlineshop.domain.warehousing.dto.CreateWarehousingRequest;
import me.kevinntech.onlineshop.global.dto.OkResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class WarehousingApiController {

    private final WarehousingService warehousingService;

    @PostMapping("/api/v1/warehousing")
    public OkResponse<Long> createWarehousing(@Valid @RequestBody CreateWarehousingRequest request) {
        Long warehousingId = warehousingService.createWarehousing(request.toDto());
        return OkResponse.of(warehousingId);
    }

}