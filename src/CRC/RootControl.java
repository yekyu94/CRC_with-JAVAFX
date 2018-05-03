package CRC;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class RootControl implements Initializable{
	@FXML private ComboBox<String> cal2;
	@FXML private TextArea area1, area2;
	@FXML private TextField cal1, data, rate;
	@FXML private ImageView pointer;
	
	char[] dataBox;
	int[] sarray = new int[1000];
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pointer.setImage(new Image(getClass().getResource("pointer.png").toString()));
		ObservableList<String> list = FXCollections.observableArrayList("Binary","HEX");
		cal2.setItems(list);
	}

	public void data(KeyEvent e) {
		dataBox = data.getText().toCharArray();
		
		String s = "";
		for(int i=0; i<dataBox.length; i++) {
			for (int j = 24; j < Integer.numberOfLeadingZeros((int)dataBox[i]); j++) {
				s += "0";
			}
			s += Integer.toBinaryString((int)dataBox[i]);
		}
		area1.setText(s);
		area2.setText(s);
	}
}
