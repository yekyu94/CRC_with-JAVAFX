package CRC;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
	
	char[] dataBox = new char[0];
	int[] sarray = new int[1000];
	int flag = 0;  // 0 : binary, 1 : HEX
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pointer.setImage(new Image(getClass().getResource("pointer.png").toString()));
		ObservableList<String> list = FXCollections.observableArrayList("Binary","HEX");
		cal2.setItems(list);
	}

	public void btn01(ActionEvent e) {   //flag 값 변경에 대한 ActionEvent
		String tmp = cal2.getValue().toString();
		if(tmp.equals("Binary")) flag = 0;
		else if(tmp.equals("HEX")) flag = 1;
		dataControl();
	}
	
	public void data(KeyEvent e) {    //data 입력시 처리하는 KeyEvent
		dataBox = data.getText().toCharArray();
		dataControl();
	}
	
	public void dataControl() {
		// box 1 : data 를 2진수로 변환하여 s라는 값에 저장시 -----
		String bin_data = "";
		for(int i=0; i<dataBox.length; i++) {
			for (int j = 24; j < Integer.numberOfLeadingZeros((int)dataBox[i]); j++) {
				bin_data += "0";
			}
			bin_data += Integer.toBinaryString((int)dataBox[i]);
		}
		// box 1 Fin. -----
		
		area1.setText(bin_data);
		try {
			char[] divisor = cal1.getText().toCharArray();
			char[] tmp_data = bin_data.toCharArray();
			int leng_D = cal1.getText().length(); //Divisor의 길이
			System.out.println(cal1.getText());
			if(leng_D == 0) leng_D = 1;
			System.out.println(tmp_data[0]);
			
			int[] result = new int[bin_data.length()+leng_D];
			int[] tmp = new int[leng_D];
			for(int i=0; i<bin_data.length(); i++) {
				for(int j=0; j<leng_D; j++) {
					tmp[j] = (int)tmp_data[i+j]^(int)divisor[j];
				}
				
			}
			
		}
		catch(Exception error) {
			area1.setText("Data를 입력해주세요.");
		}
		
		
	}
	
}
