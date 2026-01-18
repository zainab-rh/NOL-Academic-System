package nol.Servlets;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import nol.HTTPRequests.Requests;
import nol.Models.Asignatura;
import nol.Models.Alumno;

public class PDFGen extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Class Alumno = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/pdf");
		resp.setHeader("Content-Disposition", "inline; filename=notas.pdf");
	
		
		HttpSession session = req.getSession();
		String key = (String) session.getAttribute("key");
        String dniAlu = req.getRemoteUser();
        
        Requests req2 = new Requests();
		List<Asignatura> asignaturas = (List<Asignatura>) session.getAttribute("asignaturas");
		
		Gson gson = new Gson();
		String json = req2.getAlumnoPorDNIJSON(dniAlu, key);
		Alumno alumno = gson.fromJson(json, Alumno.class);
		String nombreAlu = alumno.getNombre() + " " + alumno.getApellidos();
		
		System.out.println(nombreAlu);
        
		try (PDDocument document = new PDDocument()){
			
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			
			PDRectangle mediaBox = page.getMediaBox();
			float pageWidth = mediaBox.getWidth();
			float startY = mediaBox.getHeight() - 100;
					
			PDType1Font helvetica = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
			PDType1Font helveticaBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
			
			PDPageContentStream contentStream = new PDPageContentStream(document, page);
			contentStream.beginText();
			
			
			
			
	        
	        
	        //Titulo documento
	        String title = "Certificado académico";
	        float titleWidth = helveticaBold.getStringWidth(title) / 1000 * 20;
	        
	        contentStream.setFont(helveticaBold, 20);
	        contentStream.newLineAtOffset((pageWidth - titleWidth) / 2, startY);
	        contentStream.showText(title);
			contentStream.endText();
			
			//Subtitle
			contentStream.beginText();
            contentStream.setFont(helvetica, 16);
            String subtitle = "Alumno: " + nombreAlu + " (" + dniAlu + ")";
            float subtitleWidth = helvetica.getStringWidth(subtitle) / 1000 * 16;
            contentStream.newLineAtOffset((pageWidth - subtitleWidth) / 2, startY - 30);
            contentStream.showText(subtitle);
            contentStream.endText();
            
            //Avatar
            String imagePath = getServletContext().getRealPath("/assets/images_alumnos/" + dniAlu + ".jpg");
            PDImageXObject image = PDImageXObject.createFromFile(imagePath, document);
            float imageWidth = 150;
            float imageScale = imageWidth / image.getWidth();
            float imageHeight = image.getHeight() * imageScale;
            float imageX = (pageWidth - imageWidth) / 2; // center it
            float imageY = startY - 50 - imageHeight;
            contentStream.drawImage(image, imageX, imageY, imageWidth, imageHeight);
            
            // Asignaturas con sus atributos 
            float leading = 1.5f * 10;      
            float yPosition = imageY - 40;  
            float xPosition = 80;

            for (Asignatura a : asignaturas) {
                String nombre = a.getNombre() + " (" + a.getAcronimo() + ")";
                String descripcion = "Descripción: " + a.getDescripcion();
                String cursoInfo = "Curso: " + a.getCurso() +
                                   " - Cuatrimestre: " + a.getCuatrimestre() +
                                   " - Créditos: " + (""+a.getCreditos()).replace(".", ",");
                String notaTexto = "Nota: " +
                    ((a.getNota() == null || a.getNota().isBlank()) ? "Sin calificación" : a.getNota().replace(".", ","));

                // --- DIBUJAR TÍTULO EN BOLD 14 pt ---
                contentStream.beginText();
                contentStream.setFont(helveticaBold, 12);
                contentStream.newLineAtOffset(xPosition, yPosition);
                contentStream.showText(nombre);
                contentStream.endText();

                // Bajamos el cursor para la siguiente línea
                yPosition -= (1.5f * 12);

                // --- DESCRIPCIÓN EN NORMAL 10 pt ---
                contentStream.beginText();
                contentStream.setFont(helvetica, 10);
                contentStream.newLineAtOffset(xPosition, yPosition);
                contentStream.showText(descripcion);
                contentStream.endText();

                yPosition -= leading;

                // --- CURSO / CUATRIMESTRE / CRÉDITOS EN NORMAL 10 pt ---
                contentStream.beginText();
                contentStream.setFont(helvetica, 10);
                contentStream.newLineAtOffset(xPosition, yPosition);
                contentStream.showText(cursoInfo);
                contentStream.endText();

                yPosition -= leading;

                // --- NOTA EN NORMAL 10 pt ---
                contentStream.beginText();
                contentStream.setFont(helvetica, 10);
                contentStream.newLineAtOffset(xPosition, yPosition);
                contentStream.showText(notaTexto);
                contentStream.endText();

                // Espacio extra antes de la siguiente asignatura
                yPosition -= (leading * 1.5f);
            }
			
			contentStream.close();
			
			document.save(resp.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace(); // Print any errors for debugging
		}
	}
}