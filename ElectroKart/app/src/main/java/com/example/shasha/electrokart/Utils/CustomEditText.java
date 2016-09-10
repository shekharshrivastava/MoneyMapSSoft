package com.example.shasha.electrokart.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import com.example.shasha.electrokart.R;

/**
 * Created by shasha on 26-07-2016.
 */
public class CustomEditText extends EditText implements TextWatcher {
    private Typeface tf = null, tfhint = null;
    private String customhintfont, customFont;
    boolean inputtypepassword;
    private CharSequence chartype;

    // private CharSequence mSource;

    public CustomEditText(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        // initViews();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        // initViews();
        setCustomFontEdittext(context, attrs);

    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        // initViews();
        setCustomFontEdittext(context, attrs);
    }

    private void setCustomFontEdittext(Context ctx, AttributeSet attrs) {
        final TypedArray a = ctx.obtainStyledAttributes(attrs,
                R.styleable.CustomEditText);
        customFont = a.getString(R.styleable.CustomEditText_edittextfont);
        customhintfont = a
                .getString(R.styleable.CustomEditText_edittextfontHint);

        // custompwd = a.getString(R.styleable.CustomEditText_edittextpwd);
        inputtypepassword = a.getBoolean(
                R.styleable.CustomEditText_edittextpwd, false);
        setCustomFontEdittext(ctx, customFont);
        setCustomFontEdittextHint(ctx, customhintfont);

        chartype = (CharSequence) a
                .getText(R.styleable.CustomEditText_editcharpwd);
        a.recycle();
    }


    public boolean setCustomFontEdittext(Context ctx, String asset) {
        try {
            tf = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            return false;
        }
        setTypeface(tf);
        return true;
    }

    public boolean setCustomFontEdittextHint(Context ctx, String asset) {
        try {
            tfhint = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            return false;
        }
        setTypeface(tfhint);

        return true;
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore,
                              int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (text.length() < 0) {
            setCustomFontEdittextHint(getContext(), customhintfont);
        } else {
            setCustomFontEdittextHint(getContext(), customFont);
        }
        if (TextUtils.isEmpty(text)) {
            setCustomFontEdittextHint(getContext(), customhintfont);
        }
        // this.setText("*");
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }
}

