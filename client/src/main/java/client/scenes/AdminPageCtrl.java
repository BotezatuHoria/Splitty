package client.scenes;

import client.utils.ServerUtils;
import jakarta.inject.Inject;

public class AdminPageCtrl {

    private final MainCtrl mainCtrl;
    private final ServerUtils serverUtils;

    /**
     * Constructor for the admin page controller.
     * @param server the server
     * @param mainCtrl the main controller
     */
    @Inject
    public AdminPageCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.serverUtils = server;
        this.mainCtrl = mainCtrl;
    }

    public void goBack() {
        mainCtrl.showAdminLogin();
    }
}
