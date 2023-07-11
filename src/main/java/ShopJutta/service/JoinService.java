package ShopJutta.service;

import ShopJutta.entity.Join;
import ShopJutta.repository.JoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class JoinService implements UserDetailsService {
    private final JoinRepository joinRepository;

    public Join saveJoin(Join join) {
        validateDuplicateJoin(join);
        return joinRepository.save(join);
    }

    private void validateDuplicateJoin(Join join) {
        Join findJoin = joinRepository.findByEmail(join.getEmail());
        if(findJoin != null) {
            throw new IllegalStateException("이미 가입되어 있는 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Join join = joinRepository.findByEmail(email);

        if(Join == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(join.getEmail())
                .password(join.getPassword())
                .roles(join.getRole().toString())
                .build();
    }
}