package models;

public interface Session {

    StateValue getValueState();
    boolean isBlocked();
    String getName();
    void setName(String name);


}
