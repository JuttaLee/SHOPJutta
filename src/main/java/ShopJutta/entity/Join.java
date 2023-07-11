package ShopJutta.entity;

import ShopJutta.constant.Role;
import ShopJutta.dto.JoinFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "joins")
@Getter
@Setter
@ToString
public class Join extends BaseEntity {
    @Id
    @Column(name = "join_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;
    private String password_confirm;
    private String name;
    private String email_confirm;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Join createJoin(JoinFormDto joinFormDto, PasswordEncoder passwordEncoder) {
        Join join = new Join();
        join.setEmail(joinFormDto.getEmail());
        join.setName(joinFormDto.getName());
        String password = passwordEncoder.encode(joinFormDto.getPassword());
        join.setPassword(password);
        join.setPassword_confirm(password);
        join.setEmail_confirm(join.email_confirm);
        join.setRole(Role.USER);
        return join;
    }
}