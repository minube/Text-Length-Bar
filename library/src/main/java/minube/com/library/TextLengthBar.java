/*
    MIT License

    Copyright (c) 2017 minube

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
*/

package minube.com.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class TextLengthBar extends RelativeLayout {
    private float textSize;
    private int textColor;
    private int backgroundColor;
    private String content;
    private Drawable icon;
    private String textFontPath;

    private TextView message;
    private ImageView imageView;
    private RelativeLayout rootView;

    private List<TextLengthBarState> states;
    private TextLengthBarState currentState;
    private int minChars;

    public TextLengthBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupAttrs(context, attrs);
        setupViews();
    }

    public TextLengthBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupAttrs(context, attrs);
        setupViews();
    }

    private void setupAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Tlb);
        textSize = typedArray.getDimension(R.styleable.Tlb_barMessageTextSize, 12);

        textColor = typedArray.getColor(R.styleable.Tlb_barMessageTextColor,
            ContextCompat.getColor(context, android.R.color.white));

        backgroundColor = typedArray.getColor(R.styleable.Tlb_barBackgroundColor,
            ContextCompat.getColor(context, android.R.color.holo_blue_dark));

        minChars = typedArray.getInt(R.styleable.Tlb_barMinChars, 1);

        content = typedArray.getString(R.styleable.Tlb_barMessage);

        String iconPathId = typedArray.getString(R.styleable.Tlb_barIcon);

        if (!TextUtils.isEmpty(iconPathId)) {
            int iconResourceId = context.getResources()
                .getIdentifier(iconPathId.split(Pattern.quote("/"))[2], "drawable", context.getPackageName());

            icon = (iconResourceId >= 0) ? typedArray.getDrawable(R.styleable.Tlb_barIcon) : null;
        }

        textFontPath = typedArray.getString(R.styleable.Tlb_textFontPath);
    }

    private void setupViews() {
        inflate(getContext(), R.layout.text_length_bar, this);
        loadViews();

        fillViewsWithContent(0, minChars);
    }

    private void fillViewsWithContent(int count, int minChars) {
        manageIconState();

        message.setText(content.replace("%d", String.valueOf(minChars - count)));
        message.setTextColor(textColor);
        message.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        if (!TextUtils.isEmpty(textFontPath)) {
            Typeface typeFace =
                Typeface.createFromAsset(getContext().getApplicationContext().getAssets(), textFontPath);
            message.setTypeface(typeFace);
        }

        rootView.setBackgroundColor(backgroundColor);
    }

    private void manageIconState() {
        if (icon != null) {
            imageView.setVisibility(VISIBLE);
            imageView.setImageDrawable(icon);
        } else {
            imageView.setVisibility(GONE);
        }
    }

    private void loadViews() {
        message = (TextView) findViewById(R.id.text);
        imageView = (ImageView) findViewById(R.id.icon);
        rootView = (RelativeLayout) findViewById(R.id.root_view);
    }

    public void setState(@NonNull TextLengthBarState state) {
        states.clear();
        states.add(state);
    }

    public void setStates(@NonNull List<TextLengthBarState> states) {
        this.states = states;
        Collections.sort(this.states);
    }

    public void attachToEditText(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                int textLength = editText.getText().length();
                currentState = getCurrentState(textLength);
                updateContentWithState(textLength);
            }

            @Override public void afterTextChanged(Editable s) {

            }
        });
    }

    private void updateContentWithState(int count) {
        if (currentState != null) {
            int minCharsState = currentState.getCharsLimit();
            message.setText(currentState.getText().toString().replace("%d", String.valueOf(minCharsState - count)));

            if (currentState.getBackgroundColor() >= 0) {
                rootView.setBackgroundColor(ContextCompat.getColor(getContext(), currentState.getBackgroundColor()));
            }

            if (currentState.getIcon() >= 0) {
                imageView.setVisibility(VISIBLE);
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), currentState.getIcon()));
            } else {
                imageView.setVisibility(GONE);
            }
        }
    }

    private TextLengthBarState getCurrentState(int count) {
        for (int i = 0; states != null && i < states.size(); i++) {
            if (i == 0) {
                if (count > minChars && count < states.get(0).getCharsLimit()) {
                    return states.get(0);
                }
            } else if (i > 0 && i < (states.size() - 1)) {
                if (count >= states.get(i - 1).getCharsLimit() && count < states.get(i).getCharsLimit()) {
                    return states.get(i);
                }
            } else {
                if (count >= states.get(i - 1).getCharsLimit()) {
                    return states.get(i);
                }
            }
        }
        fillViewsWithContent(count, minChars);
        return null;
    }
}
