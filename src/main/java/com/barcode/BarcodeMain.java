
package com.barcode;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
 
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;
 


import org.apache.commons.lang.StringUtils;
import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WidthCodedPainter;
 


import sun.misc.BASE64Encoder;
 
public class BarcodeMain {
	
	/**
     * 128条形码
     *
     * @param strBarCode
     *            条形码：0-100位
     * @param dimension
     *            商品条形码：尺寸
     * @param barheight
     *            商品条形码：高度
     * @return 图片(Base64编码)
     */
	  public static String generateBarCode128(String strBarCode,String dimension,String barheight) {
	        
	 
	        try {
	        	ByteArrayOutputStream outputStream = null;
	            BufferedImage bi = null;
	            int len = strBarCode.length();
	            JBarcode productBarcode = new JBarcode(Code128Encoder.getInstance(),
	                    WidthCodedPainter.getInstance(),
	                    EAN13TextPainter.getInstance());
	 
	            // 尺寸，面积，大小 密集程度
	            productBarcode.setXDimension(Double.valueOf(dimension).doubleValue());
	            // 高度 10.0 = 1cm 默认1.5cm
	            productBarcode.setBarHeigth(Double.valueOf(barheight).doubleValue());
	            // 宽度
	            productBarcode.setWideRatio(Double.valueOf(30).doubleValue());
//	      		    是否显示字体
	            productBarcode.setShowText(true);
//	         	   显示字体样式
	            productBarcode.setTextPainter(BaseLineTextPainter.getInstance()); 
	 
	            // 生成二维码
	            bi = productBarcode.createBarcode(strBarCode);
	            
	            outputStream = new ByteArrayOutputStream();
	            ImageIO.write(bi, "jpg", outputStream);
	            BASE64Encoder encoder = new BASE64Encoder();
//	          System.err.println(encoder.encode(outputStream.toByteArray()));
 
	            return encoder.encode(outputStream.toByteArray());
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "encodeError";
	        }
	    }
	
	
	
	
 
    /**
     * 商品条形码
     * @param strBarCode
     *            商品条形码：13位
     * @param dimension
     *            商品条形码：尺寸
     * @param barheight
     *            商品条形码：高度
     * @return 图片(Base64编码)
     */
    public static String generateBarCode(String strBarCode,String dimension,String barheight) {
// 		isNumeric 是否是数值
//    	校验。。。。。
        
 
        try {
        	FileOutputStream outputStream = null;
            BufferedImage bi = null;
            int len = strBarCode.length();
            JBarcode productBarcode = new JBarcode(EAN13Encoder.getInstance(),
                    WidthCodedPainter.getInstance(),
                    EAN13TextPainter.getInstance());
            
            String barCode = strBarCode.substring(0, len - 1);
            String code = strBarCode.substring(len - 1, len);
            
            //校验13位
            String checkCode = productBarcode.calcCheckSum(barCode);
            if (!code.equals(checkCode)) {
                return "checkCodeError";
            }
 
 
            // 尺寸，面积，大小
            productBarcode.setXDimension(Double.valueOf(dimension).doubleValue());
            // 高度 10.0 = 1cm 默认1.5cm
            productBarcode.setBarHeigth(Double.valueOf(barheight).doubleValue());
            // 宽度
            productBarcode.setWideRatio(Double.valueOf(25).doubleValue());
            
            // 是否校验13位，默认false
            productBarcode.setShowCheckDigit(true);
            
          //显示字符串内容中是否显示检查码内容
//          productBarcode.setShowCheckDigit(true);
 
            // 生成二维码
            bi = productBarcode.createBarcode(barCode);
            
            outputStream = new FileOutputStream(new File("D:\\Users\\ZGM\\Desktop\\barcode\\1.png"));
            ImageIO.write(bi, "jpg", outputStream);
            BASE64Encoder encoder = new BASE64Encoder();
//          System.err.println(encoder.encode(outputStream.toByteArray()));
 
       //     return encoder.encode(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return "encodeError";
        }
		return barheight;
    }
 
    /**
     * @param args
     * @throws InvalidAtributeException
     */
    public static void main(String[] args) throws InvalidAtributeException {
 
        String encode = BarcodeMain.generateBarCode("6936983800013","0.1","6");
        String encode2 = BarcodeMain.generateBarCode128("69369833450938430579753045230800013","0.5","30");
 
        System.out.println(encode);
 
    }
 
}