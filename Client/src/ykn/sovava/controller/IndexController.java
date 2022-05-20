package ykn.sovava.controller;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ykn.sovava.Director;

/**
 * @className: IndexController
 * @description:
 * @author: ykn
 * @date: 2022/5/19
 **/
public class IndexController {

    @FXML
    private Label label;

    @FXML
    private TextField accountLogin;

    @FXML
    private Button loginButton;

    @FXML
    void onClickStartGame(ActionEvent event) {
        Director.getInstance().gameStart();
    }
}
