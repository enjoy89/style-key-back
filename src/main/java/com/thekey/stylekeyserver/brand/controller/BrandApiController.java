package com.thekey.stylekeyserver.brand.controller;

import com.thekey.stylekeyserver.brand.domain.Brand;
import com.thekey.stylekeyserver.brand.dto.response.BrandResponse;
import com.thekey.stylekeyserver.brand.service.BrandAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Brand", description = "Brand API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/brands")
public class BrandApiController {

    private final BrandAdminService brandAdminService;

    @GetMapping("/{id}")
    @Operation(summary = "Read One Brand", description = "브랜드 정보 단건 조회")
    public ResponseEntity<BrandResponse> getBrand(@PathVariable Long id) {
        Optional<Brand> optional = Optional.ofNullable(brandAdminService.findById(id));

        return optional.map(brand -> {
            BrandResponse responseDto = BrandResponse.of(brand);
            return ResponseEntity.ok(responseDto);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Read All Brands", description = "브랜드 정보 전체 조회")
    public ResponseEntity<List<BrandResponse>> getBrands() {
        List<Brand> brands = brandAdminService.findAll();
        List<BrandResponse> brandDtos = brands.stream()
                .map(BrandResponse::of)
                .collect(Collectors.toList());

        return Optional.of(brandDtos)
                .filter(list -> !list.isEmpty())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).body(brandDtos));
    }

    @GetMapping("style-points/{id}")
    @Operation(summary = "Read All Brands By StylePointId", description = "스타일포인트 ID에 해당하는 브랜드 목록 전체 조회")
    public ResponseEntity<List<BrandResponse>> getBrandsByStylePointId(@PathVariable Long id) {
        List<Brand> brands = brandAdminService.findByStylePointId(id);
        List<BrandResponse> brandDtos = brands.stream()
                .map(BrandResponse::of)
                .collect(Collectors.toList());

        return Optional.of(brandDtos)
                .filter(list -> !list.isEmpty())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).body(brandDtos));
    }
}
