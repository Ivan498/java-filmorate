package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.model.key.UserFriendPK;

import javax.persistence.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "USER_FRIEND")
@IdClass(UserFriendPK.class)
public class UserFriend {

    @Id
    @Column(name = "USER_ID")
    private Integer userId;

    @Id
    @Column(name = "FRIEND_ID")
    private Integer friendId;

    @OneToOne(mappedBy = "userFriend", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "FRIEND_ID", referencedColumnName = "ID")
    private User friend;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @JsonBackReference
    private User user;

}
