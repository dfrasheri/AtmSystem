package com.mycompany.atmmanagementsys;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AccountInfoController implements Initializable {
    
    String UserID;
    @FXML
    private TextField uid;
    @FXML
    private TextField uname;
    @FXML
    private TextArea uaddress;
    @FXML
    private TextField uemail;
    @FXML
    private TextField uphone;
    @FXML
    private TextField ubalance;
    @FXML
    private Label welcome;
    @FXML
    private PasswordField oldpass;
    @FXML
    private PasswordField newpass;
    @FXML
    private PasswordField passretype;
    @FXML
    private Label changepassconf;
    @FXML
    private TextArea quotediscp;
    @FXML
    private TextArea quotedisai;

    public void getUserID(String Id) {
        UserID = Id;
    }
 // Added these methods to to make testing in "ChangePasswordTest" easier
    public void setOldPass(String password) {
        this.getOldpass().setText(password);
    }

    public void setNewPass(String password) {
        this.getNewpass().setText(password);
    }

    public void setPassRetype(String password) {
        this.getPassretype().setText(password);
    }
    public void StqPasswordChangePage() {
        Quotes qt = new Quotes();
        String quote = qt.returnQuotes();
        quotediscp.setText(quote);
    }

    public void StqAccountInfo() {
        Quotes qt = new Quotes();
        String quote = qt.returnQuotes();
        quotedisai.setText(quote);
    }
    
    public void AccountInfo(ActionEvent event) throws SQLException{
        Connection con = DbConnection.Connection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ps = con.prepareStatement("SELECT * FROM users WHERE id = ?");
        ps.setString(1, UserID);
        rs = ps.executeQuery();
        while(rs.next()){
        getUname().setText(rs.getString("name"));
        getUaddress().setText(rs.getString("address"));
        getUemail().setText(rs.getString("email"));
        getUphone().setText(rs.getString("phone"));
        getUbalance().setText(rs.getString("balance"));
        }
        ps.close();
        rs.close();
        con.close();
    }

   public void ChangePassword(ActionEvent event) throws SQLException{
       if(!getNewpass().getText().equals(getPassretype().getText())){
       getChangepassconf().setText("Password Confirmation Failed");
       getPassretype().setText("");
       getPassretype().setStyle("-fx-border-color:red;-fx-border-width:2;-fx-border-radius:20;-fx-background-radius:22;");
       }
       else if(getOldpass().getText().isEmpty()||getNewpass().getText().isEmpty()||getPassretype().getText().isEmpty()){
       getChangepassconf().setText("Please Fill Up Everything Correctly.");
       }
       else{
       Connection con = DbConnection.Connection();
       PreparedStatement ps = con.prepareStatement("UPDATE users SET password = ? WHERE id = ? AND password = ?");
       ps.setString(1, getNewpass().getText());
       ps.setString(2, UserID);
       ps.setString(3, getOldpass().getText());
       int i = ps.executeUpdate();
       if(i>0){
       getChangepassconf().setText("Password Changed.");
       }
       else{
       getChangepassconf().setText("Wrong Password.");
       }
       getOldpass().setText("");
       getNewpass().setText("");
       getPassretype().setText("");
       getPassretype().setStyle("-fx-border-color: #3b5998;-fx-border-width:2;-fx-border-radius:20;-fx-background-radius:22;");
       ps.close();
       con.close();
       }
   }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
	public PasswordField getOldpass() {
		return oldpass;
	}
	public void setOldpass(PasswordField oldpass) {
		this.oldpass = oldpass;
	}
	public PasswordField getNewpass() {
		return newpass;
	}
	public void setNewpass(PasswordField newpass) {
		this.newpass = newpass;
	}
	public PasswordField getPassretype() {
		return passretype;
	}
	public void setPassretype(PasswordField passretype) {
		this.passretype = passretype;
	}
	public Label getChangepassconf() {
		return changepassconf;
	}
	public void setChangepassconf(Label changepassconf) {
		this.changepassconf = changepassconf;
	}
	public TextField getUname() {
		return uname;
	}
	public void setUname(TextField uname) {
		this.uname = uname;
	}
	public TextArea getUaddress() {
		return uaddress;
	}
	public void setUaddress(TextArea uaddress) {
		this.uaddress = uaddress;
	}
	public TextField getUemail() {
		return uemail;
	}
	public void setUemail(TextField uemail) {
		this.uemail = uemail;
	}
	public TextField getUphone() {
		return uphone;
	}
	public void setUphone(TextField uphone) {
		this.uphone = uphone;
	}
	public TextField getUbalance() {
		return ubalance;
	}
	public void setUbalance(TextField ubalance) {
		this.ubalance = ubalance;
	}
}
