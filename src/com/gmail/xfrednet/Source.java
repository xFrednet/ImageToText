package com.gmail.xfrednet;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Source {

	public static int[] imagePixel;
	public static int imageWidth, imageHeight;
	
	public static void main(String[] args) {
		Source s = new Source();
		if (args.length == 0) {
			System.out.println("Please enter the image name as a argument");
			return;
		}
		imagePixel = s.loadImage(args[0]);
		
		String[] output = s.getOutput();
		
		String path = s.getSavePath(args[0]);
		s.saveString(path, output);
	}
	
	private String[] getOutput() {
		String[] output = new String[imageHeight];
		int outputWidth = imageWidth;
		int outputHeight = imageHeight;
		for (int h = 0; h < outputHeight; h++) {
			output[h] = "";
			for (int w = 1; w < outputWidth; w++) {
				output[h] += getChar(getDarkness(imagePixel[w + h * imageWidth])) + " ";
			}
		}
		return output;
	}

	private void saveString(String path, String[] output) {
		try {
			FileWriter writer = new FileWriter(new File(path));
			System.out.println(output[0].charAt(0));
			for (int i = 0; i < output.length; i++) {
				writer.write(output[i] + System.lineSeparator());
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private String getSavePath(String string) {
		char[] splitString = new char[string.length()];
		string.getChars(0, string.length(), splitString, 0);
		String path = "";
		for (int i = 0; i < string.length() - 4; i++) {
			path += splitString[i];
		}
		path += ".txt";
		
		return path;
	}

	private int[] loadImage(String path) {
		int[] pixels = null;
		try {
			BufferedImage image = ImageIO.read(new File(path));
			imageWidth = image.getWidth();
			imageHeight = image.getHeight();
			pixels = new int[imageWidth * imageHeight];
			image.getRGB(0, 0, imageWidth, imageHeight, pixels, 0, imageWidth);
		} catch (IOException e) {
			System.out.println("Unable to load: " + path);
			e.printStackTrace();
		}
		return pixels;
	}
	
	private int getDarkness(int rgb) {
		Color c = new Color(rgb);
		int total = 0;
		total += c.getRed() + c.getGreen() + c.getBlue();
		total /= 3;
		return total;
	}
	
	private String getChar(int darknessValue) {
		if (darknessValue >= 0 && darknessValue < 40) return "&";
		if (darknessValue >= 40 && darknessValue < 75) return "#";
		if (darknessValue >= 75 && darknessValue < 125) return "U";
		if (darknessValue >= 125 && darknessValue < 175) return "-";
		if (darknessValue >= 125 && darknessValue < 175) return "*";
		if (darknessValue >= 200 && darknessValue < 225) return "\"";
		if (darknessValue >= 225 && darknessValue <= 255) return ".";
		return "|";
	}
	
}
