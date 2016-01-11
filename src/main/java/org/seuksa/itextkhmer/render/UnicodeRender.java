/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seuksa.itextkhmer.render;

/**
 * Integrating existing rendering of Android for Khmer Unicode to iText
 *    The class from the rendering of Mobile Project, Android from Nokor Group (AKA: Nokor-IT)
 *    The understanding also taking from the Khmum Browser that would lead to build this helper
 *    (Comment above by Pongsametrey S. <metrey@osify.com>)
 * 	  Thanks for Nokor Group & Mr. Pengleng HUOT
 *
 * @author sok.pongsametrey
 * @version 1.0
 */

/**
 * UnicodeRender Class.
 * @author huot.pengleng
 *
 * simple classes, they are used in the state table (in this file) to control the length of a syllable
 * they are also used to know where a character should be placed (location in reference to the base character)
 * and also to know if a character, when independently displayed, should be displayed with a dotted-circle to
 * indicate error in syllable construction
 * Character class tables
 * _xx character does not combine into syllable, such as numbers, puntuation marks, non-Khmer signs...
 * _sa Sign placed above the base
 * _sp Sign placed after the base
 * _c1 Consonant of type 1 or independent vowel (independent vowels behave as type 1 consonants)
 * _c2 Consonant of type 2 (only RO)
 * _c3 Consonant of type 3
 * _rb Khmer sign robat u17CC. combining mark for subscript consonants
 * _cd Consonant-shifter
 * _dl Dependent vowel placed before the base (left of the base)
 * _db Dependent vowel placed below the base
 * _da Dependent vowel placed above the base
 * _dr Dependent vowel placed behind the base (right of the base)
 * _co Khmer combining mark COENG u17D2, combines with the consonant or independent vowel following
 *     it to create a subscript consonant or independent vowel
 * _va Khmer split vowel in wich the first part is before the base and the second one above the base
 * _vr Khmer split vowel in wich the first part is before the base and the second one behind (right of) the base
 *
 */
public class UnicodeRender {

    private final int _xx = 0;
    private final int CC_COENG = 7; // Subscript consonant combining character
    private final int CC_CONSONANT = 1; // Consonant of type 1 or independent vowel
    private final int CC_CONSONANT_SHIFTER = 5;
    private final int CC_CONSONANT2 = 2; // Consonant of type 2
    private final int CC_CONSONANT3 = 3; // Consonant of type 3
    private final int CC_DEPENDENT_VOWEL = 8;
    private final int CC_ROBAT = 6; // Khmer special diacritic accent -treated differently in state table
    private final int CC_SIGN_ABOVE = 9;
    private final int CC_SIGN_AFTER = 10;
    private final int CF_ABOVE_VOWEL = 536870912; // flag to speed up comparing
    private final int CF_CLASS_MASK = 65535;
    private final int CF_COENG = 134217728; // flag to speed up comparing
    private final int CF_CONSONANT = 16777216; // flag to speed up comparing
    private final int CF_DOTTED_CIRCLE = 67108864;

    // add a dotted circle if a character with this flag is the first in a syllable
    private final int CF_POS_ABOVE = 131072;
    private final int CF_POS_AFTER = 65536;
    private final int CF_POS_BEFORE = 524288;
    private final int CF_POS_BELOW = 262144;
    private final int CF_SHIFTER = 268435456; // flag to speed up comparing
    private final int CF_SPLIT_VOWEL = 33554432;
    private final int _c1 = CC_CONSONANT + CF_CONSONANT;
    private final int _c2 = CC_CONSONANT2 + CF_CONSONANT;
    private final int _c3 = CC_CONSONANT3 + CF_CONSONANT;
    private final int _co = CC_COENG + CF_COENG + CF_DOTTED_CIRCLE;
    private final int _cs = CC_CONSONANT_SHIFTER + CF_DOTTED_CIRCLE + CF_SHIFTER;
    private final int _da = CC_DEPENDENT_VOWEL + CF_POS_ABOVE + CF_DOTTED_CIRCLE + CF_ABOVE_VOWEL;
    private final int _db = CC_DEPENDENT_VOWEL + CF_POS_BELOW + CF_DOTTED_CIRCLE;
    private final int _dl = CC_DEPENDENT_VOWEL + CF_POS_BEFORE + CF_DOTTED_CIRCLE;
    private final int _dr = CC_DEPENDENT_VOWEL + CF_POS_AFTER + CF_DOTTED_CIRCLE;
    private final int _rb = CC_ROBAT + CF_POS_ABOVE + CF_DOTTED_CIRCLE;
    private final int _sa = CC_SIGN_ABOVE + CF_DOTTED_CIRCLE + CF_POS_ABOVE;
    private final int _sp = CC_SIGN_AFTER + CF_DOTTED_CIRCLE + CF_POS_AFTER;
    private final int _va = _da + CF_SPLIT_VOWEL;
    private final int _vr = _dr + CF_SPLIT_VOWEL;
    // flag for a split vowel -> the first part is added in front of the syllable
    private char BA;
    private char COENG;
    private String CONYO;
    private String CORO;

    private int[] khmerCharClasses = new int[] {
            _c1, _c1, _c1, _c3, _c1, _c1, _c1, _c1, _c3, _c1, _c1, _c1, _c1, _c3, _c1, _c1, _c1, _c1, _c1, _c1, _c3, _c1, _c1, _c1, _c1, _c3, _c2, _c1, _c1, _c1, _c3, _c3, _c1, _c3, _c1, _c1, _c1, _c1, _c1, _c1, _c1, _c1, _c1, _c1, _c1, _c1, _c1, _c1, _c1, _c1, _c1, _c1, _dr, _dr, _dr, _da, _da, _da, _da, _db, _db, _db, _va, _vr, _vr, _dl, _dl, _dl, _vr, _vr, _sa, _sp, _sp, _cs, _cs, _sa, _rb, _sa, _sa, _sa, _sa, _sa, _co, _sa, _xx, _xx, _xx, _xx, _xx, _xx, _xx, _xx, _xx, _sa, _xx, _xx
    };
    private short[][] khmerStateTable = new short[][] {
            {
                    1, 2, 2, 2, 1, 1, 1, 6, 1, 1, 1, 2
            }, {
                    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
            }, {
                    -1, -1, -1, -1, 3, 4, 5, 6, 16, 17, 1, -1
            }, {
                    -1, -1, -1, -1, -1, 4, -1, -1, 16, -1, -1, -1
            }, {
                    -1, -1, -1, -1, 15, -1, -1, 6, 16, 17, 1, 14
            }, {
                    -1, -1, -1, -1, -1, -1, -1, -1, 20, -1, 1, -1
            }, {
                    -1, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, -1
            }, {
                    -1, -1, -1, -1, 12, 13, -1, 10, 16, 17, 1, 14
            }, {
                    -1, -1, -1, -1, 12, 13, -1, -1, 16, 17, 1, 14
            }, {
                    -1, -1, -1, -1, 12, 13, -1, 10, 16, 17, 1, 14
            }, {
                    -1, 11, 11, 11, -1, -1, -1, -1, -1, -1, -1, -1
            }, {
                    -1, -1, -1, -1, 15, -1, -1, -1, 16, 17, 1, 14
            }, {
                    -1, -1, -1, -1, -1, 13, -1, -1, 16, -1, -1, -1
            }, {
                    -1, -1, -1, -1, 15, -1, -1, -1, 16, 17, 1, 14
            }, {
                    -1, -1, -1, -1, -1, -1, -1, -1, 16, -1, -1, -1
            }, {
                    -1, -1, -1, -1, -1, -1, -1, -1, 16, -1, -1, -1
            }, {
                    -1, -1, -1, -1, -1, -1, -1, -1, -1, 17, 1, 18
            }, {
                    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 18
            }, {
                    -1, -1, -1, -1, -1, -1, -1, 19, -1, -1, -1, -1
            }, {
                    -1, 1, -1, 1, -1, -1, -1, -1, -1, -1, -1, -1
            }, {
                    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, -1
            }
    };
    private char MARK;
    private char NYO;
    private char SA;
    private char SRAAA;
    private char SRAAU;
    private char SRAE;
    private char SRAIE;
    private char SRAII;
    private char SRAOE;
    private char SRAOO;
    private char SRAU;
    private char SRAYA;
    private char TRIISAP;
    private char YO;

    public UnicodeRender() {
        SRAAA = UnicodeUtil.unicodeChar("0x17B6");
        SRAE = UnicodeUtil.unicodeChar("0x17C1");
        SRAOE = UnicodeUtil.unicodeChar("0x17BE");
        SRAOO = UnicodeUtil.unicodeChar("0x17C4");
        SRAYA = UnicodeUtil.unicodeChar("0x17BF");
        SRAIE = UnicodeUtil.unicodeChar("0x17C0");
        SRAAU = UnicodeUtil.unicodeChar("0x17C5");
        SRAII = UnicodeUtil.unicodeChar("0x17B8");
        SRAU = UnicodeUtil.unicodeChar("0x17BB");
        TRIISAP = UnicodeUtil.unicodeChar("0x17CA");
        NYO = UnicodeUtil.unicodeChar("0x1789");
        BA = UnicodeUtil.unicodeChar("0x1794");
        YO = UnicodeUtil.unicodeChar("0x1799");
        SA = UnicodeUtil.unicodeChar("0x179F");
        COENG = UnicodeUtil.unicodeChar("0x17D2");
        CORO = Character.toString(UnicodeUtil.unicodeChar("0x17D2")).concat(Character.toString(UnicodeUtil.unicodeChar("0x179A")));
        CONYO = Character.toString(UnicodeUtil.unicodeChar("0x17D2")).concat(Character.toString(UnicodeUtil.unicodeChar("0x1789")));
        MARK = UnicodeUtil.unicodeChar("0x17EA");
    }

    private char strEcombining(final char chrInput) {
        char retChar = ' ';
        if (chrInput == SRAOE) {
            retChar = SRAII;
        }
        else if (chrInput == SRAYA) {
            retChar = SRAYA;
        }
        else if (chrInput == SRAIE) {
            retChar = SRAIE;
        }
        else if (chrInput == SRAOO) {
            retChar = SRAAA;
        }
        else if (chrInput == SRAAU) {
            retChar = SRAAU;
        }

        return retChar;
    }

    // Gets the charactor class.
    private int getCharClass(final char uniChar) {
        int retValue = 0;
        int ch;
        ch = uniChar;
        if (ch > 255) {
            if (ch >= UnicodeUtil.unicodeChar("0x1780")) {
                ch -= UnicodeUtil.unicodeChar("0x1780");
                if (ch < khmerCharClasses.length) {
                    retValue = khmerCharClasses[ch];
                }
            }
        }
        return retValue;
    }

    /**
     * Re-order Khmer unicode for display with Khmer.ttf file on Android.
     * @param strInput Khmer unicode string.
     * @return String after render.
     */
    public String render(final String strInput) {
        //Given an input String of unicode cluster to reorder.
        //The return is the visual based cluster (legacy style) String.

        int cursor = 0;
        short state = 0;
        int charCount = strInput.length();
        StringBuilder result = new StringBuilder();

        while (cursor < charCount) {
            String _reserved = "";
            String _signAbove = "";
            String _signAfter = "";
            String _base = "";
            String _robat = "";
            String _shifter = "";
            String _vowelBefore = "";
            String _vowelBelow = "";
            String _vowelAbove = "";
            String _vowelAfter = "";
            boolean _coeng = false;
            String _cluster;

            String _coeng1 = "";
            String _coeng2 = "";

            boolean _shifterAfterCoeng = false;

            while (cursor < charCount) {
                char curChar = strInput.charAt(cursor);
                int kChar = getCharClass(curChar);
                int charClass = kChar & CF_CLASS_MASK;
                try {
                    state = khmerStateTable[state][charClass];
                }
                catch (Exception ex) {
                    state = -1;
                }

                if (state < 0) {
                    break;
                }

                //collect variable for cluster here

                if (kChar == _xx) {
                    _reserved = Character.toString(curChar);
                }
                else if (kChar == _sa) //Sign placed above the base
                {
                    _signAbove = Character.toString(curChar);
                }
                else if (kChar == _sp) //Sign placed after the base
                {
                    _signAfter = Character.toString(curChar);
                }
                else if (kChar == _c1 || kChar == _c2 || kChar == _c3) //Consonant
                {
                    if (_coeng) {
                        if ("".equalsIgnoreCase(_coeng1)) {
                            _coeng1 = Character.toString(COENG).concat(Character.toString(curChar));
                        }
                        else {
                            _coeng2 = Character.toString(COENG).concat(Character.toString(curChar));
                        }
                        _coeng = false;
                    }
                    else {
                        _base = Character.toString(curChar);
                    }
                }
                else if (kChar == _rb) //Khmer sign robat u17CC
                {
                    _robat = Character.toString(curChar);
                }
                else if (kChar == _cs) //Consonant-shifter
                {
                    if (!"".equalsIgnoreCase(_coeng1)) {
                        _shifterAfterCoeng = true;
                    }

                    _shifter = Character.toString(curChar);
                }
                else if (kChar == _dl) //Dependent vowel placed before the base
                {
                    _vowelBefore = Character.toString(curChar);
                }
                else if (kChar == _db) //Dependent vowel placed below the base
                {
                    _vowelBelow = Character.toString(curChar);
                }
                else if (kChar == _da) //Dependent vowel placed above the base
                {
                    _vowelAbove = Character.toString(curChar);
                }
                else if (kChar == _dr) //Dependent vowel placed behind the base
                {
                    _vowelAfter = Character.toString(curChar);
                }
                else if (kChar == _co) //Khmer combining mark COENG
                {
                    _coeng = true;
                }
                else if (kChar == _va) //Khmer split vowel, see _da
                {
                    _vowelBefore = Character.toString(SRAE);
                    _vowelAbove = Character.toString(strEcombining(curChar));
                }
                else if (kChar == _vr) //Khmer split vowel, see _dr
                {
                    _vowelBefore = Character.toString(SRAE);
                    _vowelAfter = Character.toString(strEcombining(curChar));
                }

                cursor += 1;
            }
            // end of while (a cluster has found)

            // logic when cluster has coeng
            // should coeng be located on left side
            String _coengBefore = "";
            if (CORO.equalsIgnoreCase(_coeng1)) {
                _coengBefore = _coeng1;
                _coeng1 = "";
            }
            else if (CORO.equalsIgnoreCase(_coeng2)) {
                _coengBefore = _coeng2;
                _coeng2 = "";
            }

            if (!"".equalsIgnoreCase(_coeng1) || !"".equalsIgnoreCase(_coeng2)) {
                // NYO must change to other form when there is coeng
                if (Character.toString(NYO).equalsIgnoreCase(_base)) {
                    _base = Character.toString(UnicodeUtil.unicodeChar("0xF0AE"));

                    if (_coeng1.equalsIgnoreCase(CONYO)) {
                        _coeng1 = Character.toString(UnicodeUtil.unicodeChar("0xF0CB"));
                    }
                }
            }

            //logic of shifter with base character
            if (!"".equalsIgnoreCase(_base) && !"".equalsIgnoreCase(_shifter)) {
                //special case apply to BA only
                if (!"".equalsIgnoreCase(_vowelAbove) && Character.toString(BA).equalsIgnoreCase(_base) && Character.toString(TRIISAP).equalsIgnoreCase(_shifter)) {
                    _vowelAbove = UnicodeUtil.getVowelAbove(_vowelAbove);
                }
                else if (!"".equalsIgnoreCase(_vowelAbove)) {
                    _shifter = "";
                    _vowelBelow = Character.toString(SRAU);
                }
            }

            // uncomplete coeng
            if (_coeng && "".equalsIgnoreCase(_coeng1)) {
                _coeng1 = Character.toString(COENG);
            }
            else if (_coeng && "".equalsIgnoreCase(_coeng2)) {
                _coeng2 = Character.toString(MARK).concat(Character.toString(COENG));
            }

            //render DOTCIRCLE for standalone sign or vowel
            if ("".equalsIgnoreCase(_base) && ("".equalsIgnoreCase(_vowelBefore) || "".equalsIgnoreCase(_coengBefore) || !"".equalsIgnoreCase(_robat) || !"".equalsIgnoreCase(_shifter) || !"".equalsIgnoreCase(_coeng1) || !"".equalsIgnoreCase(_coeng2) || !"".equalsIgnoreCase(_vowelAfter) || !"".equalsIgnoreCase(_vowelBelow) || !"".equalsIgnoreCase(_vowelAbove) || !"".equalsIgnoreCase(_signAbove) || !"".equalsIgnoreCase(_signAfter))) {
                //_base = ""; //DOTCIRCLE
            }

            //place of shifter
            String _shifter1 = "";
            String _shifter2 = "";
            _shifter = UnicodeUtil.getConsonantShifter(_shifter);

            if (_shifterAfterCoeng) {
                _shifter2 = _shifter;
            }
            else {
                _shifter1 = _shifter;
            }

            boolean _specialCaseBA = false;
            String strMARK_SRAAA = Character.toString(MARK).concat(Character.toString(SRAAA));
            String strMARK_SRAAU = Character.toString(MARK).concat(Character.toString(SRAAU));

            if (Character.toString(BA).equalsIgnoreCase(_base) && (Character.toString(SRAAA).equalsIgnoreCase(_vowelAfter) || Character.toString(SRAAU).equalsIgnoreCase(_vowelAfter) || strMARK_SRAAA.equalsIgnoreCase(_vowelAfter) || strMARK_SRAAU.equalsIgnoreCase(_vowelAfter))) {
                // SRAAA or SRAAU will get a MARK if there is coeng, redefine to last char
                _base = Character.toString(UnicodeUtil.unicodeChar("0xF0AF"));
                _specialCaseBA = true;

                if (!"".equalsIgnoreCase(_coeng1)) {
                    String _coeng1Complete = _coeng1.substring(0, _coeng1.length() - 1);
                    if (Character.toString(BA).equalsIgnoreCase(_coeng1Complete) || Character.toString(YO).equalsIgnoreCase(_coeng1Complete) || Character.toString(SA).equalsIgnoreCase(_coeng1Complete)) {
                        _specialCaseBA = false;

                    }
                }
            }

            _coengBefore = UnicodeUtil.getSubConsonant(_coengBefore);
            _coeng1 = UnicodeUtil.getSubConsonant(_coeng1);
            _coeng2 = UnicodeUtil.getSubConsonant(_coeng2);
            _vowelAfter = UnicodeUtil.change(_vowelAfter);
            _signAbove = UnicodeUtil.change(_signAbove);

            if (!_robat.equalsIgnoreCase("")) {
                _vowelAbove = UnicodeUtil.getVowelAbove(_vowelAbove);
            }
            else {
                _vowelAbove = UnicodeUtil.change(_vowelAbove);
            }

            if (!_coeng1.equalsIgnoreCase("") || !_coeng2.equalsIgnoreCase("")) {
                _vowelBelow = UnicodeUtil.getVowelBelow(_vowelBelow);
            }
            else {
                _vowelBelow = UnicodeUtil.change(_vowelBelow);
            }

            // cluster formation
            if (_specialCaseBA) {
                _cluster = _vowelBefore + _coengBefore + _base + _vowelAfter + _robat + _shifter1 + _coeng1 + _coeng2 + _shifter2 + _vowelBelow + _vowelAbove + _signAbove + _signAfter;
            }
            else {
                _cluster = _vowelBefore + _coengBefore + _base + _robat + _shifter1 + _coeng1 + _coeng2 + _shifter2 + _vowelBelow + _vowelAbove + _vowelAfter + _signAbove + _signAfter;
            }
            //            + UnicodeUtil.unicodeChar("0x200B")
            result.append(_cluster + _reserved);
            state = 0;
            //end of while
        }

        return result.toString();
    }
}
