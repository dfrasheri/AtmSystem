package com.mycompany.atmmanagementsys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AddCustomerController implements Initializable {
    @FXML
    private TextField cusid;
    @FXML
    private TextField cusname;
    @FXML
    private TextArea cusaddress;
    @FXML
    private TextField cusphone;
    @FXML
    private Button add;
    @FXML
    private Label addcusconfirm;
    @FXML
    private TextField cusemail;
    @FXML
    private TextField balance;
    @FXML
    private Button browse;
    private File file;
    @FXML
    private ImageView userimage;
    @FXML
    private TextField cuspass;
    private FileInputStream fis;
    int cf;
    @FXML
    public void ChooseFile(ActionEvent event) {
        FileChooser fc = new FileChooser();
        file = fc.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            userimage.setImage(image);
            cf = 1;
        } else {
            cf = 0;
        }
    }
    @FXML
    public void AddCustomer(ActionEvent event) throws SQLException, FileNotFoundException {
        try {
            if (getCusid().getText().isEmpty() || getCuspass().getText().isEmpty() || getCusname().getText().isEmpty() || getCusaddress().getText().isEmpty() || getCusemail().getText().isEmpty() || getCusphone().getText().isEmpty() || getBalance().getText().isEmpty()) {
                getAddcusconfirm().setText("Please Fill Up Everything");
            } else if (Integer.parseInt(getBalance().getText()) < 500) {
                getAddcusconfirm().setText("Minimum Balance Requirement Is 500 Tk.");
            } else {
                if (cf != 1) {
                    getAddcusconfirm().setText("Please Upload An Image To Add New Customer");
                } else if (cf == 1) {
                    try {
                        Connection con = DbConnection.Connection();
                        PreparedStatement ps = con.prepareStatement("INSERT INTO users VALUES (?,?,?,?,?,?,?,?)");
                        ps.setInt(1, Integer.parseInt(getCusid().getText()));
                        ps.setString(2, getCuspass().getText());
                        ps.setString(3, getCusname().getText());
                        ps.setString(4, getCusaddress().getText());
                        ps.setString(5, getCusemail().getText());
                        ps.setString(6, getCusphone().getText());
                        ps.setString(7, getBalance().getText());
                        fis = new FileInputStream(file);
                        ps.setBinaryStream(8, (InputStream) fis, (int) file.length());
                        int i = ps.executeUpdate();
                        if (i > 0) {
                            getCusid().setText("");
                            getCuspass().setText("");
                            getCusname().setText("");
                            getCusaddress().setText("");
                            getCusemail().setText("");
                            getCusphone().setText("");
                            getBalance().setText("");
                            getAddcusconfirm().setText("New Customer Added Successfully");
                        } else {
                            getAddcusconfirm().setText("Failed To Add New Customer");
                        }
                        ps.close();
                        con.close();
                    } catch (FileNotFoundException | NumberFormatException | SQLException e) {
                        getAddcusconfirm().setText("Invalid Customer ID or ID Is Not Available");
                    }
                }
            }
        } catch (NumberFormatException e) {
            getAddcusconfirm().setText("Please Enter Everything Correctly");
        }
        cf = 0;
        Image image = new Image("/icons/usericon1.png");
        userimage.setImage(image);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
	public TextField getCusid() {
		return cusid;
	}
	public void setCusid(TextField cusid) {
		this.cusid = cusid;
	}
	public TextField getCusname() {
		return cusname;
	}
	public void setCusname(TextField cusname) {
		this.cusname = cusname;
	}
	public TextArea getCusaddress() {
		return cusaddress;
	}
	public void setCusaddress(TextArea cusaddress) {
		this.cusaddress = cusaddress;
	}
	public TextField getCuspass() {
		return cuspass;
	}
	public void setCuspass(TextField cuspass) {
		this.cuspass = cuspass;
	}
	public TextField getCusemail() {
		return cusemail;
	}
	public void setCusemail(TextField cusemail) {
		this.cusemail = cusemail;
	}
	public TextField getCusphone() {
		return cusphone;
	}
	public void setCusphone(TextField cusphone) {
		this.cusphone = cusphone;
	}
	public TextField getBalance() {
		return balance;
	}
	public void setBalance(TextField balance) {
		this.balance = balance;
	}
	public Label getAddcusconfirm() {
		return addcusconfirm;
	}
	public void setAddcusconfirm(Label addcusconfirm) {
		this.addcusconfirm = addcusconfirm;
	}
}
