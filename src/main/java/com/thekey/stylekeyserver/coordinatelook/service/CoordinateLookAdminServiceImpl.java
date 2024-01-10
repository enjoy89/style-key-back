package com.thekey.stylekeyserver.coordinatelook.service;

import com.thekey.stylekeyserver.coordinatelook.CoordinateLookErrorMessage;
import com.thekey.stylekeyserver.coordinatelook.domain.CoordinateLook;
import com.thekey.stylekeyserver.coordinatelook.dto.CoordinateLookDto;
import com.thekey.stylekeyserver.coordinatelook.repository.CoordinateLookRepository;
import com.thekey.stylekeyserver.item.ItemErrorMessage;
import com.thekey.stylekeyserver.item.domain.Item;
import com.thekey.stylekeyserver.item.dto.ItemDto;
import com.thekey.stylekeyserver.item.service.ItemAdminService;
import com.thekey.stylekeyserver.stylepoint.domain.StylePoint;
import com.thekey.stylekeyserver.stylepoint.service.StylePointAdminService;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CoordinateLookAdminServiceImpl implements CoordinateLookAdminService {

    private final CoordinateLookRepository coordinateLookRepository;
    private final StylePointAdminService stylePointAdminService;
    private final ItemAdminService itemAdminService;

    @Override
    public CoordinateLook create(CoordinateLookDto requestDto) {
        StylePoint stylePoint = stylePointAdminService.findById(requestDto.getStylePointId());
        CoordinateLook savedCoordinateLook = coordinateLookRepository.save(requestDto.toEntity(stylePoint));

        if (requestDto.getItems() != null) {
            List<Item> items = new ArrayList<>();
            for (ItemDto itemDto : requestDto.getItems()) {
                Item createdItem = itemAdminService.create(itemDto);
                items.add(createdItem);
            }

            for (Item item : items) {
                savedCoordinateLook.addItem(item);
            }
        }
        return savedCoordinateLook;
    }

    @Override
    public CoordinateLook findById(Long id) {
        return coordinateLookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        CoordinateLookErrorMessage.NOT_FOUND_COORDINATE_LOOK.get() + id));
    }

    @Override
    public List<CoordinateLook> findAll() {
        return coordinateLookRepository.findAll();
    }

    @Override
    public List<CoordinateLook> findByStylePointId(Long id) {
        return coordinateLookRepository.findByStylePointId(id);
    }

    @Override
    public CoordinateLook update(Long id, CoordinateLookDto requestDto) {
        CoordinateLook coordinateLook = coordinateLookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        CoordinateLookErrorMessage.NOT_FOUND_COORDINATE_LOOK.get() + id));
        StylePoint stylePoint = stylePointAdminService.findById(requestDto.getStylePointId());

        coordinateLook.update(
                requestDto.getTitle(),
                requestDto.getImage(),
                requestDto.toEntity(stylePoint).getStylePoint());

        if (requestDto.getItems() != null) {
            for (ItemDto itemRequestDto : requestDto.getItems()) {
                itemAdminService.update(itemRequestDto.getId(), itemRequestDto);
            }
        }

        return coordinateLook;
    }

    @Override
    public void deleteById(Long id) {
        coordinateLookRepository.deleteById(id);
    }

    @Override
    public void deleteItemFromCoordinateLook(Long coordinateLookId, Long itemId) {
        CoordinateLook coordinateLook = coordinateLookRepository.findById(coordinateLookId)
                .orElseThrow(() -> new EntityNotFoundException(
                        CoordinateLookErrorMessage.NOT_FOUND_COORDINATE_LOOK.get() + coordinateLookId));

        List<Item> items = coordinateLook.getItems();

        try {
            Item itemToDelete = items.stream()
                    .filter(item -> item.getId().equals(itemId))
                    .findFirst()
                    .orElseThrow();

            itemAdminService.delete(itemToDelete.getId());
            items.remove(itemToDelete);

            // 코디룩에 속한 아이템이 없을 경우, 코디룩도 함께 삭제한다.
            if (coordinateLook.getItems().isEmpty()) {
                coordinateLookRepository.delete(coordinateLook);
            }
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException(ItemErrorMessage.NOT_FOUND_ITEM.get() + itemId);
        }
    }

    @Override
    public CoordinateLookDto convertToDto(CoordinateLook coordinateLook) {
        return CoordinateLookDto.builder()
                .id(coordinateLook.getId())
                .title(coordinateLook.getTitle())
                .image(coordinateLook.getImage())
                .stylePointId(coordinateLook.getStylePoint().getId())
                .build();
    }

    @Override
    public CoordinateLookDto convertToResponseDto(CoordinateLook coordinateLook) {
        List<ItemDto> itemResponseDtos = convertItemsToDtoList(coordinateLook.getItems());

        return CoordinateLookDto.builder()
                .id(coordinateLook.getId())
                .title(coordinateLook.getTitle())
                .image(coordinateLook.getImage())
                .stylePointId(coordinateLook.getStylePoint().getId())
                .items(itemResponseDtos)
                .build();
    }

    private List<ItemDto> convertItemsToDtoList(List<Item> items) {
        return items.stream()
                .map(item -> ItemDto.builder()
                        .id(item.getId())
                        .title(item.getTitle())
                        .sales_link(item.getSales_link())
                        .image(item.getImage())
                        .brandId(item.getBrand().getId())
                        .categoryId(item.getCategory().getId())
                        .build())
                .collect(Collectors.toList());
    }

}
