package app.banking.bankmuscat.merchant.util;

import android.app.Activity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.StringTokenizer;

public class CommonUtils {
    /**
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional
        // '-' and decimal.
    }

    public static class DecimalDigitsInputFilter implements InputFilter {

        private final int decimalDigits = 2;


		/*public DecimalDigitsInputFilter(int decimalDigits) {
            this.decimalDigits = decimalDigits;
		}*/

        public DecimalDigitsInputFilter() {

        }

        @Override
        public CharSequence filter(CharSequence source,
                                   int start,
                                   int end,
                                   Spanned dest,
                                   int dstart,
                                   int dend) {


            int dotPos = -1;
            int len = dest.length();
            for (int i = 0; i < len; i++) {
                char c = dest.charAt(i);
                if (c == '.' || c == ',') {
                    dotPos = i;
                    break;
                }
            }
            if (dotPos >= 0) {

                // protects against many dots
                if (source.equals(".") || source.equals(",")) {
                    return "";
                }
                // if the text is entered before the dot
                if (dend <= dotPos) {
                    return null;
                }
                if (len - dotPos > decimalDigits) {
                    return "";
                }
            }

            return null;
        }

    }



    /*public static void AdjustAmount(final EditText editText) {

        //editText = editTxt



        InputFilter[] filters = new InputFilter[2];
        filters[0] = new InputFilter.LengthFilter(8); //Filter to 10 characters
        filters[1] = new DecimalDigitsInputFilter();

        editText.setFilters(filters);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!view.hasFocus()) {
                    String userInput = editText.getText().toString();

                    int dotPos = -1;

                    for (int i = 0; i < userInput.length(); i++) {
                        char c = userInput.charAt(i);
                        if (c == '.') {
                            dotPos = i;
                        }
                    }

                    if (dotPos == -1) {
                        editText.setText(userInput + ".00");
                    } else {
                        if (userInput.length() - dotPos == 1) {
                            editText.setText(userInput + "00");
                        } else if (userInput.length() - dotPos == 2) {
                            editText.setText(userInput + "0");

                        }

                    }

                    *//*if (dotPos == -1){
                        edt_amount.setText(userInput + ".000");
                    } else {
                        if ( userInput.length() - dotPos == 1 ) {
                            edt_amount.setText(userInput + "000");
                        } else if ( userInput.length() - dotPos == 2 ) {
                            edt_amount.setText(userInput + "00");
                        }
                        else if ( userInput.length() - dotPos == 3 ) {
                            edt_amount.setText(userInput + "0");
                        }
                    }*//*


                    editText.setText(CommonUtils.AdjustAmountFraction(editText.getText().toString()));
                }
            }
        });
    }*/

    public static boolean ValidateAmount(final String amount) {

        try
        {
            Double.parseDouble(amount);
        }
        catch(NumberFormatException e)
        {
           return false;
        }

        return true;

    }



    public static String AdjustAmountFraction(final String amount) {

        String value = amount;



        if(value.length() == 0)
            value = "0.00";

        if(value.compareToIgnoreCase(".") == 0)
            value = "0.00";

        if(value.startsWith("."))
            value = "0" + value;

        if(value.endsWith("."))
            value = value+ "00";
        if(!value.contains("."))
            value = value+ ".00";

        if(value.length() > 2) {
            Character c = value.charAt(value.length() - 2);
            if (c.compareTo('.') == 0)
                value = value + "0";
        }


       /* try
        {
            Float val=Float.parseFloat(amount);
            Double vall = Double.parseDouble(amount);
            DecimalFormat df = new DecimalFormat("0.00");
            df.setMaximumFractionDigits(2);
            value = df.format(val);
            return value;
        }
        catch(NumberFormatException e)
        {
            return value;
        }*/

        return value;

    }

    public static void AdjustAmount(final EditText editText) {

        //editText = editTxt

        editText.addTextChangedListener(new CommonUtils.CustomTextWatcher(editText));

      /*  InputFilter[] filters = new InputFilter[2];

        filters[1] = new DecimalDigitsInputFilter();

        editText.setFilters(filters);*/

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!view.hasFocus()) {
                    String userInput = editText.getText().toString();

                    int dotPos = -1;

                    for (int i = 0; i < userInput.length(); i++) {
                        char c = userInput.charAt(i);
                        if (c == '.') {
                            dotPos = i;
                        }
                    }

                    if (dotPos == -1) {
                        editText.setText(userInput + ".00");
                    } else {
                        if (userInput.length() - dotPos == 1) {
                            editText.setText(userInput + "00");
                        } else if (userInput.length() - dotPos == 2) {
                            editText.setText(userInput + "0");

                        }

                    }

                    editText.setText(CommonUtils.AdjustAmountFraction(editText.getText().toString()));
                }
            }
        });
    }



   /* public class DecimalFilter implements TextWatcher {

        int count= -1 ;
        EditText et;
        Activity activity;

        public DecimalFilter(EditText edittext, Activity activity) {
            et = edittext;
            this.activity = activity;
        }

        public void afterTextChanged(Editable s) {

            if (s.length() > 0) {
                String str = et.getText().toString();
                et.setOnKeyListener(new View.OnKeyListener() {

                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL) {
                            count--;
                            InputFilter[] fArray = new InputFilter[1];
                            fArray[0] = new InputFilter.LengthFilter(100);//Re sets the maxLength of edittext to 100.
                            et.setFilters(fArray);
                        }
                        if (count > 2) {
                            Toast.makeText(activity, "Sorry! You cant enter more than two digits after decimal point!", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });

                char t = str.charAt(s.length() - 1);

                if (t == '.') {
                    count = 0;
                }

                if (count >= 0) {
                    if (count == 2) {
                        InputFilter[] fArray = new InputFilter[1];
                        fArray[0] = new InputFilter.LengthFilter(s.length());
                        et.setFilters(fArray); // sets edittext's maxLength to number of digits now entered.

                    }
                    count++;
                }
            }

        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
// TODO Auto-generated method stub
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
// TODO Auto-generated method stub
        }

    }*/



    public static class EditTextLocker {

        private EditText editText;

        private int charactersLimit;

        private int fractionLimit;

        public EditTextLocker(final EditText editText) {

            this.editText = editText;

            editText.setOnKeyListener(editTextOnKeyListener);
        }

        private View.OnKeyListener editTextOnKeyListener = new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    startStopEditing(false);
                }

                return false;
            }
        };

        private TextWatcher editTextWatcherForCharacterLimits = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!editText.getText().toString().equalsIgnoreCase("")) {

                    int editTextLength = editText.getText().toString().trim().length();

                    if (editTextLength >= charactersLimit) {

                        startStopEditing(true);

                    }

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        private TextWatcher editTextWatcherForFractionLimit = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!editText.getText().toString().equalsIgnoreCase("")) {

                    String editTextString = editText.getText().toString().trim();
                    int decimalIndexOf = editTextString.indexOf(".");

                    if (decimalIndexOf >= 0) {

                        if (editTextString.substring(decimalIndexOf).length() > fractionLimit) {

                            startStopEditing(true);

                        }
                    }

                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        public void limitCharacters(final int limit) {

            this.charactersLimit = limit;
            editText.addTextChangedListener(editTextWatcherForCharacterLimits);
        }

        public void limitFractionDigitsinDecimal(int fractionLimit) {

            this.fractionLimit = fractionLimit;
            editText.addTextChangedListener(editTextWatcherForFractionLimit);
        }

        public void unlockEditText() {

            startStopEditing(false);
        }

        public void startStopEditing(boolean isLock) {

            if (isLock) {

                editText.setFilters(new InputFilter[]{new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        return source.length() < 1 ? dest.subSequence(dstart, dend) : "";
                    }
                }});

            } else {

                editText.setFilters(new InputFilter[]{new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        return null;
                    }
                }});
            }
        }
    }



    public static class DecimalFilter implements TextWatcher {

        int count= -1 ;
        EditText et;
        Activity activity;

        public DecimalFilter(EditText edittext, Activity activity) {
            et = edittext;
            this.activity = activity;
        }

        public void afterTextChanged(Editable s) {

            String value = s.toString();
            StringTokenizer strTok = new StringTokenizer(value, ".");
            int numberOfTokens = strTok.countTokens();
            String[] splitArr = new String[numberOfTokens];
            for (int i = 0; i < numberOfTokens; i++) {
                splitArr[i] = strTok.nextToken();
            }

            if (splitArr[0].length() == 1) {
                s.append(".00");
            }


            if (splitArr[0].length() > 8) {
                splitArr[0]= splitArr[0].substring(0,7);
            }

            if (splitArr[1].length() > 2) {
                splitArr[1]= splitArr[0].substring(0,1);
            }

            s=  new SpannableStringBuilder( splitArr[1]+ splitArr[1]);

            if (s.length() > 0) {
                String str = et.getText().toString();
                et.setOnKeyListener(new View.OnKeyListener() {

                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL) {
                            count--;
                            InputFilter[] fArray = new InputFilter[1];
                            fArray[0] = new InputFilter.LengthFilter(8);//Re sets the maxLength of edittext to 100.
                            et.setFilters(fArray);
                        }
                        if (count > 2) {
                            Toast.makeText(activity, "Sorry! You cant enter more than two digits after decimal point!", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });

                char t = str.charAt(s.length() - 1);

                if (t == '.') {
                    count = 0;
                }

                if (count >= 0) {
                    if (count == 2) {
                        InputFilter[] fArray = new InputFilter[1];
                        fArray[0] = new InputFilter.LengthFilter(s.length());
                        et.setFilters(fArray); // sets edittext's maxLength to number of digits now entered.

                    }
                    count++;
                }
            }

        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
// TODO Auto-generated method stub
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
// TODO Auto-generated method stub
        }

    }


    public static String getOnlyNumerics(String str) {

        if (str == null) {
            return null;
        }

        StringBuffer strBuff = new StringBuffer();
        char c;

        for (int i = 0; i < str.length() ; i++) {
            c = str.charAt(i);

            if (Character.isDigit(c)) {
                strBuff.append(c);
            }
        }
        return strBuff.toString();
    }







    public static class CustomTextWatcher implements TextWatcher {
        private NumberFormat nf = NumberFormat.getNumberInstance();
        private EditText et;
        private String tmp = "";
        private int moveCaretTo;
        private static final int INTEGER_CONSTRAINT = 8;
        private static final int FRACTION_CONSTRAINT = 2;
        private static final int MAX_LENGTH = INTEGER_CONSTRAINT + FRACTION_CONSTRAINT + 1;

        public CustomTextWatcher(EditText et) {
            this.et = et;
            nf.setMaximumIntegerDigits(INTEGER_CONSTRAINT);
            nf.setMaximumFractionDigits(FRACTION_CONSTRAINT);
            nf.setGroupingUsed(false);
        }

        public int countOccurrences(String str, char c) {
            int count = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == c) {
                    count++;
                }
            }
            return count;
        }

        @Override
        public void afterTextChanged(Editable s) {
            et.removeTextChangedListener(this); // remove to prevent stackoverflow
            String ss = s.toString();
            int len = ss.length();
            int dots = countOccurrences(ss, '.');
            boolean shouldParse = dots <= 1 && (dots == 0 ? len != (INTEGER_CONSTRAINT + 1) : len < (MAX_LENGTH + 1));
            if (shouldParse) {
                if (len > 1 && ss.lastIndexOf(".") != len - 1) {
                    try {
                        Double d = Double.parseDouble(ss);
                        if (d != null) {
                            et.setText(nf.format(d));
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            } else {
                et.setText(tmp);
            }
            et.addTextChangedListener(this); // reset listener

            //tried to fix caret positioning after key type:
            if (et.getText().toString().length() > 0) {
                if (dots == 0 && len >= INTEGER_CONSTRAINT && moveCaretTo > INTEGER_CONSTRAINT) {
                    moveCaretTo = INTEGER_CONSTRAINT;
                } else if (dots > 0 && len >= (MAX_LENGTH) && moveCaretTo > (MAX_LENGTH)) {
                    moveCaretTo = MAX_LENGTH;
                }
                try {
                    et.setSelection(et.getText().toString().length());
                    // et.setSelection(moveCaretTo); <- almost had it :))
                } catch (Exception e) {
                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            moveCaretTo = et.getSelectionEnd();
            tmp = s.toString();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int length = et.getText().toString().length();
            if (length > 0) {
                moveCaretTo = start + count - before;
            }
        }
    }


    public static void ReplaceCharacter(final EditText edit,
                                           final String replaceValue, final String replaceWith) {
        edit.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                /** do noting */
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                /** do noting */
            }

            public void afterTextChanged(Editable s) {
                String nickNameValue = s.toString().replaceAll(replaceValue,
                        replaceWith);

                if (!s.toString().equals(nickNameValue)) {
                    edit.setText(nickNameValue);
                    edit.setSelection(nickNameValue.length());

                }

            }

        });
    }

}
