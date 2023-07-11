package ShopJutta.service;

import ShopJutta.dto.BasketDetailDto;
import ShopJutta.dto.BasketItemDto;
import ShopJutta.dto.BasketOrderDto;
import ShopJutta.dto.SelectDto;
import ShopJutta.entity.Basket;
import ShopJutta.entity.BasketItem;
import ShopJutta.entity.Good;
import ShopJutta.entity.Join;
import ShopJutta.repository.BasketItemRepository;
import ShopJutta.repository.BasketRepository;
import ShopJutta.repository.GoodRepository;
import ShopJutta.repository.JoinRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BasketService {
    private final GoodRepository goodRepository;
    private final JoinRepository joinRepository;
    private final BasketRepository basketRepository;
    private final BasketItemRepository basketItemRepository;
    private final SelectService selectService;

    public Long addBasket(BasketItemDto basketItemDto, String email) {
        // 상품 조회
        Good good = goodRepository.findById(basketItemDto.getBasketId()).orElseThrow(EntityNotFoundException::new);

        // 현재 로그인한 회원 조회
        Join join = joinRepository.findByEmsil(email);

        if(basket == null) {
            basket = Basket.createBasket(join);
            basketRepository.save(basket);
        }
        BasketItem savedBasketItem = basketItemRepository.findByBasketIdAndGoodId(basket.getId(), good.getId());

        if (savedBasketItem != null) {
            savedBasketItem.addBasket(basketItemDto.getBasketCount());
            return savedBasketItem.getId();
        } else {
            BasketItem basketItem = BasketItem.createBasketItem(basket, good, basketItemDto.getBasketCount());
            basketItemRepository.save(basketItem);
            return basketItem.getId();
        }
    }

    @Transactional(readOnly = true)
    public List<BasketDetailDto> getBasketList(String email) {
        List<BasketDetailDto> basketDetailDtoList = new ArrayList<>();

        Join join = joinRepository.findByEmail(email);
        Basket basket = basketRepository.findByMemberId(join.getId());
        if(basket == null) {
            return basketDetailDtoList;
        }
        basketDetailDtoList = basketItemRepository.findBasketDtoList(basket.getId());
        return basketDetailDtoList;
    }
    @Transactional(readOnly = true)
    public boolean validateBasketItem (Long basketItemId, String email) {
        Join curJoin = joinRepository.findByEmail(email);
        BasketItem basketItem = basketItemRepository.findById(basketItemId).orElseThrow(EntityNotFoundException::new);
        Join savedJoin = basketItem.getBasket().getJoin();
        if(!StringUtils.equals(curJoin.getEmail(), savedJoin.getEmail())) {
            return false;
        }
        return true;
    }

    public void updateBasketItemCount(Long basketItemId, int count) {
        BasketItem basketItem = basketItemRepository.findById(basketItemId).orElseThrow(EntityNotFoundException::new);
        basketItem.updateCount(count);
    }
    public void deleteBasketItem(Long basketItemId) {
        BasketItem basketItem = basketItemRepository.findById(basketItemId).orElseThrow(EntityNotFoundException::new);
        basketItemRepository.delete(basketItem);
    }

    public Long orderBasketItem(List<BasketOrderDto> basketOrderDtoList, String email) {
        List<SelectDto> selectDtoList = new ArrayList<>();

        for(BasketOrderDto basketOrderDto : basketOrderDtoList) {
            BasketItem basketItem = basketItemRepository.findById(basketOrderDto.getBasketItemId())
                    .orElseThrow(EntityNotFoundException::new);

            SelectDto selectDto = new SelectDto();
            selectDto.setGoodId(basketItem.getBasket().getId());
            selectDto.setCount(basketItem.getCount());
            selectDtoList.add(selectDto);
        }

        Long selectId = selectService.select((SelectDto) selectDtoList, email);
        for(BasketOrderDto basketOrderDto : basketOrderDtoList) {
            BasketItem basketItem = basketItemRepository
                    .findById(basketOrderDto.getBasketItemId())
                    .orElseThrow(EntityNotFoundException::new);
            basketItemRepository.delete(basketItem);
        }
        return selectId;
    }
    }
}