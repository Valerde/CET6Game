package ykn.sovava.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ykn.sovava.Director;
import java.io.IOException;

/**
 * description: 游戏结束controller
 * @className: OverController
 * @author: ykn
 * @date: 2022/5/18
 **/
public class OverController {

    @FXML
    private ImageView overImage;

    @FXML
    private Button returnBack;

    @FXML
private Button lookWrongWord;

public void flagSuccess() {
    overImage.setImage(new Image("image/win.jpg"));
}


@FXML
void clickToBack(ActionEvent event) {
    Director.getInstance().gameStart();
}

@FXML
void clickToLookWA(ActionEvent event) throws IOException {
    Director.getInstance().lookWA();
}
}
