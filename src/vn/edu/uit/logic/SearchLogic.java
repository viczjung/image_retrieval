package vn.edu.uit.logic;

import com.mathworks.toolbox.javabuilder.*;
import search.*;

public class SearchLogic {

	public static void search(String appDir) {
		System.out.println("START");
		Object[] result = null;
		SearchImage seachImage = null;
		try {
			seachImage = new SearchImage();
			result = seachImage.search(appDir);
			System.out.println(result[0]);
		} catch (Exception e) {
			System.out.println("Exception: " + e.toString());
		} finally {
			MWArray.disposeArray(result);
			MWArray.disposeArray(appDir);
			seachImage.dispose();
		}
		System.out.println("END");
	}
}
