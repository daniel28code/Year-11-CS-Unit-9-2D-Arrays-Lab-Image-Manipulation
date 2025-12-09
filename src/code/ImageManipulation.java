package code;

import image.APImage;
import image.Pixel;

public class ImageManipulation {

    /** CHALLENGE 0: Display Image
     *  Write a statement that will display the image in a window
     */
    public static void main(String[] args) {
        String path = "StageSpotlight_1.1.1.png";
        APImage image = new APImage(path);
        image.draw();
        // grayScale(path);
        // blackAndWhite(path);
        // edgeDetection(path, 20);
        // reflectImage(path);
        // rotateImage(path);
        addHalation(path, 248, 8);
    }

    /** CHALLENGE ONE: Grayscale
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a grayscale copy of the image
     *
     * To convert a colour image to grayscale, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * Set the red, green, and blue components to this average value. */
    public static void grayScale(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        for (int height = 0; height < image.getHeight(); height++){
            for (int width = 0; width < image.getWidth(); width++){
                Pixel pixel = image.getPixel(width, height);
                int average = getAverageColour(pixel);
                pixel.setRed(average);
                pixel.setGreen(average);
                pixel.setBlue(average);
            }
        }
        image.draw(); 
    }
    /** A helper method that can be used to assist you in each challenge.
     * This method simply calculates the average of the RGB values of a single pixel.
     * @param pixel
     * @return the average RGB value
     */
    private static int getAverageColour(Pixel pixel) {
        return (pixel.getRed() + pixel.getBlue() + pixel.getGreen()) / 3;
    }

    /** CHALLENGE TWO: Black and White
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a black and white copy of the image
     *
     * To convert a colour image to black and white, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * If the average is less than 128, set the pixel to black
     * If the average is equal to or greater than 128, set the pixel to white */
    public static void blackAndWhite(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        for (int height = 0; height < image.getHeight(); height++){
            for (int width = 0; width < image.getWidth(); width++){
                Pixel pixel = image.getPixel(width, height);
                //int average = getAverageColour(pixel);
                pixel.setRed(blackOrWhite(pixel));
                pixel.setGreen(blackOrWhite(pixel));
                pixel.setBlue(blackOrWhite(pixel));
            }
        }
        image.draw(); 
    }
    private static int blackOrWhite(Pixel pixel){
        if (getAverageColour(pixel) < 128){
            return 0;
        }
        else{
            return 255;
        }
    }

    /** CHALLENGE Three: Edge Detection
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: an outline of the image. The amount of information will correspond to the threshold.
     *
     * Edge detection is an image processing technique for finding the boundaries of objects within images.
     * It works by detecting discontinuities in brightness. Edge detection is used for image segmentation
     * and data extraction in areas such as image processing, computer vision, and machine vision.
     *
     * There are many different edge detection algorithms. We will use a basic edge detection technique
     * For each pixel, we will calculate ...
     * 1. The average colour value of the current pixel
     * 2. The average colour value of the pixel to the left of the current pixel
     * 3. The average colour value of the pixel below the current pixel
     * If the difference between 1. and 2. OR if the difference between 1. and 3. is greater than some threshold value,
     * we will set the current pixel to black. This is because an absolute difference that is greater than our threshold
     * value should indicate an edge and thus, we colour the pixel black.
     * Otherwise, we will set the current pixel to white
     * NOTE: We want to be able to apply edge detection using various thresholds
     * For example, we could apply edge detection to an image using a threshold of 20 OR we could apply
     * edge detection to an image using a threshold of 35
     *  */
    public static void edgeDetection(String pathToFile, int threshold) {
        APImage image = new APImage(pathToFile);
        APImage outputImage = new APImage(image.getWidth(), image.getHeight());
        for (int height = 1; height < image.getHeight(); height++){
            for (int width = 1; width < image.getWidth(); width++){
                Pixel currentPixel = image.getPixel(width, height);
                Pixel leftPixel = image.getPixel(width-1, height);
                Pixel downPixel = image.getPixel(width, height-1);
                int currentAvg = getAverageColour(currentPixel);
                int leftAvg = getAverageColour(leftPixel);
                int downAvg = getAverageColour(downPixel);
                if (Math.abs(currentAvg - leftAvg) > threshold || Math.abs(currentAvg-downAvg) > threshold){
                    outputImage.getPixel(width, height).setRed(0);
                    outputImage.getPixel(width, height).setBlue(0);
                    outputImage.getPixel(width, height).setGreen(0);
                }
                else{
                    outputImage.getPixel(width, height).setRed(255);
                    outputImage.getPixel(width, height).setBlue(255);
                    outputImage.getPixel(width, height).setGreen(255);
                }
            }
        }
        outputImage.draw();
    }

    /** CHALLENGE Four: Reflect Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image reflected about the y-axis
     *
     */
    public static void reflectImage(String pathToFile) {
        APImage image = new APImage(pathToFile);
        APImage outputImage = new APImage(image.getWidth(), image.getHeight());
        for (int height = 0; height < image.getHeight(); height++){
            for (int width = 0; width < image.getWidth(); width++){
                Pixel pixel = image.getPixel(width, height);
                outputImage.getPixel(image.getWidth() - 1 - width, height).setRed(pixel.getRed());
                outputImage.getPixel(image.getWidth() - 1 -width, height).setGreen(pixel.getGreen());
                outputImage.getPixel(image.getWidth() - 1 -width, height).setBlue(pixel.getBlue());
            }
        }
        outputImage.draw();
    }

    /** CHALLENGE Five: Rotate Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image rotated 90 degrees CLOCKWISE
     *
     *  */
    public static void rotateImage(String pathToFile) {
        APImage image = new APImage(pathToFile);
        APImage outputImage = new APImage(image.getHeight(), image.getWidth());
        for (int height = 0; height < image.getHeight(); height++){
            for (int width = 0; width < image.getWidth(); width++){
                Pixel ogPixel = image.getPixel(width, height);
                outputImage.getPixel(image.getHeight() - 1 - height, image.getWidth() - 1 - width).setRed(ogPixel.getRed());
                outputImage.getPixel(image.getHeight() - 1 - height, image.getWidth() - 1 - width).setGreen(ogPixel.getGreen());
                outputImage.getPixel(image.getHeight() - 1 - height, image.getWidth() - 1 - width).setBlue(ogPixel.getBlue());
            }
        }
        outputImage.draw();
    }

    // adding halation
    public static void addHalation(String pathToFile, int threshold, int strength){
        APImage original = new APImage(pathToFile);
        APImage modified = new APImage(pathToFile);

        double radius = Math.max(1, strength);
        //double center = 0.35;

        for (int height = 0; height < original.getHeight(); height++){
            for (int width = 0; width < original.getWidth(); width++){
                Pixel current = original.getPixel(width, height);
                int brightness = (current.getRed() + current.getGreen() + current.getBlue()) / 3;
                
                if (brightness >= threshold){
                    for (int y = - strength; y <= strength; y++){
                        for (int x = - strength; x <= strength; x++){
                            if (height + y < 0 || height + y >= original.getHeight() || width + x < 0 || width + x >= original.getWidth()) continue;
                            double distanceSq = x * x + y * y;
                            if (distanceSq < (radius * radius)){
                                double distance = Math.sqrt(distanceSq);
                                double distanceFactor = distance / radius;
                                double falloffWeight = Math.pow(distanceFactor, 1.5) * Math.exp(-3.0 * distanceSq /(radius * radius));
                                int boostR = (int) Math.round(brightness * 0.18 * falloffWeight);
                                int boostG = (int) Math.round(brightness * 0.08 * falloffWeight);
                                int boostB = (int) Math.round(brightness * 0.04 * falloffWeight);

                                Pixel combined = modified.getPixel(width + x, height + y);
                                combined.setRed(Math.min(255, combined.getRed() + boostR));
                                combined.setGreen(Math.min(255, combined.getGreen() + boostG));
                                combined.setBlue(Math.min(255, combined.getBlue() + boostB));
                                
                            }
                        }
                    }
                }
            }
        }
        modified.draw();
    }
}
