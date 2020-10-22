import com.vonblum.vicabilling.app.data.model.Bill;
import com.vonblum.vicabilling.app.data.model.BillSettings;
import com.vonblum.vicabilling.app.data.model.Customer;
import com.vonblum.vicabilling.app.service.ObjectSerializer;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class BillSettingsTest {

    public static final String EMPTY_OBJECT_MESSAGE = "EMPTY";

    private String message;
    private BillSettings billSettings;

    private void showTestMessage(String message) {
        System.out.println("\n------------\n" + message + "\n------------\n");
    }

    private boolean isBillSettingsNull() {
        return billSettings != null;
    }

    @Before
    public void doBefore() {
        message = EMPTY_OBJECT_MESSAGE;
        ObjectSerializer serializer = new ObjectSerializer();
        billSettings = (BillSettings) serializer.deserializeObject(BillSettings.getFilepath());
    }

    @Test
    public void showBillSettingsSavedDate() {
        if (isBillSettingsNull()) {
            Date date = billSettings.getDate();
            message = date.toString();
        }

        showTestMessage(message);
    }

    @Test
    public void showBillSettingsSavedBillType() {
        if (isBillSettingsNull()) {
            Bill.Type billType = billSettings.getType();
            message = billType.name();
        }

        showTestMessage(message);
    }

    @Test
    public void showSavedCustomer() {
        if (isBillSettingsNull()) {
            Customer customer = billSettings.getCustomer();
            message = customer.toString();
        }

        showTestMessage(message);
    }

}
