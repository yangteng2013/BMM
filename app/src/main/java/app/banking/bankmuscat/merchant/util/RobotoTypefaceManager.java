/*
 * Copyright 2014 Evgeny Shishkin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.banking.bankmuscat.merchant.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseArray;

import static app.banking.bankmuscat.merchant.base.ActivityBase.BM_DATA;
import static app.banking.bankmuscat.merchant.base.ActivityBase.BM_LANGUAGE;
import static app.banking.bankmuscat.merchant.base.ActivityBase.LANGUAGE_ARABIC;
import static app.banking.bankmuscat.merchant.base.ActivityBase.LANGUAGE_NONE;

/**
 * The manager of roboto typefaces.
 *
 * @author Evgeny Shishkin
 */
public class RobotoTypefaceManager {
    private static Context _context;
    /**
     * Array of created typefaces for later reused.
     */
    private final static SparseArray<android.graphics.Typeface> mTypefaces = new SparseArray<android.graphics.Typeface>(22);

    /**
     * Obtain typeface.
     *
     * @param context       The Context the widget is running in, through which it can access the current theme, resources, etc.
     * @param typefaceValue The value of "typeface" attribute
     * @return specify {@link android.graphics.Typeface}
     * @throws IllegalArgumentException if unknown `typeface` attribute value.
     */
    public static android.graphics.Typeface obtainTypeface(Context context, int typefaceValue) throws IllegalArgumentException {

        android.graphics.Typeface typeface = mTypefaces.get(typefaceValue);
        if (typeface == null) {
            typeface = createTypeface(context, typefaceValue);
            mTypefaces.put(typefaceValue, typeface);
        }

        return typeface;
    }

    /**
     * Obtain typeface.
     *
     * @param context    The Context the widget is running in, through which it can access the current theme, resources, etc.
     * @param fontFamily The value of "fontFamily" attribute
     * @param textWeight The value of "textWeight" attribute
     * @param textStyle  The value of "textStyle" attribute
     * @return specify {@link android.graphics.Typeface}
     * @throws IllegalArgumentException if unknown `typeface` attribute value.
     */
    public static android.graphics.Typeface obtainTypeface(
            Context context, int fontFamily, int textWeight, int textStyle) throws IllegalArgumentException {
        int typefaceValue = getTypefaceValue(fontFamily, textWeight, textStyle);
        _context = context;
        return obtainTypeface(context, typefaceValue);
    }

    /**
     * @param fontFamily The value of "fontFamily" attribute
     * @param textWeight The value of "textWeight" attribute
     * @param textStyle  The value of "textStyle" attribute
     * @return typeface value
     */
    private static int getTypefaceValue(int fontFamily, int textWeight, int textStyle) {
        int typeface;
        if (fontFamily == FontFamily.ROBOTO) {
            if (textWeight == TextWeight.NORMAL) {
                switch (textStyle) {
                    case TextStyle.NORMAL:
                        typeface = Typeface.ROBOTO_REGULAR;
                        break;
                    case TextStyle.ITALIC:
                        typeface = Typeface.ROBOTO_ITALIC;
                        break;
                    default:
                        throw new IllegalArgumentException("`textStyle` attribute value " + textStyle +
                                " is not supported for this fontFamily " + fontFamily +
                                " and textWeight " + textWeight);
                }
            } else if (textWeight == TextWeight.THIN) {
                switch (textStyle) {
                    case TextStyle.NORMAL:
                        typeface = Typeface.ROBOTO_THIN;
                        break;
                    case TextStyle.ITALIC:
                        typeface = Typeface.ROBOTO_THIN_ITALIC;
                        break;
                    default:
                        throw new IllegalArgumentException("`textStyle` attribute value " + textStyle +
                                " is not supported for this fontFamily " + fontFamily +
                                " and textWeight " + textWeight);
                }
            } else if (textWeight == TextWeight.LIGHT) {
                switch (textStyle) {
                    case TextStyle.NORMAL:
                        typeface = Typeface.ROBOTO_LIGHT;
                        break;
                    case TextStyle.ITALIC:
                        typeface = Typeface.ROBOTO_LIGHT_ITALIC;
                        break;
                    default:
                        throw new IllegalArgumentException("`textStyle` attribute value " + textStyle +
                                " is not supported for this fontFamily " + fontFamily +
                                " and textWeight " + textWeight);
                }
            } else if (textWeight == TextWeight.MEDIUM) {
                switch (textStyle) {
                    case TextStyle.NORMAL:
                        typeface = Typeface.ROBOTO_MEDIUM;
                        break;
                    case TextStyle.ITALIC:
                        typeface = Typeface.ROBOTO_MEDIUM_ITALIC;
                        break;
                    default:
                        throw new IllegalArgumentException("`textStyle` attribute value " + textStyle +
                                " is not supported for this fontFamily " + fontFamily +
                                " and textWeight " + textWeight);
                }
            } else if (textWeight == TextWeight.BOLD) {
                switch (textStyle) {
                    case TextStyle.NORMAL:
                        typeface = Typeface.ROBOTO_BOLD;
                        break;
                    case TextStyle.ITALIC:
                        typeface = Typeface.ROBOTO_BOLD_ITALIC;
                        break;
                    default:
                        throw new IllegalArgumentException("`textStyle` attribute value " + textStyle +
                                " is not supported for this fontFamily " + fontFamily +
                                " and textWeight " + textWeight);
                }
            } else if (textWeight == TextWeight.ULTRA_BOLD) {
                switch (textStyle) {
                    case TextStyle.NORMAL:
                        typeface = Typeface.ROBOTO_BLACK;
                        break;
                    case TextStyle.ITALIC:
                        typeface = Typeface.ROBOTO_BLACK_ITALIC;
                        break;
                    default:
                        throw new IllegalArgumentException("`textStyle` attribute value " + textStyle +
                                " is not supported for this fontFamily " + fontFamily +
                                " and textWeight " + textWeight);
                }
            } else {
                throw new IllegalArgumentException("`textWeight` attribute value " + textWeight +
                        " is not supported for this font family " + fontFamily);
            }
        } else if (fontFamily == FontFamily.ROBOTO_CONDENSED) {
            if (textWeight == TextWeight.NORMAL) {
                switch (textStyle) {
                    case TextStyle.NORMAL:
                        typeface = Typeface.ROBOTO_CONDENSED_REGULAR;
                        break;
                    case TextStyle.ITALIC:
                        typeface = Typeface.ROBOTO_CONDENSED_ITALIC;
                        break;
                    default:
                        throw new IllegalArgumentException("`textStyle` attribute value " + textStyle +
                                " is not supported for this fontFamily " + fontFamily +
                                " and textWeight " + textWeight);
                }
            } else if (textWeight == TextWeight.LIGHT) {
                switch (textStyle) {
                    case TextStyle.NORMAL:
                        typeface = Typeface.ROBOTO_CONDENSED_LIGHT;
                        break;
                    case TextStyle.ITALIC:
                        typeface = Typeface.ROBOTO_CONDENSED_LIGHT_ITALIC;
                        break;
                    default:
                        throw new IllegalArgumentException("`textStyle` attribute value " + textStyle +
                                " is not supported for this fontFamily " + fontFamily +
                                " and textWeight " + textWeight);
                }
            } else if (textWeight == TextWeight.BOLD) {
                switch (textStyle) {
                    case TextStyle.NORMAL:
                        typeface = Typeface.ROBOTO_CONDENSED_BOLD;
                        break;
                    case TextStyle.ITALIC:
                        typeface = Typeface.ROBOTO_CONDENSED_BOLD_ITALIC;
                        break;
                    default:
                        throw new IllegalArgumentException("`textStyle` attribute value " + textStyle +
                                " is not supported for this fontFamily " + fontFamily +
                                " and textWeight " + textWeight);
                }
            } else {
                throw new IllegalArgumentException("`textWeight` attribute value " + textWeight +
                        " is not supported for this font family " + fontFamily);
            }
        } else if (fontFamily == FontFamily.ROBOTO_SLAB) {

            if (textStyle != TextStyle.NORMAL) {
                throw new IllegalArgumentException("`textStyle` attribute value " + textStyle +
                        " is not supported for this fontFamily " + fontFamily);
            }

            if (textWeight == TextWeight.NORMAL) {
                typeface = Typeface.ROBOTO_SLAB_REGULAR;

            } else if (textWeight == TextWeight.THIN) {
                typeface = Typeface.ROBOTO_SLAB_THIN;

            } else if (textWeight == TextWeight.LIGHT) {
                typeface = Typeface.ROBOTO_SLAB_LIGHT;

            } else if (textWeight == TextWeight.BOLD) {
                typeface = Typeface.ROBOTO_SLAB_BOLD;

            } else {
                throw new IllegalArgumentException("`textWeight` attribute value " + textWeight +
                        " is not supported for this font family " + fontFamily);
            }
        } else {
            throw new IllegalArgumentException("Unknown `fontFamily` attribute value " + fontFamily);
        }
        return typeface;
    }

    public static String BMReadData(String key, String defaultValue) {
        try {
            SharedPreferences settings = _context.getSharedPreferences(BM_DATA, Context.MODE_PRIVATE);
            return settings.getString(key, defaultValue);
        } catch (Exception e) {
        }
        return defaultValue;
    }


    /**
     * Create typeface from assets.
     *
     * @param context       The Context the widget is running in, through which it can
     *                      access the current theme, resources, etc.
     * @param typefaceValue The value of "typeface" attribute
     * @return Roboto {@link android.graphics.Typeface}
     * @throws IllegalArgumentException if unknown `typeface` attribute value.
     */
    private static android.graphics.Typeface createTypeface(Context context, int typefaceValue) throws IllegalArgumentException {
        String typefacePath;

        _context = context;

        if(BMReadData(BM_LANGUAGE, LANGUAGE_NONE).compareToIgnoreCase(LANGUAGE_ARABIC) == 0) {
            android.graphics.Typeface font = android.graphics.Typeface.createFromAsset(_context.getAssets(),                 
                    "fonts/GE SS Two Medium.otf");
        }
        else
        {
            android.graphics.Typeface font = android.graphics.Typeface.createFromAsset(_context.getAssets(),
                     "fonts/PreciousSansMedium.ttf");
        }


        switch (typefaceValue) {
            case Typeface.ROBOTO_THIN:
                typefacePath = "fonts/PreciousSansMedium.ttf";
                break;
            case Typeface.ROBOTO_THIN_ITALIC:
                typefacePath = "fonts/PreciousSansMedium.ttf";
                break;
            case Typeface.ROBOTO_LIGHT:
                typefacePath = "fonts/PreciousSansMedium.ttf";
                break;
            case Typeface.ROBOTO_LIGHT_ITALIC:
                typefacePath = "fonts/PreciousSansMedium.ttf";
                break;
            case Typeface.ROBOTO_REGULAR:
                typefacePath = "fonts/PreciousSansMedium.ttf";
                break;
            case Typeface.ROBOTO_ITALIC:
                typefacePath = "fonts/PreciousSansMedium.ttf";
                break;
            case Typeface.ROBOTO_MEDIUM:
                typefacePath = "fonts/PreciousSansMedium.ttf";
                break;
            case Typeface.ROBOTO_MEDIUM_ITALIC:
                typefacePath = "fonts/PreciousSansMedium.ttf";
                break;
            case Typeface.ROBOTO_BOLD:
                typefacePath = "fonts/PreciousSansMedium.ttf";
                break;
            case Typeface.ROBOTO_BOLD_ITALIC:
                typefacePath = "fonts/PreciousSansMedium.ttf";
                break;
            case Typeface.ROBOTO_BLACK:
                typefacePath = "fonts/PreciousSansMedium.ttf";
                break;
            case Typeface.ROBOTO_BLACK_ITALIC:
                typefacePath = "fonts/PreciousSansMedium.ttf";
                break;
            case Typeface.ROBOTO_CONDENSED_LIGHT:
                typefacePath = "fonts/PreciousSansMedium.ttf";
                break;
            case Typeface.ROBOTO_CONDENSED_LIGHT_ITALIC:
                typefacePath = "fonts/PreciousSansMedium.ttf";
                break;
            case Typeface.ROBOTO_CONDENSED_REGULAR:
                typefacePath = "fonts/PreciousSansMedium.ttf";
                break;
            case Typeface.ROBOTO_CONDENSED_ITALIC:
                typefacePath = "fonts/PreciousSansMedium.ttf";
                break;
            case Typeface.ROBOTO_CONDENSED_BOLD:
                typefacePath = "fonts/PreciousSansMedium.ttf";
                break;
            case Typeface.ROBOTO_CONDENSED_BOLD_ITALIC:
                typefacePath = "fonts/PreciousSansMedium.ttf";
                break;
            case Typeface.ROBOTO_SLAB_THIN:
                typefacePath = "fonts/PreciousSansMedium.ttf";
                break;
            case Typeface.ROBOTO_SLAB_LIGHT:
                typefacePath = "fonts/RPreciousSansMedium.ttf";
                break;
            case Typeface.ROBOTO_SLAB_REGULAR:
                typefacePath = "fonts/PreciousSansMedium.ttf";
                break;
            case Typeface.ROBOTO_SLAB_BOLD:
                typefacePath = "fonts/PreciousSansMedium.ttf";
                break;
            default:
                throw new IllegalArgumentException("Unknown `typeface` attribute value " + typefaceValue);
        }


        /*if(BMReadData(BM_LANGUAGE, LANGUAGE_NONE).compareToIgnoreCase(LANGUAGE_ARABIC) == 0) {
            switch (typefaceValue) {
                case Typeface.ROBOTO_THIN:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                case Typeface.ROBOTO_THIN_ITALIC:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                case Typeface.ROBOTO_LIGHT:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                case Typeface.ROBOTO_LIGHT_ITALIC:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                case Typeface.ROBOTO_REGULAR:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                case Typeface.ROBOTO_ITALIC:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                case Typeface.ROBOTO_MEDIUM:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                case Typeface.ROBOTO_MEDIUM_ITALIC:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                case Typeface.ROBOTO_BOLD:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                case Typeface.ROBOTO_BOLD_ITALIC:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                case Typeface.ROBOTO_BLACK:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                case Typeface.ROBOTO_BLACK_ITALIC:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                case Typeface.ROBOTO_CONDENSED_LIGHT:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                case Typeface.ROBOTO_CONDENSED_LIGHT_ITALIC:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                case Typeface.ROBOTO_CONDENSED_REGULAR:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                case Typeface.ROBOTO_CONDENSED_ITALIC:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                case Typeface.ROBOTO_CONDENSED_BOLD:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                case Typeface.ROBOTO_CONDENSED_BOLD_ITALIC:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                case Typeface.ROBOTO_SLAB_THIN:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                case Typeface.ROBOTO_SLAB_LIGHT:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                case Typeface.ROBOTO_SLAB_REGULAR:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                case Typeface.ROBOTO_SLAB_BOLD:
                    typefacePath = "fonts/GE SS Two Medium.otf";
                    break;
                default:
                    throw new IllegalArgumentException("Unknown `typeface` attribute value " + typefaceValue);
            }
        }
        else
        {
            switch (typefaceValue) {
                case Typeface.ROBOTO_THIN:
                    typefacePath = "fonts/PreciousSansMedium.ttf";
                    break;
                case Typeface.ROBOTO_THIN_ITALIC:
                    typefacePath = "fonts/PreciousSansMedium.ttf";
                    break;
                case Typeface.ROBOTO_LIGHT:
                    typefacePath = "fonts/PreciousSansMedium.ttf";
                    break;
                case Typeface.ROBOTO_LIGHT_ITALIC:
                    typefacePath = "fonts/PreciousSansMedium.ttf";
                    break;
                case Typeface.ROBOTO_REGULAR:
                    typefacePath = "fonts/PreciousSansMedium.ttf";
                    break;
                case Typeface.ROBOTO_ITALIC:
                    typefacePath = "fonts/PreciousSansMedium.ttf";
                    break;
                case Typeface.ROBOTO_MEDIUM:
                    typefacePath = "fonts/PreciousSansMedium.ttf";
                    break;
                case Typeface.ROBOTO_MEDIUM_ITALIC:
                    typefacePath = "fonts/PreciousSansMedium.ttf";
                    break;
                case Typeface.ROBOTO_BOLD:
                    typefacePath = "fonts/PreciousSansMedium.ttf";
                    break;
                case Typeface.ROBOTO_BOLD_ITALIC:
                    typefacePath = "fonts/PreciousSansMedium.ttf";
                    break;
                case Typeface.ROBOTO_BLACK:
                    typefacePath = "fonts/PreciousSansMedium.ttf";
                    break;
                case Typeface.ROBOTO_BLACK_ITALIC:
                    typefacePath = "fonts/PreciousSansMedium.ttf";
                    break;
                case Typeface.ROBOTO_CONDENSED_LIGHT:
                    typefacePath = "fonts/PreciousSansMedium.ttf";
                    break;
                case Typeface.ROBOTO_CONDENSED_LIGHT_ITALIC:
                    typefacePath = "fonts/PreciousSansMedium.ttf";
                    break;
                case Typeface.ROBOTO_CONDENSED_REGULAR:
                    typefacePath = "fonts/PreciousSansMedium.ttf";
                    break;
                case Typeface.ROBOTO_CONDENSED_ITALIC:
                    typefacePath = "fonts/PreciousSansMedium.ttf";
                    break;
                case Typeface.ROBOTO_CONDENSED_BOLD:
                    typefacePath = "fonts/PreciousSansMedium.ttf";
                    break;
                case Typeface.ROBOTO_CONDENSED_BOLD_ITALIC:
                    typefacePath = "fonts/PreciousSansMedium.ttf";
                    break;
                case Typeface.ROBOTO_SLAB_THIN:
                    typefacePath = "fonts/PreciousSansMedium.ttf";
                    break;
                case Typeface.ROBOTO_SLAB_LIGHT:
                    typefacePath = "fonts/RPreciousSansMedium.ttf";
                    break;
                case Typeface.ROBOTO_SLAB_REGULAR:
                    typefacePath = "fonts/PreciousSansMedium.ttf";
                    break;
                case Typeface.ROBOTO_SLAB_BOLD:
                    typefacePath = "fonts/PreciousSansMedium.ttf";
                    break;
                default:
                    throw new IllegalArgumentException("Unknown `typeface` attribute value " + typefaceValue);
            }
        }*/



        return android.graphics.Typeface.createFromAsset(context.getAssets(), typefacePath);
    }

    private String GetFontPath()
    {
        return "fonts/PreciousSansMedium.ttf";
    }

    /**
     * Available values ​​for the "typeface" attribute.
     */
    public class Typeface {
        public final static int ROBOTO_THIN = 0;
        public final static int ROBOTO_THIN_ITALIC = 1;
        public final static int ROBOTO_LIGHT = 2;
        public final static int ROBOTO_LIGHT_ITALIC = 3;
        public final static int ROBOTO_REGULAR = 4;
        public final static int ROBOTO_ITALIC = 5;
        public final static int ROBOTO_MEDIUM = 6;
        public final static int ROBOTO_MEDIUM_ITALIC = 7;
        public final static int ROBOTO_BOLD = 8;
        public final static int ROBOTO_BOLD_ITALIC = 9;
        public final static int ROBOTO_BLACK = 10;
        public final static int ROBOTO_BLACK_ITALIC = 11;
        public final static int ROBOTO_CONDENSED_LIGHT = 12;
        public final static int ROBOTO_CONDENSED_LIGHT_ITALIC = 13;
        public final static int ROBOTO_CONDENSED_REGULAR = 14;
        public final static int ROBOTO_CONDENSED_ITALIC = 15;
        public final static int ROBOTO_CONDENSED_BOLD = 16;
        public final static int ROBOTO_CONDENSED_BOLD_ITALIC = 17;
        public final static int ROBOTO_SLAB_THIN = 18;
        public final static int ROBOTO_SLAB_LIGHT = 19;
        public final static int ROBOTO_SLAB_REGULAR = 20;
        public final static int ROBOTO_SLAB_BOLD = 21;
    }

    /**
     * Available values ​​for the "fontFamily" attribute.
     */
    public class FontFamily {
        public static final int ROBOTO = 0;
        public static final int ROBOTO_CONDENSED = 1;
        public static final int ROBOTO_SLAB = 2;
    }

    /**
     * Available values ​​for the "textWeight" attribute.
     */
    public class TextWeight {
        public static final int NORMAL = 0;
        public static final int THIN = 1;
        public static final int LIGHT = 2;
        public static final int MEDIUM = 3;
        public static final int BOLD = 4;
        public static final int ULTRA_BOLD = 5;
    }

    /**
     * Available values ​​for the "textStyle" attribute.
     */
    public class TextStyle {
        public static final int NORMAL = 0;
        public static final int ITALIC = 1;
    }

}
