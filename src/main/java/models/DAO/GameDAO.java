package models.DAO;

import com.pablo.annotations.DAOSerializer;
import models.Game;

import java.io.BufferedReader;
import java.io.IOException;

class GameDAO {

    private Game game;

    GameDAO(Game game) {
        this.game = game;
    }

    public void save() {
        try {
            DAOSerializer.serialize(this.game);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load(BufferedReader bufferedReader) {
        try {
            this.game = (Game)DAOSerializer.deserialize(this.game);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
