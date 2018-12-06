package weber.kaden.common.injectedInterfaces.persistence;

import java.util.List;

import weber.kaden.common.model.Player;

public interface UserDao {
    boolean save(List<Player> users);
    List<Player> getUsers();
}
