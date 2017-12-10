package vn.edu.uit.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import vn.edu.uit.logic.SearchLogic;

@WebServlet("/SearchServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                 maxFileSize=1024*1024*10,      // 10MB
                 maxRequestSize=1024*1024*50)   // 50MB
public class SearchServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Name of the directory where uploaded files will be saved, relative to
     * the web application directory.
     */
    private static final String SAVE_DIR = "test";
    private static final String FILE_NAME_ORIGIN = "orginal.jpg";
    private static final String FILE_NAME_TEST = "test.jpg";
     
    /**
     * handles file upload
     */
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
        // gets absolute path of the web application
        String appPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String savePath = appPath + File.separator + SAVE_DIR;
         
        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        
        int x = Integer.parseInt(request.getParameter("x_pos"));
        int y = Integer.parseInt(request.getParameter("y_pos"));
        int width = Integer.parseInt(request.getParameter("width"));
        int height = Integer.parseInt(request.getParameter("height"));
        
        // check if croped
        if (width > 0 && height > 0) {
        	// save original file 
	        for (Part part : request.getParts()) {
	            part.write(savePath + File.separator + FILE_NAME_ORIGIN);
	            break;
	        }
	        // save crop file
	        BufferedImage originalImgage = ImageIO.read(new File(savePath + File.separator + FILE_NAME_ORIGIN));
	        BufferedImage cropImage = originalImgage.getSubimage(x, y, width, height);
	        File outputfile = new File(savePath + File.separator + FILE_NAME_TEST);
	        ImageIO.write(cropImage, "jpg", outputfile);
        } else {
        	 for (Part part : request.getParts()) {
 	            part.write(savePath + File.separator + FILE_NAME_TEST);
 	            break;
 	        }
        }
        // Search
		SearchLogic.search(appPath);
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }
}
