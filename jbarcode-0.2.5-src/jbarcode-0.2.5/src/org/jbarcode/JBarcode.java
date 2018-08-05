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
package org.jbarcode;

import java.awt.image.BufferedImage;

import org.jbarcode.encode.BarSet;
import org.jbarcode.encode.BarcodeEncoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.paint.BarcodePainter;
import org.jbarcode.paint.TextPainter;




/**
 * A barcode class implemented using Java 2D APIs.
 * <br>
 * JBarcode coordinates three objects - <code>BarcodeEncoder</code> 
 * encode a text, <code>BarcodePaint</code> implements a stratergy to
 * paint the code, and <code>TextPainter</code> implements the barcode
 * text drawing - and several attibutes to achieve its aim of being able
 * to draw a barcode on a Java 2D graphics device.  
 * 
 * @author Flavio Sampaio
 * @since 0.1
 */
public class JBarcode {
    
    public static final double MIN_XDIMENSION = 0.03;

	private int barHeigth;

	private double wideToNarrow;

    private int xdimension;

	private boolean showText;

	private boolean checkDigit;

	private boolean showCheckDigit;

	private BarcodeEncoder bcencoder;

	private BarcodePainter bcpainter;

    private TextPainter textpainter;

    /**
     * TODO: Description.
     * 
     * @param encoder
     * @param painter
     * @param textpainter
     * @param wnRatio
     * @param nWidth
     * @param barHeight
     */
    public JBarcode(BarcodeEncoder encoder, BarcodePainter painter, TextPainter textpainter, double xdimension, int barHeight, double wideRatio){
        this.bcencoder = encoder;
        this.bcpainter = painter;
        this.textpainter = textpainter;
        this.barHeigth = barHeight;
        this.xdimension = (int)(xdimension/MIN_XDIMENSION);
        this.wideToNarrow = wideRatio;
        this.showCheckDigit = true;
        this.checkDigit = true;
        this.showText = true;
    }

    /**
     * TODO: Description.
     * 
     * @param encoder
     * @param painter
     * @param textpainter
     */
    public JBarcode(BarcodeEncoder encoder, BarcodePainter painter, TextPainter textpainter){
        this(encoder, painter, textpainter, MIN_XDIMENSION, 60, 2.0);
    }
    
    /**
     * TODO: Description.
     * 
     * @param code
     * @return
     * @throws InvalidAtributeException
     */
    public BufferedImage createBarcode(String code) throws InvalidAtributeException{
        String tmp = new String(code);
        if(isCheckDigit()){
            code = code + bcencoder.computeCheckSum(code);
            if(isShowCheckDigit()){
                tmp = code;
            }
            
        }
        BarSet [] encoded = bcencoder.encode(code);
        BufferedImage img = bcpainter.paint(encoded, xdimension, barHeigth, wideToNarrow);
        if(isShowText()){
            textpainter.paintText(img, tmp, xdimension);
        }
        return img;
    }
    
    /**
     * TODO: Description.
     * 
     * @param text
     * @return
     * @throws InvalidAtributeException
     */
    public String calcCheckSum(String text) throws InvalidAtributeException{
        return bcencoder.computeCheckSum(text);
    }

    /**
     * TODO: Description.
     * 
     * @return Returns the barHeigth.
     */
    public double getBarHeigth() {
        return barHeigth;
    }

    /**
     * TODO: Description.
     * 
     * @param barHeigth The barHeigth to set.
     */
    public void setBarHeigth(double barHeigth) {
        this.barHeigth = (int)Math.round(barHeigth/MIN_XDIMENSION);
    }

    /**
     * TODO: Description.
     * 
     * @return Returns the bcencoder.
     */
    public BarcodeEncoder getEncoder() {
        return bcencoder;
    }

    /**
     * TODO: Description.
     * 
     * @param bcencoder The bcencoder to set.
     */
    public void setEncoder(BarcodeEncoder bcencoder) {
        this.bcencoder = bcencoder;
    }

    /**
     * TODO: Description.
     * 
     * @return Returns the bcpainter.
     */
    public BarcodePainter getPainter() {
        return bcpainter;
    }

    /**
     * TODO: Description.
     * 
     * @param bcpainter The bcpainter to set.
     */
    public void setPainter(BarcodePainter bcpainter) {
        this.bcpainter = bcpainter;
    }

    /**
     * TODO: Description.
     * 
     * @return Returns the wideToNarrow.
     */
    public double getWideRatio() {
        return wideToNarrow;
    }

    /**
     * TODO: Description.
     * 
     * @param wideToNarrow The wideToNarrow to set.
     * @throws InvalidAtributeException 
     */
    public void setWideRatio(double wideRatio) throws InvalidAtributeException {
        if(wideRatio < 1){
            throw new InvalidAtributeException("[JBarcode] Invalid wide to narrow ratio value.");
        }    
        this.wideToNarrow = wideRatio;
    }

    /**
     * TODO: Description.
     * 
     * @return Returns the narrowWidth.
     */
    public double getXDimension() {
        return xdimension*MIN_XDIMENSION;
    }

    /**
     * TODO: Description.
     * 
     * @param narrowWidth The narrowWidth to set.
     */
    public void setXDimension(double xdimension) throws InvalidAtributeException {
        if(xdimension < MIN_XDIMENSION){
            throw new InvalidAtributeException("[JBarcode] Invalid x-dimention value.");
        }        
        this.xdimension = (int)(xdimension/MIN_XDIMENSION);
    }

    /**
     * TODO: Description.
     * 
     * @return Returns the checkDigit.
     */
    public boolean isCheckDigit() {
        return checkDigit;
    }

    /**
     * TODO: Description.
     * 
     * @param checkDigit The checkDigit to set.
     */
    public void setCheckDigit(boolean checkDigit) {
        this.checkDigit = checkDigit;
    }

    /**
     * TODO: Description.
     * 
     * @return Returns the showCheckDigit.
     */
    public boolean isShowCheckDigit() {
        return showCheckDigit;
    }

    /**
     * TODO: Description.
     * 
     * @param showCheckDigit The showCheckDigit to set.
     */
    public void setShowCheckDigit(boolean showCheckDigit) {
        this.showCheckDigit = showCheckDigit;
    }

    /**
     * TODO: Description.
     * 
     * @return Returns the showText.
     */
    public boolean isShowText() {
        return showText;
    }

    /**
     * TODO: Description.
     * 
     * @param showText The showText to set.
     */
    public void setShowText(boolean showText) {
        this.showText = showText;
    }

    /**
     * TODO: Description.
     * 
     * @return Returns the textpainter.
     */
    public TextPainter getTextPainter() {
        return textpainter;
    }

    /**
     * TODO: Description.
     * 
     * @param textpainter The textpainter to set.
     */
    public void setTextPainter(TextPainter textpainter) {
        this.textpainter = textpainter;
    }
    
    /**
     * TODO: Description.
     */
    public String toString(){
        return bcencoder.toString();
    }
}