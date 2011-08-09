package com.xsis.ics.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class ImageUtil {
	public static BufferedImage resizeImage(BufferedImage img, int width, int height){
		BufferedImage nimg = new BufferedImage(width,height,img.getType());
		Graphics2D g2 = nimg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(img,0,0,width,height,0,0,img.getWidth(),img.getHeight(),null);
		g2.dispose();
		return nimg;		
	}
}
