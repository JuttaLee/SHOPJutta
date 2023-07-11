package ShopJutta.controller;

import ShopJutta.dto.BasketDetailDto;
import ShopJutta.dto.BasketItemDto;
import ShopJutta.dto.BasketOrderDto;
import ShopJutta.service.BasketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;

    @PostMapping(value = "/basket")
    public @ResponseBody ResponseEntity select(@RequestBody @Valid BasketItemDto basketItemDto, BindingResult bindingResult,
                                               Principal principal) {
        if(bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for(FieldError fieldError : fieldErrors) {
                stringBuilder.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(stringBuilder.toString(), HttpStatus.BAD_REQUEST);
        }
        String email = principal.getName();
        Long basketItemId;

        try {
            basketItemId = basketService.addBasket(basketItemDto, email);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(basketItemId, HttpStatus.OK);
    }
    @GetMapping(value = "/basket")
    public String selectHist(Principal principal, Model model) {
        List<BasketDetailDto> basketDetailList = basketService.getBasketList(principal.getName());
        model.addAttribute("basketItems", basketDetailList);
        return "basket/basketList";
    }

    @PatchMapping(value = "/basketItem/{basketItemId}")
    public @ResponseBody ResponseEntity updateBasketItem(@PathVariable("basketItemId") Long basketItemId, int count,
                                                         Principal principal) {
        if(count <= 0){
            return new ResponseEntity<String>("최소 1개의 동호회를 선택해주세요.", HttpStatus.BAD_REQUEST);
        } else if (!basketService.validateBasketItem(basketItemId, principal.getName())) {
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        basketService.updateBasketItemCount(basketItemId, count);
        return  new ResponseEntity<Long>(basketItemId, HttpStatus.OK);
    }

    @DeleteMapping(value = "/basketItem/{basketItemId}")
    public @ResponseBody ResponseEntity deleteBasketItem(@PathVariable("basketItemId") Long basketItemId,
                                                         Principal principal) {
        if(!basketService.validateBasketItem(basketItemId, principal.getName())){
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        basketService.deleteBasketItem(basketItemId);
        return new ResponseEntity<Long>(basketItemId, HttpStatus.OK);
    }
    @PostMapping(value = "/basket/orders")
    private @ResponseBody ResponseEntity selectBasketItem(@RequestBody BasketOrderDto basketOrderDto, Principal principal){
        List<BasketOrderDto> basketOrderDtoList = basketOrderDto.getBasketOrderDtoList();
        if(basketOrderDtoList == null || basketOrderDtoList.size() == 0) {
            return new ResponseEntity<String>("참석할 동호회를 선택하세요.", HttpStatus.FORBIDDEN);
        }
        for (BasketOrderDto basketOrder : basketOrderDtoList) {
            if(!basketService.validateBasketItem(basketOrder.getBasketItemId(), principal.getName())) {
                return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
            }
        }
        Long selectId = basketService.orderBasketItem(basketOrderDtoList, principal.getName());
        return new ResponseEntity<Long>(selectId, HttpStatus.OK);
    }
}