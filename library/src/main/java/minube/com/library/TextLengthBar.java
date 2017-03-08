package minube.com.library;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;

/**
 * Created by franciscoalfacemartin on 7/3/17.
 */

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

        icon = typedArray.getDrawable(R.styleable.Tlb_barIcon);

        textFontPath = typedArray.getString(R.styleable.Tlb_textFontPath);
    }

    private void setupViews() {
        inflate(getContext(), R.layout.text_length_bar, this);
        loadViews();

        fillViewsWithContent(0, minChars);
    }

    private void fillViewsWithContent(int count, int minChars) {
        imageView.setImageDrawable(icon);

        message.setText(content.replace("%d", String.valueOf(minChars - count)));
        message.setTextColor(textColor);
        message.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        Typeface typeFace=Typeface.createFromAsset(getContext().getApplicationContext().getAssets(),textFontPath);
        message.setTypeface(typeFace);

        rootView.setBackgroundColor(backgroundColor);
    }

    private void loadViews() {
        message = (TextView) findViewById(R.id.text);
        imageView = (ImageView) findViewById(R.id.icon);
        rootView = (RelativeLayout) findViewById(R.id.root_view);
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

            ObjectAnimator objectAnimator =
                ObjectAnimator.ofInt(rootView, "background", Color.BLACK, currentState.backgroundColor);
            objectAnimator.setDuration(1500).setInterpolator(new OvershootInterpolator(2.f));
            objectAnimator.start();

            rootView.setBackgroundColor(ContextCompat.getColor(getContext(), currentState.getBackgroundColor()));
            imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), currentState.getIcon()));
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
