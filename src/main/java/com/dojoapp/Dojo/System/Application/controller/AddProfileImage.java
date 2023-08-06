package com.dojoapp.Dojo.System.Application.controller;

import com.dojoapp.Dojo.System.Application.CurrentStudent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class AddProfileImage implements Initializable {

    @Value("/profileimages/defaultprofilepic.jpg")
    private Resource defaultImageResource;

    @FXML
    private ImageView imageView;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button uploadImageBtn;

    @FXML
    private Button saveBtn;

    @FXML
    void cancelAction(ActionEvent event) {

    }

    @FXML
    void saveAction(ActionEvent event) {

    }

    @FXML
    void uploadImageAction() {
        var fileChooser = new FileChooser();
        var extensionFilter = new FileChooser.ExtensionFilter("Image Files",
                "*.jpeg", "*.png", "*.bmp", "*.jpg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Profile Image");
        var file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                var idk = copyAndSaveImage(file);
                var bufferedInputStream = new BufferedInputStream(new FileInputStream(idk));
                imageView.setImage(new Image(bufferedInputStream));
                bufferedInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String defaultImagePath = null;
        try {
            defaultImagePath = Objects.requireNonNull(defaultImageResource.getURL()).toExternalForm();
            imageView.setImage(new Image(defaultImagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File copyAndSaveImage(File imageFile) throws IOException {
        var bufferedImage = ImageIO.read(imageFile);
        var student = CurrentStudent.getStudent();
        var destPath = System.getProperty("user.home") + File.separator + "Desktop" +
                File.separator + "DojoApp" + File.separator + "students" +
                File.separator + student.getId() + File.separator;
        //TODO could probably store directory paths in application.properties
        var dest = new File(destPath + "profile-image.png");
        dest.createNewFile();
        ImageIO.write(bufferedImage, "png", dest);
        return dest;
    }
}
