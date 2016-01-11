/**
 *
 */
package org.seuksa.itextkhmer;

import org.seuksa.itextkhmer.render.UnicodeRender;

import com.itextpdf.text.pdf.BidiLine;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.languages.LanguageProcessor;

/**
 * The ligaturizer specific language for Khmer to manage the specific render
 * The class is required by iText to understand how the render needs for the language
 * @author sok.pongsametrey <metrey@osify.com>
 * @version $Revision$
 */
public class KhmerLigaturizer implements LanguageProcessor {

	public KhmerLigaturizer () {

	}

	public String process(String s) {
		UnicodeRender khmerRender = new UnicodeRender();
		return BidiLine.processLTR(khmerRender.render(s), PdfWriter.RUN_DIRECTION_LTR, 0);
	}

	public boolean isRTL() {
		return false;
	}

}
