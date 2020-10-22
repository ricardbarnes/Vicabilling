package com.vonblum.vicabilling.app.controller;

import com.vonblum.vicabilling.app.Context;
import com.vonblum.vicabilling.app.data.exception.ProgramException;
import com.vonblum.vicabilling.app.data.model.*;
import com.vonblum.vicabilling.app.service.DateTool;
import com.vonblum.vicabilling.app.service.MathTool;
import com.vonblum.vicabilling.app.service.ViewPathBuilder;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class BillSettingsController extends AnchorPane implements Initializable {

    private ResourceBundle resourceBundle;
    private ToggleGroup billTypeGroup;
    private String budgetTypeId;
    private String receiptTypeId;

    @FXML
    private ComboBox<Currency> currencyBox;

    @FXML
    private DatePicker billDate;

    @FXML
    private Label dateLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private Label taxLabel;

    @FXML
    private RadioButton receiptRadioBtn;

    @FXML
    private RadioButton budgetRadioBtn;

    @FXML
    private Spinner<Integer> numberSpinner;

    @FXML
    private Spinner<Double> taxSpinner;

    @FXML
    private TextArea conditionsArea;

    private Bill.Type getBillType() {
        Bill.Type billType;

        RadioButton selectedRadioButton = (RadioButton) billTypeGroup.getSelectedToggle();
        String radioButtonId = selectedRadioButton.getId();

        if (radioButtonId.equals(receiptTypeId)) {
            billType = Bill.Type.INVOICE;
        } else {
            billType = Bill.Type.BUDGET;
        }

        return billType;
    }

    private void setBillTypeRadioButtonConfig() {
        budgetTypeId = Bill.Type.BUDGET.name();
        receiptTypeId = Bill.Type.INVOICE.name();

        budgetRadioBtn.setId(budgetTypeId);
        receiptRadioBtn.setId(receiptTypeId);

        billTypeGroup = new ToggleGroup();
        budgetRadioBtn.setToggleGroup(billTypeGroup);
        receiptRadioBtn.setToggleGroup(billTypeGroup);
    }

    private void setCurrencyBoxConfig() {
        Set<Currency> currencySet = Currency.getAvailableCurrencies();
        Currency[] currencies = currencySet.toArray(new Currency[0]);
        Arrays.sort(currencies, Comparator.comparing(Currency::getDisplayName));

        currencyBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Currency object) {
                return object.getDisplayName(Locale.getDefault());
            }

            @Override
            public Currency fromString(String string) {
                return null;
            }
        });
        currencyBox.setItems(FXCollections.observableArrayList(currencies));
    }

    private void resetDate() {
        billDate.setValue(LocalDate.now());
    }

    private void resetBillNumber() {
        numberSpinner.getValueFactory().setValue(1);
    }

    private void resetTaxPercent() {
        taxSpinner.getValueFactory().setValue(Tax.getDefaultTax().getCoefficient() * 100);
    }

    private void resetConditions() {
        conditionsArea.setText("");
    }

    private void resetCurrencyBox() {
        currencyBox.setValue(
                Currency.getInstance(new Locale("ca", "ES")) // Current company location (2020)
        );
    }

    private void resetBillType() {
        receiptRadioBtn.setSelected(true);
    }

    public BillSettingsController() {
        try {
            ResourceBundle bundle = Context.getResourceBundle();

            String entitySimpleName = BillSettings.class.getSimpleName();
            URL fxmlView = ViewPathBuilder.getViewURLFromEntity(entitySimpleName, this);

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(fxmlView);
            fxmlLoader.setResources(bundle);
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();

            // Those listeners make sure that the last values of the spinners are collected, and prevent bad inputs.
            // Closing the window without hitting enter after having changed a value makes the value to get lost.
            numberSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
                try {
                    int value = Integer.parseInt(newValue);
                    numberSpinner.getValueFactory().setValue(value);
                } catch (NumberFormatException exception) {
                    numberSpinner.getEditor().setText("");
                }
            });

            taxSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
                try {
                    double value = Double.parseDouble(newValue);
                    taxSpinner.getValueFactory().setValue(value);
                } catch (NumberFormatException exception) {
                    taxSpinner.getEditor().setText("");
                }
            });
        } catch (IOException e) {
            ProgramException.showDefaultError();
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Context.setBillSettingsController(this);
        setBillTypeRadioButtonConfig();
        setCurrencyBoxConfig();
        clear();
        showBillSettings(Context.getBillSettings());
    }

    public void showBillSettings(BillSettings billSettings) {
        String billTypeId = billSettings.getType().name();
        Currency currency = billSettings.getCurrency();
        Date billDate = billSettings.getDate();
        LocalDate localBillDate = DateTool.convertDateToLocalDate(billDate);
        int billNumber = billSettings.getNumber();
        Tax billTax = billSettings.getTax();
        String conditions = billSettings.getConditions();

        double billTaxPercent = MathTool.getPercentFromCoefficient(billTax.getCoefficient());

        if (billTypeId.equals(budgetTypeId)) {
            budgetRadioBtn.setSelected(true);
            receiptRadioBtn.setSelected(false);
        } else {
            budgetRadioBtn.setSelected(false);
            receiptRadioBtn.setSelected(true);
        }

        this.billDate.setValue(localBillDate);
        currencyBox.setValue(currency);
        numberSpinner.getValueFactory().setValue(billNumber);
        taxSpinner.getValueFactory().setValue(billTaxPercent);
        conditionsArea.setText(conditions);
    }

    public BillSettings getBillSettings() {
        Bill.Type billType = getBillType();
        LocalDate localBillDate = this.billDate.getValue();
        Date billDate = DateTool.convertLocalDateToDate(localBillDate);

        int billNumber = numberSpinner.getValue();
        Currency currency = currencyBox.getValue();

        Tax tax = Tax.getDefaultTax();
        double taxPercent = taxSpinner.getValue();
        double taxCoefficient = MathTool.getCoefficientFromPercent(taxPercent);
        tax.setCoefficient(taxCoefficient);

        String conditions = conditionsArea.getText().trim();

        Company company = Context.getSettingsController().getCompanyController().getCompany();
        Customer customer = Context.getSettingsController().getCustomerController().getCustomer();

        BillSettings billSettings = new BillSettings();
        billSettings.setType(billType);
        billSettings.setDate(billDate);
        billSettings.setCurrency(currency);
        billSettings.setNumber(billNumber);
        billSettings.setTax(tax);
        billSettings.setConditions(conditions);
        billSettings.setCompany(company);
        billSettings.setCustomer(customer);

        return billSettings;
    }

    public void clear() {
        resetBillType();
        resetCurrencyBox();
        resetDate();
        resetBillNumber();
        resetTaxPercent();
        resetConditions();
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

}
