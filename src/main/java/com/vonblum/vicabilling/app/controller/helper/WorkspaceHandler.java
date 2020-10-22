package com.vonblum.vicabilling.app.controller.helper;

import com.vonblum.vicabilling.app.Context;
import com.vonblum.vicabilling.app.data.model.BillSettings;
import com.vonblum.vicabilling.app.data.model.Workspace;
import com.vonblum.vicabilling.app.service.ObjectSerializer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ResourceBundle;

public class WorkspaceHandler {

    private final ResourceBundle resourceBundle;

    private FileChooser.ExtensionFilter[] getExtensionFilters() {
        String ktbDescription =
                resourceBundle.getString("App_name") + " " + resourceBundle.getString("Files");
        String ktbRepresentation =
                "*" + Workspace.FILE_EXT;

        String allDescription = resourceBundle.getString("All_files");
        String allFilesRepr = "*";

        return new FileChooser.ExtensionFilter[]{
                new FileChooser.ExtensionFilter(ktbDescription, ktbRepresentation),
                new FileChooser.ExtensionFilter(allDescription, allFilesRepr)
        };
    }

    public WorkspaceHandler(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public void save(Workspace workspace) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(getExtensionFilters());
        fileChooser.setInitialDirectory(new File(Workspace.getBaseFilepath()));
        fileChooser.setInitialFileName(workspace.getSaveFilename(resourceBundle));

        Stage stage = Context.getPrimaryStage();
        File selectedFile = fileChooser.showSaveDialog(stage);

        if (selectedFile != null) {
            ObjectSerializer serializer = new ObjectSerializer();
            serializer.serializeObject(workspace, selectedFile.getPath());
        }
    }

    public Workspace openLoadingDialog() {
        Workspace workspace = null;

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(getExtensionFilters());
        fileChooser.setInitialDirectory(new File(Workspace.getBaseFilepath()));

        Stage stage = Context.getPrimaryStage();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            Context.getWorkspaceController().clearWorkspace();
            workspace = (Workspace) new ObjectSerializer().deserializeObject(selectedFile.getPath());
            if (workspace != null) {
                Context.setBillSettings(BillSettings.extractBillSettings(workspace.getBill()));
            }
        }

        return workspace;
    }

}
