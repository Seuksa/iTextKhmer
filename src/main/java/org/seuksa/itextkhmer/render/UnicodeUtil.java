/**
 * Create on 12/03/2012.
 */
package org.seuksa.itextkhmer.render;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * UnicodeUtil Class.
 *    The class from the rendering of Mobile Project, Android from Nokor Group (AKA: Nokor-IT)
 *    The understanding also taking from the Khmum Browser that would lead to build this helper
 *    (Comment above by Pongsametrey S. <metrey@osify.com>)
 * @author huot.pengleng
 * @version $Revision$
 */
public class UnicodeUtil {

    private static final String COENG = Character.toString(UnicodeUtil.unicodeChar("0x17D2"));
    private static Map<String, String> subConsonant;
    private static Map<String, String> vowelBelow;
    private static Map<String, String> vowelAbove;
    private static Map<String, String> changeVowel;
    private static Map<String, String> consonantShifter;

    public static char unicodeChar(final String strInput) {
        return (char) (Integer.parseInt(strInput.substring(2, strInput.length()), 16));
    }

    /**
     * Get subscription consonant key.
     * @param charValue char value.
     * @return Subscription consonant key
     */
    private static String getSubConsonantKey(final char charValue) {
        return COENG.concat(Character.toString(charValue));
    }

    /**
     * Get khmer key.
     * @param charValue char key.
     * @return Khmer key.
     */
    private static String getKey(final char charValue) {
        return Character.toString(charValue);
    }

    /**
     * Get Khmer vowel below by key.
     * @param key Key as string.
     * @return Khmer vowel below.
     */
    public static String getVowelBelow(final String key) {
        if (vowelBelow == null) {
            loadVowelBelow();
        }
        if (vowelBelow.containsKey(key)) {
            return Character.toString(UnicodeUtil.unicodeChar(vowelBelow.get(key)));
        }
        return key;
    }

    /**
     * Get Khmer vowel above by key.
     * @param key Key as string.
     * @return Khmer vowel above.
     */
    public static String getVowelAbove(final String key) {
        if (vowelAbove == null) {
            loadVowelAbove();
        }
        if (vowelAbove.containsKey(key)) {
            return Character.toString(UnicodeUtil.unicodeChar(vowelAbove.get(key)));
        }
        return key;
    }

    /**
     * Get Khmer change vowel by key.
     * @param key Key as string.
     * @return Khmer change vowel.
     */
    public static String change(final String key) {
        if (changeVowel == null) {
            loadChangeVowel();
        }
        if (changeVowel.containsKey(key)) {
            return Character.toString(UnicodeUtil.unicodeChar(changeVowel.get(key)));
        }
        return key;
    }

    public static String getConsonantShifter(final String key) {
        if (consonantShifter == null) {
            consonantShifter = new LinkedHashMap<String, String>();
            consonantShifter.put(getKey(UnicodeUtil.unicodeChar("0x17C9")), "0xF0D4");
            consonantShifter.put(getKey(UnicodeUtil.unicodeChar("0x17CA")), "0xF0DB");
        }
        if (consonantShifter.containsKey(key)) {
            return Character.toString(UnicodeUtil.unicodeChar(consonantShifter.get(key)));
        }
        return key;
    }

    /**
     * Get Khmer subscription consonant.
     * @param key Subscription consonant key.
     * @return Khmer subscription consonant.
     */
    public static String getSubConsonant(final String key) {
        if (subConsonant == null) {
            loadConsonant();
        }
        if (subConsonant.containsKey(key)) {
            return Character.toString(UnicodeUtil.unicodeChar(subConsonant.get(key)));
        }
        return key;
    }

    /**
     * Load subscription by Consonant.
     */
    private static void loadConsonant() {
        subConsonant = new LinkedHashMap<String, String>();
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x1780")), "0xF000");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x1781")), "0xF001");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x1782")), "0xF002");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x1783")), "0xF003");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x1784")), "0xF004");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x1785")), "0xF005");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x1786")), "0xF006");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x1787")), "0xF007");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x1788")), "0xF008");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x1789")), "0xF009");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x178A")), "0xF00A");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x178B")), "0xF00B");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x178C")), "0xF00C");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x178D")), "0xF00D");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x178E")), "0xF00E");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x178F")), "0xF00F");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x1790")), "0xF010");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x1791")), "0xF011");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x1792")), "0xF012");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x1793")), "0xF013");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x1794")), "0xF014");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x1795")), "0xF015");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x1796")), "0xF016");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x1797")), "0xF017");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x1798")), "0xF018");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x1799")), "0xF019");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x179A")), "0xF01A");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x179B")), "0xF01B");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x179C")), "0xF01C");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x179F")), "0xF01F");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x17A0")), "0xF0A0");
        subConsonant.put(getSubConsonantKey(UnicodeUtil.unicodeChar("0x17A2")), "0xF0A2");
    }

    /**
     * Load vowel below.
     */
    private static void loadVowelBelow() {
        vowelBelow = new LinkedHashMap<String, String>();
        vowelBelow.put(getKey(UnicodeUtil.unicodeChar("0x17BB")), "0xF0A3");
        vowelBelow.put(getKey(UnicodeUtil.unicodeChar("0x17BC")), "0xF0A4");
        vowelBelow.put(getKey(UnicodeUtil.unicodeChar("0x17BD")), "0xF0A5");
    }

    /**
     * Load vowel above.
     */
    private static void loadVowelAbove() {
        vowelAbove = new LinkedHashMap<String, String>();
        vowelAbove.put(getKey(UnicodeUtil.unicodeChar("0x17B7")), "0xF0A6");
        vowelAbove.put(getKey(UnicodeUtil.unicodeChar("0x17B8")), "0xF0A7");
        vowelAbove.put(getKey(UnicodeUtil.unicodeChar("0x17B9")), "0xF0A8");
        vowelAbove.put(getKey(UnicodeUtil.unicodeChar("0x17BA")), "0xF0A9");
    }

    /**
     * Load change vowel.
     */
    private static void loadChangeVowel() {
        changeVowel = new LinkedHashMap<String, String>();
        changeVowel.put(getKey(UnicodeUtil.unicodeChar("0x17B7")), "0xF0CD"); //áž·
        changeVowel.put(getKey(UnicodeUtil.unicodeChar("0x17B8")), "0xF0CE"); //áž¸
        changeVowel.put(getKey(UnicodeUtil.unicodeChar("0x17B9")), "0xF0CF"); //áž¹
        changeVowel.put(getKey(UnicodeUtil.unicodeChar("0x17BA")), "0xF0D0"); //ážº
        changeVowel.put(getKey(UnicodeUtil.unicodeChar("0x17BB")), "0xF0DC"); //áž»
        changeVowel.put(getKey(UnicodeUtil.unicodeChar("0x17BC")), "0xF0DD"); //áž¼
        changeVowel.put(getKey(UnicodeUtil.unicodeChar("0x17BD")), "0xF0DE"); //áž½
        changeVowel.put(getKey(UnicodeUtil.unicodeChar("0x17C5")), "0xF0CC"); //áŸ…
        changeVowel.put(getKey(UnicodeUtil.unicodeChar("0x17C6")), "0xF0D3"); //áŸ†
        changeVowel.put(getKey(UnicodeUtil.unicodeChar("0x17BF")), "0xF0D1"); //áŸ€
        changeVowel.put(getKey(UnicodeUtil.unicodeChar("0x17C0")), "0xF0D2"); //áž¿

        //Sign Above
        changeVowel.put(getKey(UnicodeUtil.unicodeChar("0x17C9")), "0xF0D4"); //áŸ‰
        changeVowel.put(getKey(UnicodeUtil.unicodeChar("0x17CB")), "0xF0D5"); //áŸ‹
        changeVowel.put(getKey(UnicodeUtil.unicodeChar("0x17CC")), "0xF0D6"); //áŸŒ
        changeVowel.put(getKey(UnicodeUtil.unicodeChar("0x17CD")), "0xF0D7"); //áŸ�
        changeVowel.put(getKey(UnicodeUtil.unicodeChar("0x17CE")), "0xF0D8"); //
        changeVowel.put(getKey(UnicodeUtil.unicodeChar("0x17CF")), "0xF0D9"); //áŸ�
        changeVowel.put(getKey(UnicodeUtil.unicodeChar("0x17D0")), "0xF0DA"); //áŸ�
        changeVowel.put(getKey(UnicodeUtil.unicodeChar("0x17CA")), "0xF0DB"); //áŸŠ  
    }
}