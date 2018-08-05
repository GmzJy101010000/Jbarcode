/* ================================================================
 * JBarcode : Java Barcode Library
 * ================================================================
 *
 * Project Info:  http://jbcode.sourceforge.net
 * Project Lead:  Fl�vio Sampaio (flavio@ronisons.com);
 *
 * (C) Copyright 2005, by Favio Sampaio
 *
 * This library is free software; you can redistribute it and/or modify it underthe terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 */
package org.jbarcode.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

/**
 * TODO: Description.
 * 
 * @author Flávio Sampaio
 * @since 0.1
 */
public class ImageUtil {
	
	public static final String JPEG = "jpeg";
	public static final String PNG = "png";
	
	public static byte[] encode(BufferedImage image, String format) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		encodeAndWrite(image, format, out);
		return out.toByteArray();
	}
	
	public static void encodeAndWrite(BufferedImage image, String format, OutputStream out) throws IOException{
		ImageIO.write(image, format, out);
	}

}
