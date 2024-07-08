package MavenDemo.SDET2;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

public class checkLoginFunc {

	String filePath = "C:\\workspace\\SDET2\\Resource\\";
	String fileName = "TestCredentials.xlsx";
	String tabName = "TestData";
	
	String Url="https://www.demo.guru99.com/V4/";
	WebDriver driver;

	@Test(dataProvider = "supplyData")
	public void validateLoginFunc(String userID, String password) {
		
	}

	@BeforeMethod
	public void initTect() {
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver-win64\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(Url);
	}

	@AfterMethod
	public void clearTest() {
		driver.quit();
	}

	@DataProvider
	public String[][] supplyData() throws IOException {
		String[][] myTestData = null;

		File file = new File(filePath + fileName);
		FileInputStream fin = new FileInputStream(file);

		Workbook workbook = null;

		String fileExt = fileName.substring(fileName.indexOf("."));

		if (fileExt.equals(".xlsx")) {
			workbook = new XSSFWorkbook(fin);

		} else if (fileExt.equals(".xls")) {
			workbook = new HSSFWorkbook(fin);

		} else {
			System.out.println("Invalid File Type");
		}

		Sheet sheet = workbook.getSheet(tabName);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		Iterator rowIterator = sheet.rowIterator();
		Row headerRow = (Row) rowIterator.next();

		int columnCount = headerRow.getPhysicalNumberOfCells();

		myTestData = new String[rowCount][columnCount];
		
		for(int i=1; i<rowCount+1; i++) {
			Row row =sheet.getRow(i);
			
			for(int j=0; j<row.getLastCellNum(); j++) {
				myTestData[i-1][j]=row.getCell(j).getStringCellValue();
				
			}
		}

		return myTestData;

	}
}
