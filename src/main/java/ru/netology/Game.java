package ru.netology;

import ru.netology.domain.Player;
import ru.netology.exception.NotRegisteredException;

import java.util.HashMap;
import java.util.Map;

public class Game {
//    private List<Player> players = new ArrayList<>();       //хранилище всех пользователей

    private Map<String, Player> players = new HashMap<>();
    //метод регистрации, т.е. добавления игрока
    public void register(Player player) {
        this.players.put(player.getName(), player);
    }

    private Player findPlayer(String name) {
        return players.get(name);
    }

    //метод соревнования между двумя игроками
    public int round(String playerName1, String playerName2) {
        Player firstPlayer = findPlayer(playerName1);
        Player secondPlayer = findPlayer(playerName2);

        if (firstPlayer == null || secondPlayer == null) {
            throw new NotRegisteredException("One or both players not registered");
        }

        int result = firstPlayer.getStrength() - secondPlayer.getStrength();
        if (result > 0) {
            return 1;
        } else if (result < 0) {
            return 2;
        }
        return 0;
    }
}
