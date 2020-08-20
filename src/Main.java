import java.awt.Color;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedImage image = ImageIO.read(new File("src/postal.png"));
	    // parameters for correction
	    double paramA = -0.007715; // n'affecte que les pixels le plus à l'exterieur de l'image
	    double paramB = 0.00000002; // la plus part de ces cas ne nécessitent qu'une optimisation b
	    double paramC = 0.0; // most uniform correction
	    double paramD = 1.0 - paramA - paramB - paramC; // décrit la mise à l'échelle linéaire de l'image
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
	    int[] pixelsCopy = pixels.clone();
		try {
			System.out.println("debut de la boucle for");
			for(int i =0; i< width ; i++) {
				for(int j =0; j< height ; j++) {
		            int d = Math.min(width, height) / 2;    //calcul du rayon du cercle 
		            System.out.println("rayon du cercle : "+d+"px");
		            // centre de l'image
		            double centerX = (width - 1) / 2.0;
		            double centerY = (height - 1) / 2.0;
		            System.out.println("centre ["+centerX+"px,"+centerY+"px]");
		            // cordonnées cartesiennes du vecteur par rapport au centre de l'image
		            double deltaX = (i - centerX) / d;
		            double deltaY = (j - centerY) / d;
		            System.out.println("delta ["+deltaX+"px,"+deltaY+"px]");
		            // distance ou rayon de l'image
		            double dstR = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		            System.out.println("distance"+dstR);
		            // distance ou rayon de l'image avec les parametres correctionels
		            double srcR = (paramA * dstR * dstR * dstR + paramB * dstR * dstR + paramC * dstR + paramD) * dstR;

		            // factor = ancienne distance sur nouvelle distance
		            double factor = Math.abs(dstR / srcR);

		            // cordonnées dans l'image source
		            double srcXd = centerX + (deltaX * factor * d);
		            double srcYd = centerY + (deltaY * factor * d);

		            // pas encore d'interpolation juste les points les plus proches
		            int srcX = (int) srcXd;
		            int srcY = (int) srcYd;

		            if (srcX >= 0 && srcY >= 0 && srcX < d && srcY < d) {
		                int dstPos = j * width + i;
		                System.out.println("position "+ dstPos);
		                pixels[dstPos] = pixelsCopy[srcY * width + srcX];
		            }
				}
			}
			System.out.println("on sort de la boucle for");
	        MemoryImageSource mis = new MemoryImageSource(width, height, pixels, 0, width);
	        java.awt.Image im = Toolkit.getDefaultToolkit().createImage(mis);
			BufferedImage newImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			newImage.getGraphics().drawImage(im, 0, 0, null);
			ImageIO.write(newImage, "png", new File("src/output.png") );
			textToImage("src/output.jpeg", width, height, pixels);
			System.out.println("c'est finni");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
    private static void textToImage(String path, int width, int height, int[] data) throws IOException {
        MemoryImageSource mis = new MemoryImageSource(width, height, data, 0, width);
        java.awt.Image im = Toolkit.getDefaultToolkit().createImage(mis);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(im, 0, 0, null);
        ImageIO.write(bufferedImage, "jpg", new File(path));
        System.out.println("Done! Check the result");
    }

}
