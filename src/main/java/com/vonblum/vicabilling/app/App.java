package com.vonblum.vicabilling.app;

import com.vonblum.vicabilling.app.config.AppConstant;
import com.vonblum.vicabilling.app.data.exception.ProgramException;
import com.vonblum.vicabilling.app.data.model.Workspace;
import com.vonblum.vicabilling.app.service.ObjectSerializer;
import com.vonblum.vicabilling.app.service.ViewPathBuilder;
import com.vonblum.vicabilling.app.tool.AppFileSystem;
import com.vonblum.vicabilling.app.tool.ContextLoader;
import com.vonblum.vicabilling.app.tool.ResourceBundleHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Application initializing class.
 *
 * @author Ricard P. Barnes
 */
public class App extends Application {

    private ResourceBundle resourceBundle;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        AppFileSystem appFileSystem = new AppFileSystem();
        appFileSystem.checkAppFileSystem();
        appFileSystem.createFolderStructure();

        ResourceBundleHandler resourceBundleHandler = new ResourceBundleHandler();
        resourceBundleHandler.setAppResourceBundle();
        resourceBundle = Context.getResourceBundle();

        ObjectSerializer serializer = new ObjectSerializer();
        ContextLoader contextLoader = new ContextLoader(serializer);
        contextLoader.loadAll();
    }

    @Override
    public void start(@NotNull Stage primaryStage) {
        Context.setPrimaryStage(primaryStage);
        Parent root = null;

        String entitySimpleName = Workspace.class.getSimpleName();
        URL fxmlView = ViewPathBuilder.getViewURLFromEntity(entitySimpleName, this);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(fxmlView);
        fxmlLoader.setResources(resourceBundle);

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            ProgramException.showDefaultError();
            e.printStackTrace();
            System.exit(1);
        }

        if (root != null) {
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Context.getCss());

            primaryStage.setTitle(Context.getWorkspaceController().getWorkspace().getTitleName(resourceBundle));
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(AppConstant.BAR_ICON_RES)));
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setOnCloseRequest(event -> Platform.exit());
            primaryStage.show();
        } else {
            ProgramException.showDefaultError();
            System.exit(1);
        }
    }

    @Override
    public void stop() {
        System.out.println("Application closed.");
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }
}
