package pk.webdoor.fx.mvp.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pk.webdoor.fx.mvp.gui.presenters.MainPresenter;

public class WebdoorApp extends Application {

    private static final Logger log = Logger.getLogger(WebdoorApp.class.getName());
    private Stage primaryStage;

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        log.info("Starting Webdoor Application");
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Webdoor Application");

        //WebdoorAppFactory factory = new WebdoorAppFactory();
        //MainPresenter mainPresenter = factory.getMainPresenter();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebdoorAppFactorySpring.class);
        MainPresenter mainPresenter = context.getBean(MainPresenter.class);
        mainPresenter.configureApp();
        Scene scene = new Scene(mainPresenter.getView());
        scene.getStylesheets().add("/resources/webdoor.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Webdoor Customer Care");
        primaryStage.show();

    }

    /**
     * Returns the main stage.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
