package org.seuksa.itextkhmer.table;

import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;

/** This class created is to display khmer unicode in table
 * it is extended from PdfPTable
 * @author bo.seng
 */
public class PdfPTableKhmer extends PdfPTable {

    public PdfPTableKhmer() {}
    public PdfPTableKhmer(float[] relativeWidths) { super(relativeWidths);}
    public PdfPTableKhmer(int numColumns) { super(numColumns);}
    public PdfPTableKhmer(PdfPTable table) { super(table);}
    
    public void addCell(String text, Font font) {
        addCell(new Phrase(text, font));
    }
}
