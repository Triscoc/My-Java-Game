package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteSheet {
    private BufferedImage spriteSheet;
    private int frameWidth;
    private int frameHeight;


    public SpriteSheet(String path, int frameWidth, int frameHeight) {
        try {
            spriteSheet = ImageIO.read(getClass().getResourceAsStream(path));
            this.frameWidth = frameWidth;
            this.frameHeight = frameHeight;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public BufferedImage getFrame(int column, int row) {
        return spriteSheet.getSubimage(
            column * frameWidth,
            row * frameHeight,
            frameWidth,
            frameHeight
        );
    }

    // Optional: Get all frames in a single row
    public BufferedImage[] getAllFramesInRow(int row, int numFrames) {
        BufferedImage[] frames = new BufferedImage[numFrames];
        for (int i = 0; i < numFrames; i++) {
            frames[i] = getFrame(i, row);
        }
        return frames;
    }
    
    public BufferedImage flipImageHorizontal(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage flippedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2 = flippedImage.createGraphics();

        // Flip horizontally
        g2.scale(-1, 1);
        g2.translate(-width, 0);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();

        return flippedImage;
    }
    
    public BufferedImage rotateImage(BufferedImage image, double angle) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage rotatedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2 = rotatedImage.createGraphics();
        g2.rotate(angle, width / 2.0, height / 2.0); 
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return rotatedImage;
    }
    
    public BufferedImage turnImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        
        // Create a new image with swapped dimensions
        BufferedImage rotatedImage = new BufferedImage(height, width, image.getType());
        Graphics2D g2 = rotatedImage.createGraphics();
        
        // Rotate 90 degrees clockwise around the new image center
        g2.translate(height / 2.0, width / 2.0);
        g2.rotate(Math.toRadians(90));
        g2.translate(-width / 2.0, -height / 2.0);
        
        // Draw the original image
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        
        return rotatedImage;
    }
}
