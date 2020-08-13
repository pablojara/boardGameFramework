package models;

import models.utils.Registry;

public class SessionImplementation implements Session{

    private State state;
    private Registry registry;
    private Game game;
    private String name;

    public SessionImplementation() {    }
    @Override
    public StateValue getValueState() {
        return this.state.getValueState();
    }
    public void setStateValue(StateValue value) {
        this.state.setStateValue(value);
    }
    @Override
    public boolean isBlocked() {
        return this.game.isBlocked();
    }
    public void resetRegistry() {
        this.registry.reset();
    }
    public Game getGame() {
        return this.game;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }




}
