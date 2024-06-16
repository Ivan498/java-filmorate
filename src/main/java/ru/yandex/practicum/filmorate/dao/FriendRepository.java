package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendRepository {

    void addFriend(Long firstId, Long secondId);

    void deleteFriend(Long firstId, Long secondId);

    List<User> getFriends(Long userId);

    List<User> getCommonFriends(Long firstId, Long secondId);
}