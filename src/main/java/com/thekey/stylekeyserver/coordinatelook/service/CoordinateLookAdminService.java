package com.thekey.stylekeyserver.coordinatelook.service;

import com.thekey.stylekeyserver.coordinatelook.entity.CoordinateLook;
import com.thekey.stylekeyserver.coordinatelook.dto.request.CoordinateLookRequest;
import com.thekey.stylekeyserver.coordinatelook.dto.response.CoordinateLookPageResponse;
import com.thekey.stylekeyserver.item.dto.request.ItemRequest;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface CoordinateLookAdminService {

    CoordinateLook create(CoordinateLookRequest requestDto,
                          MultipartFile coordinateLookImageFile,
                          List<MultipartFile> itemImageFiles);

    CoordinateLook findById(Long id);

    List<CoordinateLook> findAll();
    CoordinateLookPageResponse findAllPaging(Pageable pageable);

    List<CoordinateLook> findByStylePointId(Long id);

    CoordinateLook update(Long id, CoordinateLookRequest requestDto,
                          MultipartFile coordinateLookImageFile);

    CoordinateLook updateItem(Long coordinateLookId, Long itemId, ItemRequest requestDto,
                              MultipartFile itemImageFile);

    void delete(Long id);

    void deleteItemFromCoordinateLook(Long coordinateLookId, Long itemId);
}
