package com.mycompany.atmmanagementsys;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.*;

public class MainAppSystemTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new MainApp().start(stage); 
    }

    @Test
    public void testRandomQuoteDisplayedOnUserPage() {
        //  valid user credentials from the database with userId 1 and password 1
        clickOn("#useridtf").write("1");
        clickOn("#passwordtf").write("1");
        clickOn("#userrb");
        clickOn("#loginb");
        
        TextArea quoteTextArea = lookup("#quotedis").query();
        assertNotNull(quoteTextArea.getText());
        assertFalse(quoteTextArea.getText().isBlank());
    }
    
}