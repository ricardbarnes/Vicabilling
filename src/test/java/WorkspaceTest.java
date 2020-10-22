import com.vonblum.vicabilling.app.data.model.Workspace;
import com.vonblum.vicabilling.app.service.ObjectSerializer;
import org.junit.Test;

public class WorkspaceTest {

    private void showTestMessage(String message) {
        System.out.println("\n------------\n" + message + "\n------------\n");
    }

    @Test
    public void showSavedWorkspace() {
        String billNumberStr = "1";
        String message = "EMPTY";
        ObjectSerializer serializer = new ObjectSerializer();
        Workspace workspace = (Workspace) serializer.deserializeObject(
                Workspace.getBaseFilepath() +
                        billNumberStr
                        + Workspace.FILE_EXT
        );

        if (workspace != null) {
            message = workspace.getBill().toString();
        }

        showTestMessage(message);
    }

}
