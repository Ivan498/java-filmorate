package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.JpaUserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    JpaUserRepository userRepository;

    @Autowired
    public UserService(JpaUserRepository jpaUserRepository) {
        this.userRepository = jpaUserRepository;
    }

    private void replaceVoidNameByLogin(User user) {
        if (user.getName().isEmpty() || user.getName() == null) {
            user.setName(user.getLogin());
        }
    }

    public Collection<User> getUser() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        replaceVoidNameByLogin(user);
        userRepository.save(user);
        return user;
    }

    public User updateUser(User user) {
        replaceVoidNameByLogin(user);
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.save(user);
        return user;
    }

    public Optional<User> findByIdUser(Integer id) {
        return userRepository.findById(id);
    }

    public void addFriend(Integer id, Integer friendId) {
        if (!userRepository.existsById(id) || !userRepository.existsById(friendId)) {
            throw new NotFoundException("User or Friend not found");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new NotFoundException("Friend not found"));

        List<User> userFriends = user.getFriends();
        userFriends.add(friend);
        userRepository.save(user);
    }


    public void deleteFriend(Integer id, Integer friendId) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new NotFoundException("Friend not found"));
        List<User> userFriends = user.getFriends();
        if (userFriends.contains(friend)) {
            userFriends.remove(friend);
            userRepository.save(user);
            log.info("Deleted Friend: " + friend);
        } else {
            log.info("Этот пользователь не является другом");
        }
    }

    public List<User> getAllFriendsByUserId(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        List<User> friends = user.getFriends();

        log.info("Показали всех друзей Юзера по id " + id);
        return friends;
    }

    public List<User> getAllCommonFriends(Integer id, Integer otherId) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        User otherUser = userRepository.findById(otherId)
                .orElseThrow(() -> new NotFoundException("Other user not found"));

        List<User> userFriends = user.getFriends();
        List<User> otherUserFriends = otherUser.getFriends();

        List<User> commonFriends = userFriends.stream()
                .filter(otherUserFriends::contains)
                .collect(Collectors.toList());

        log.info("Показали всех общих друзей Юзера по id " + id + " и " + otherId);
        return commonFriends;
    }
}


