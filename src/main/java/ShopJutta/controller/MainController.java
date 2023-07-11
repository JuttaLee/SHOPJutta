package ShopJutta.controller;

import ShopJutta.dto.GoodSearchDto;
import ShopJutta.dto.MainItemDto;
import ShopJutta.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final GoodService goodService;

    @GetMapping
    public String main(GoodSearchDto goodSearchDto, Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainItemDto> goods = goodService.getMainItemPage(goodSearchDto, pageable);

        model.addAttribute("goods", goods);
        model.addAttribute("goodSearchDto", goodSearchDto);
        model.addAttribute("maxPage", 5);

        return "main";
    }
}
