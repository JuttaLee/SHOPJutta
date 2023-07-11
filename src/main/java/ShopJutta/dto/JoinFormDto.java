package ShopJutta.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


@Getter
@Setter
public class JoinFormDto {
    @NotEmpty(message = "이메일")
    @Email(message = "사용하실 수 있는 이메일입니다.")
    private String email;

    @NotEmpty(message = "비밀번호")
    @Length(min = 8, max = 30, message = "사용하실 수 있는 비밀번호 입니다.")
    private String password;

    @NotEmpty(message = "비밀번호")
    @Length(min = 8, max = 30, message = "비밀번호가 일치합니다.")
    private String password_confirm;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotEmpty(message = "이메일 인증")
    @Email(message = "이메일을 입력해주세요.")
    private String email_confirm;
}