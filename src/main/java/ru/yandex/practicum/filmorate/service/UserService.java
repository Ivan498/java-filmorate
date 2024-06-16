package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.dao.FriendRepository;
import ru.yandex.practicum.filmorate.dao.UserRepository;

import java.util.List;

@Service
public class UserService {
    UserRepository userStorage;
    FriendRepository friendRepository;

    @Autowired
    public UserService(@Qualifier("jdbcUserRepository") UserRepository userStorage, FriendRepository friendRepository) {
        this.userStorage = userStorage;
        this.friendRepository = friendRepository;
    }

    public void addFriend(Long userId, Long friendId) {
        if (userStorage.isExists(userId) && userStorage.isExists(friendId)) {
            friendRepository.addFriend(userId, friendId);
        }
    }

    public void deleteFriend(Long userId, Long friendId) {
        if (userStorage.isExists(userId) && userStorage.isExists(friendId)) {
            friendRepository.deleteFriend(userId, friendId);
        }
    }

    public List<User> getFriends(Long id) {
        userStorage.isExists(id);

        return friendRepository.getFriends(id);
    }

    public List<User> getCommonFriends(Long userId, Long friendId) {
        userStorage.isExists(userId);
        userStorage.isExists(friendId);

        return friendRepository.getCommonFriends(userId, friendId);
    }

    public User createUser(User value) {
        return userStorage.create(value);
    }

    public User updateUser(User value) {
        return userStorage.update(value);
    }

    public List<User> getAllUser() {
        return userStorage.getAll();
    }

    public User getUserById(Long id) {
        return userStorage.getData(id);
    }
}

