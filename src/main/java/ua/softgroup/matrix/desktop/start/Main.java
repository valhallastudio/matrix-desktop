package ua.softgroup.matrix.desktop.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softgroup.matrix.desktop.controllerjavafx.LoginLayoutController;
import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.time.LocalDateTime;


public class Main extends Application {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static ServerSocket socket;

    public static void main(String[] args) {
        logger.debug("Current time: {}", LocalDateTime.now());
        launch(args);
    }

    /**
     * Point of start Application
     * @param primaryStage get default Stage from Application class
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        checkIfRunning();
        startLoginLayout(primaryStage);
    }

    private static void checkIfRunning() {
        try {
            socket = new ServerSocket(8979, 0, InetAddress.getByAddress(new byte[] {127,0,0,1}));
            logger.debug("Bind to localhost adapter with a zero connection queue");
        } catch (IOException e) {
            logger.debug("App already running");
            //TODO: remove line below, tell user to free 8979 port
            System.exit(1);
        }
    }

    /**
     * Tells {@link Main} to open login window
     * @param loginStage for create stage
     */
    public void startLoginLayout(Stage loginStage) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(classLoader.getResource("fxml/loginLayout.fxml"));
            Pane loginLayout = loader.load();
            LoginLayoutController loginLayoutController = loader.getController();
            loginLayoutController.setUpStage(loginStage);
            Scene scene = new Scene(loginLayout);
            loginStage.setScene(scene);
            loginStage.setTitle("SuperVisor");
            loginStage.setResizable(false);
            loginStage.show();
        } catch (IOException e) {
            logger.debug("Error when start Main Window "+e);
        }
    }


}
