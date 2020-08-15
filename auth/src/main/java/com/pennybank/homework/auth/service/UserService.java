package com.pennybank.homework.auth.service;

import com.pennybank.homework.auth.model.UserDTO;
import com.pennybank.homework.auth.repository.UserRepository;
import com.pennybank.homework.auth.entity.UserEntity;
import com.pennybank.homework.auth.exception.UserAlreadyFoundException;
import com.pennybank.homework.auth.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserDTO create(UserDTO userDTO) {
        log.info("create: Entering userDTO.getUsername(): {}", userDTO.getUsername());
        Optional<UserEntity> optionalPrevUser = userRepository.findByUsername(userDTO.getUsername());
        if (optionalPrevUser.isPresent()) {
            throw new UserAlreadyFoundException();
        }
        userDTO.setId(UUID.randomUUID().toString());
        userDTO.setRoles("ROLE_USER");
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));

        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(userDTO, user);
        userRepository.save(user);
        log.info("Saved user with id: {}", user.getId());

        userDTO.setPassword(null);
        return userDTO;
    }

    public UserDTO get(String id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException();
        }
        UserDTO userDTO = new UserDTO();
        UserEntity user = optionalUser.get();
        log.info("Found user with id: {}", user.getId());
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
}
