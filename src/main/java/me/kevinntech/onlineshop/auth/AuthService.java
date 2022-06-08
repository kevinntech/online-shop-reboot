package me.kevinntech.onlineshop.auth;

import lombok.RequiredArgsConstructor;
import me.kevinntech.onlineshop.user.User;
import me.kevinntech.onlineshop.user.UserDto;
import me.kevinntech.onlineshop.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean signUp(UserDto userDto) {
        return saveNewUser(userDto);
    }

    private boolean saveNewUser(UserDto userDto) {
        if (userDto == null) {
            return false;
        }

        User user = userDto.toEntity();
        user.changePassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return true;
    }

}
