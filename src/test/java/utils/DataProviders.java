package utils;

import java.io.IOException;
import org.testng.annotations.DataProvider;

public class DataProviders {
	
	@DataProvider(name="login")
	public String[][] LoginData() throws IOException {
		
	ExcelUtils xlutils = new ExcelUtils();
	xlutils.xlfile = System.getProperty("user.dir") + "//testData//OpenCart_LoginData.xlsx";
	int totalrows = xlutils.getRowCount("Sheet1");
	int totalcells = xlutils.getCellCount("Sheet1", 0);
	
	String login[][] = new String [totalrows][totalcells];
	for(int r=1; r<=totalrows; r++) {
		for(int c=0; c<=totalcells-1; c++) {
			login[r-1][c] = xlutils.getSpecificCellData("Sheet1", r, c);
		}
	}
	return login;
	}
}
