package ShopJutta.controller;

import ShopJutta.dto.GoodFormDto;
import ShopJutta.dto.GoodSearchDto;
import ShopJutta.entity.Good;
import ShopJutta.service.GoodService;
import ch.qos.logback.core.model.Model;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class GoodController {
    private final GoodService goodService;

    @GetMapping(value = "/admin/good/new")
    public String goodForm(Model model) {
        model.addAttribute("goodFormDto", new GoodFormDto());
        return "good/goodForm";
    }

    @PostMapping(value = "/admin/good/new")
    public String goodNew(@Valid GoodFormDto goodFormDto, BindingResult bindingResult, Model model,
                          @RequestParam("goodImgFile") List<MultipartFile> goodImgFileList) {
        if (bindingResult.hasErrors()) {
            return "good/goodForm";
        }
        try {
            goodService.saveGood(goodFormDto, goodImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "동호회 등록 중 에러가 발생하였습니다.");
            return "good/goodForm";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/admin/good/{goodId}")
    public String goodDtl(@PathVariable("goodId") Long goodId, Model model) {
        try {
            GoodFormDto goodFormDto = goodService.getGoodDtl(goodId);
            model.addAttribute("goodFormDto", goodFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않은 동호회 입니다");
            model.addAttribute("goodFormDto", new GoodFormDto());
            return "good/goodForm";
        }
        return "good/goodForm";
    }
    @PostMapping(value = "/admin/good/{goodId}")
    public String goodUpdate(@Valid GoodFormDto goodFormDto, BindingResult bindingResult, @RequestParam("goodImgFile")
    List<MultipartFile> goodImgFileList, Model model) {
        if(bindingResult.hasErrors()) {
            return "good/goodForm";
        }
        if(goodImgFileList.get(0).isEmpty() && goodFormDto.getId() == null) {
            model.addAttribute("errorMessage", "동호회 이미지는 필수입니다.");
            return "good/goodForm";
        }
        try {
            goodService.updateGood(goodFormDto, goodImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "동호회 수정 중 에러가 발생했습니다.");
            return "good/goodForm";
        }
        return "redirect:/";
    }
    @GetMapping(value = {"/admin/goods", "/admin/goods/{page}"})
    public String goodManage(GoodSearchDto goodSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<Good> goods = goodService.getAdminGoodPage(goodSearchDto, pageable);

        model.addAttribute("items", goods);
        model.addAttribute("itemSearchDto", goodSearchDto);
        model.addAttribute("maxPage", 5);

        return "good/goodMng";
    }

    @GetMapping(value = "/good/{goodId}")
    public String goodDtl(Model model, @PathVariable("goodId") Long goodId) {
        GoodFormDto goodFormDto = goodService.getGoodDtl(goodId);
        model.addAttribute("good", goodFormDto);
        return "good/goodDtl";
    }
}