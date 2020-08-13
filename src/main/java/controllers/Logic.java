package controllers;

import models.Session;
import models.StateValue;

import java.util.HashMap;
import java.util.Map;

public class Logic {

    protected Session session;

    protected Map<StateValue, AceptorController> acceptorControllers;

    protected Logic() {
        this.acceptorControllers = new HashMap<StateValue, AceptorController>();

	}

	public AceptorController getController() {
        return this.acceptorControllers.get(this.session.getValueState());
    }

}
