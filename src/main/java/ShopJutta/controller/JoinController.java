package ShopJutta.controller;

import ShopJutta.dto.JoinFormDto;
import ShopJutta.entity.Join;
import ShopJutta.service.JoinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/join")
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String joinForm(Model model) {
        model.addAttribute("joinFormDto", new JoinFormDto());
        return "join/joinForm";     // 회원가입
    }

    @PostMapping(value = "/new")
    public String newJoin(@Valid JoinFormDto joinFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "join/joinForm"; // 회원가입
        }
        try {
            Join join = Join.createJoin(joinFormDto, passwordEncoder);
            joinService.saveJoin(join);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "join/joinForm";     // 회원가입
        }
        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String loginJoin() {
        return "/join/joinLoginForm";   // 로그인
    }
}
