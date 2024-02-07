package com.system32.degradados;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private TextField textInput;
    private ColorPicker initialColorPicker;
    private ColorPicker finalColorPicker;
    private TextArea outputTextArea;
    private CheckBox checkboxAddAmpBeforeColor;
    private CheckBox checkboxAddAmpBeforeLetter;
    private CheckBox checkboxFormatLetters;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        textInput = new TextField();
        textInput.setPromptText("Text");

        initialColorPicker = new ColorPicker(Color.BLACK);
        initialColorPicker.setPromptText("Initial Color");

        finalColorPicker = new ColorPicker(Color.WHITE);
        finalColorPicker.setPromptText("Final Color");

        outputTextArea = new TextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setWrapText(true);
        outputTextArea.setMaxSize(600, 200);

        checkboxAddAmpBeforeColor = new CheckBox("Agrega '&' antes de cada color");
        checkboxAddAmpBeforeColor.setSelected(false);

        checkboxAddAmpBeforeLetter = new CheckBox("Agrega '&l' luego de cada color");
        checkboxAddAmpBeforeLetter.setSelected(false);

        checkboxFormatLetters = new CheckBox("Formatear Letras");
        checkboxFormatLetters.setSelected(false);
        checkboxFormatLetters.setOnAction(event -> {
            generateGradientAndShow();
        });

        HBox textInputBox = new HBox(new Label("Text:"), textInput);
        textInputBox.setAlignment(Pos.CENTER);

        HBox colorPickersBox = new HBox(initialColorPicker, finalColorPicker);
        colorPickersBox.setAlignment(Pos.CENTER);

        VBox centerBox = new VBox(10,
                textInputBox,
                colorPickersBox,
                checkboxAddAmpBeforeColor,
                checkboxAddAmpBeforeLetter,
                checkboxFormatLetters,
                outputTextArea
        );
        centerBox.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(centerBox);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 800, 400);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Message with Color Gradient");
        Image icon = new Image(getClass().getResourceAsStream("/icon.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.show();

        textInput.textProperty().addListener((observable, oldValue, newValue) -> generateGradientAndShow());
        initialColorPicker.setOnAction(event -> generateGradientAndShow());
        finalColorPicker.setOnAction(event -> generateGradientAndShow());
        checkboxAddAmpBeforeColor.setOnAction(event -> generateGradientAndShow());
        checkboxAddAmpBeforeLetter.setOnAction(event -> generateGradientAndShow());
        outputTextArea.setOnMouseClicked(event -> {
            copyOutputToClipboard();
            animateCopied();
        });
    }

    private void generateGradientAndShow() {
        String text = textInput.getText();
        String initialColor = initialColorPicker.getValue().toString();
        String finalColor = finalColorPicker.getValue().toString();
        boolean addAmpBeforeColor = checkboxAddAmpBeforeColor.isSelected();
        boolean addAmpBeforeLetter = checkboxAddAmpBeforeLetter.isSelected();
        boolean formatLetters = checkboxFormatLetters.isSelected();

        if (formatLetters) {
            text = formatLetters(text);
        }

        String gradient = generateGradient(initialColor, finalColor, text.length());
        String[] parts = parseGradient(gradient);
        String messageWithColor = buildMessageWithColor(text, parts, addAmpBeforeColor, addAmpBeforeLetter);

        outputTextArea.setText(messageWithColor);
        copyOutputToClipboard();
    }

    private String formatLetters(String text) {
        text = text.toLowerCase().replaceAll("a", "ᴀ")
                .replaceAll("b", "ʙ")
                .replaceAll("c", "ᴄ")
                .replaceAll("d", "ᴅ")
                .replaceAll("e", "ᴇ")
                .replaceAll("f", "ꜰ")
                .replaceAll("g", "ɢ")
                .replaceAll("h", "ʜ")
                .replaceAll("i", "ɪ")
                .replaceAll("j", "ᴊ")
                .replaceAll("k", "ᴋ")
                .replaceAll("l", "ʟ")
                .replaceAll("m", "ᴍ")
                .replaceAll("n", "ɴ")
                .replaceAll("o", "ᴏ")
                .replaceAll("p", "ᴘ")
                .replaceAll("q", "ǫ")
                .replaceAll("r", "ʀ")
                .replaceAll("s", "ѕ")
                .replaceAll("t", "ᴛ")
                .replaceAll("u", "ᴜ")
                .replaceAll("v", "ᴠ")
                .replaceAll("w", "ᴡ")
                .replaceAll("x", "x")
                .replaceAll("y", "ʏ")
                .replaceAll("z", "ᴢ");
        return text;
    }

    private void copyOutputToClipboard() {
        String outputText = outputTextArea.getText();
        if (!outputText.isEmpty()) {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(outputText);
            clipboard.setContent(content);
        }
    }

    private void animateCopied() {
        FadeTransition ft = new FadeTransition(Duration.millis(500), outputTextArea);
        ft.setFromValue(1.0);
        ft.setToValue(0.5);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);
        ft.play();
    }

    public static String generateGradient(String initialColor, String finalColor, int length) {
        javafx.scene.paint.Color cInitial = javafx.scene.paint.Color.valueOf(initialColor);
        javafx.scene.paint.Color cFinal = javafx.scene.paint.Color.valueOf(finalColor);

        double rInitial = cInitial.getRed();
        double gInitial = cInitial.getGreen();
        double bInitial = cInitial.getBlue();
        double rFinal = cFinal.getRed();
        double gFinal = cFinal.getGreen();
        double bFinal = cFinal.getBlue();

        StringBuilder gradient = new StringBuilder();

        for (int i = 0; i < length; i++) {
            double r = rInitial + (rFinal - rInitial) * i / (length - 1);
            double g = gInitial + (gFinal - gInitial) * i / (length - 1);
            double b = bInitial + (bFinal - bInitial) * i / (length - 1);

            int red = (int) (r * 255);
            int green = (int) (g * 255);
            int blue = (int) (b * 255);

            gradient.append(String.format("#%02x%02x%02x", red, green, blue));
        }

        return gradient.toString();
    }

    public static String[] parseGradient(String gradient) {
        String[] parts = new String[gradient.length() / 7];

        for (int i = 0; i < parts.length; i++) {
            parts[i] = gradient.substring(i * 7, (i + 1) * 7);
        }

        return parts;
    }

    public static String buildMessageWithColor(String text, String[] parts, boolean addAmpBeforeColor, boolean addAmpBeforeLetter) {
        StringBuilder message = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            if (addAmpBeforeColor) {
                message.append("&");
            }
            message.append(parts[i]);
            if (addAmpBeforeLetter) {
                message.append("&l");
            }
            message.append(text.charAt(i));
        }

        return message.toString();
    }
}
