package CRC;

import java.util.Arrays;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
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
	@FXML private CheckBox check1;
	
	char[] dataBox = new char[0];
	int[] sarray = new int[1000];
	int flag = 0;  // 0 : binary, 1 : HEX
	char[] divisor = new char[0];
	
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
	
	public void divi(KeyEvent e) {    //divisor 입력시 실행되는 부
		dataControl();
	}
	
	public void dataControl() {
		if(flag == 1) hex_to_bin();
		
		// box 1 : data 를 2진수로 변환하여 s라는 값에 저장시 -----
		String bin_data = "";
		for(int i=0; i<dataBox.length; i++) {
			for (int j = 24; j < Integer.numberOfLeadingZeros((int)dataBox[i]); j++) {
				bin_data += "0";
			}
			bin_data += Integer.toBinaryString((int)dataBox[i]);
		}
		// box 1 Fin. -----
		
		if(check1.isSelected()) {
			String prt_data ="";
			for(int i=0; i<bin_data.length()/8; i++) {
				prt_data += bin_data.substring(8*i, 8*(1+i)) + " / ";
			}
			area1.setText(prt_data);
		}
		else area1.setText(bin_data);
		
		try {
			if(flag == 0) divisor = cal1.getText().toCharArray();
			char[] tmp_data = bin_data.toCharArray();
			int leng_D = divisor.length; //Divisor의 길이
			if(leng_D == 0) leng_D = 1;
			
			// box 2 : result라는 Int형 배열에 입력한 데이터의 2진수 값과 Divisor 길이 보다 1만큼 작은 숫자의 0을 채워줌 ====
			int[] result = new int[bin_data.length()+leng_D-1];
			for(int i=0; i<tmp_data.length; i++) result[i] = (int)tmp_data[i] - 48;
			// box 2 Fin. ====
			
			
			// box 3 : XOR계산이 가능한 경우 XOR한 후 나머지 값을 result의 해당 위치에 저장 결과적으로 앞은 모두 0, CRC부분만 값을 갖음.
			for(int i=0; i<tmp_data.length; i++) {
				if(result[i] == ((int)divisor[0]-48)) {
					for(int j=0; j<leng_D; j++) {
						result[i+j] = result[i+j]^((int)divisor[j]-48);
					}
				}
			}
			// box 3 Fin.
			
			System.out.println(Arrays.toString(result));
			
		}
		catch(Exception error) {
			area1.setText("올바른 Data와 Divisor가 입력되었는지 확인하세요.");
		}
		
		
	}
	
	public void hex_to_bin() {
		String tmp = "";
		divisor = cal1.getText().toCharArray();
		String[] hex =
            { "0000", "0001", "0010", "0011", "0100",
              "0101", "0110", "0111", "1000", "1001", "1010",
              "1011", "1100", "1101", "1110", "1111" };
		for(int i=0; i<divisor.length; i++) {
			switch(divisor[i]) {
			case '0': tmp += hex[0]; break;
			case '1': tmp += hex[1]; break;
			case '2': tmp += hex[2]; break;
			case '3': tmp += hex[3]; break;
			case '4': tmp += hex[4]; break;
			case '5': tmp += hex[5]; break;
			case '6': tmp += hex[6]; break;
			case '7': tmp += hex[7]; break;
			case '8': tmp += hex[8]; break;
			case '9': tmp += hex[9]; break;
			case 'a': tmp += hex[10]; break;
			case 'b': tmp += hex[11]; break;
			case 'c': tmp += hex[12]; break;
			case 'd': tmp += hex[13]; break;
			case 'e': tmp += hex[14]; break;
			case 'f': tmp += hex[15]; break;
			}
		}
		divisor = tmp.toCharArray();
		System.out.print("--->");
		System.out.println(Arrays.toString(divisor));
		
	}
	
}
