package ShopJutta.controller;

import ShopJutta.dto.SelectDto;
import ShopJutta.dto.SelectHistDto;
import ShopJutta.service.SelectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class SelectController {
    private final SelectService selectService;

    @PostMapping(value = "/select")
    public @ResponseBody ResponseEntity select(@RequestBody @Valid SelectDto selectDto, BindingResult bindingResult,
                                               Principal principal) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        String email = principal.getName();
        Long selectId;

        try {
            selectId = selectService.select(selectDto, email);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(selectId, HttpStatus.OK);
    }

    @GetMapping(value = {"/selects", "selects/{page}"})
    public String selectHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<SelectHistDto> selectHistDtoList = selectService.getSelectList(principal.getName(), pageable);

        model.addAttribute("selects", selectHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "select/selectHist";
    }

    @PostMapping("/select/{selectId}/cancel")
    public @ResponseBody ResponseEntity cancelSelect(@PathVariable("selectId") Long selectId, Principal principal) {
        if (!selectService.validateSelect(selectId, principal.getName())) {
            return new ResponseEntity<String>("선택한 동호회를 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        selectService.cancelSelect(selectId);
        return new ResponseEntity<Long >(selectId, HttpStatus.OK);
    }
}
