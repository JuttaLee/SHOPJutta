package ShopJutta.service;

import ShopJutta.dto.SelectDto;
import ShopJutta.dto.SelectHistDto;
import ShopJutta.dto.SelectItemDto;
import ShopJutta.entity.*;
import ShopJutta.repository.GoodImgRepository;
import ShopJutta.repository.GoodRepository;
import ShopJutta.repository.JoinRepository;
import ShopJutta.repository.SelectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SelectService {
    private final GoodRepository goodRepository;
    private final JoinRepository joinRepository;
    private final SelectRepository selectRepository;
    private final GoodImgRepository goodImgRepository;

    public Long select(SelectDto selectDto, String email) {
        Good good = goodRepository.findById(selectDto.getGoodId()).orElseThrow(EntityNotFoundException::new);
        Join join = joinRepository.findByEmail(email);

        List<SelectItem> selectItemList = new ArrayList<>();
        SelectItem selectItem = SelectItem.createSelectItem(good, selectDto.getCount());
        selectItemList.add(selectItem);

        Select select = Select.createSelect(join, selectItemList);
        selectRepository.save(select);

        return select.getId();
    }

    // 상품 선택 목록 조회
    @Transactional(readOnly = true)
    public Page<SelectHistDto> getSelectList(String email, Pageable pageable) {
        List<Select> selects = selectRepository.findSelect(email, (java.awt.print.Pageable) pageable);
        Long totalCount = selectRepository.countSelect(email);
        List<SelectHistDto> selectHistDtos = new ArrayList<>();

        for(Select select : selects) {
            SelectHistDto selectHistDto = new SelectHistDto(select);
            List<SelectItem> selectItems = select.getSelectItems();
            for (SelectItem selectItem : selectItems) {
                GoodImg goodImg = goodImgRepository.findByGoodIdAndRepImgYn(selectItem.getGood().getId(), "Y");
                SelectItemDto selectItemDto = new SelectItemDto(selectItem, goodImg.getGoodImgUrl());
                selectHistDto.addSelectItemDto(selectItemDto);
            }
            selectHistDtos.add(selectHistDto);
        }
        return new PageImpl<SelectHistDto>(selectHistDtos, pageable, totalCount);
    }

    @Transactional(readOnly = true)
    public boolean validateSelect(Long selectId, String email) {
        Join curJoin = joinRepository.findByEmail(email);
        Select select = selectRepository.findById(selectId).orElseThrow(EntityNotFoundException::new);
        Join savedMember = select.getJoin();

        if(!StringUtils.equals(curJoin.getEmail(), savedMember.getEmail())) {
            return false;
        }
        return true;
    }
    public void cancelSelect(Long selectId) {
        Select select = selectRepository.findById(selectId).orElseThrow(EntityNotFoundException::new);
        select.cancelOrder();
    }

    public Long selects(List<SelectDto> selectDtoList, String email) {
        Join join = joinRepository.findByEmail(email);
        List<SelectItem> selectItemList = new ArrayList<>();

        for (SelectDto selectDto : selectDtoList) {
            Good good = goodRepository.findById(selectDto.getGoodId()).orElseThrow(EntityNotFoundException::new);

            SelectItem selectItem = SelectItem.createSelectItem(good, selectDto.getCount());
            selectItemList.add(selectItem);
        }

        Select select = Select.createSelect(join, selectItemList);
        selectRepository.save(select);

        return select.getId();
    }
}