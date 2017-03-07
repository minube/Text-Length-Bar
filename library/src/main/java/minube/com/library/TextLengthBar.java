package minube.com.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
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

    private List<TextLenthBarState> states;

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
        textSize = typedArray.getDimension(R.styleable.Tlb_barMessageTextSize, 16);

        textColor = typedArray.getColor(R.styleable.Tlb_barMessageTextColor,
            ContextCompat.getColor(context, android.R.color.white));

        backgroundColor = typedArray.getColor(R.styleable.Tlb_barBackgroundColor,
            ContextCompat.getColor(context, android.R.color.holo_blue_dark));

        content = typedArray.getString(R.styleable.Tlb_barMessage);

        icon = typedArray.getDrawable(R.styleable.Tlb_barIcon);

        textFontPath = typedArray.getString(R.styleable.Tlb_textFontPath);
    }

    private void setupViews() {
        inflate(getContext(), R.layout.text_length_bar, this);
        loadViews();

        imageView.setImageDrawable(icon);

        message.setText(content);
        message.setTextColor(textColor);
        message.setTextSize(textSize);

        rootView.setBackgroundColor(backgroundColor);
    }

    private void loadViews() {
        message = (TextView) findViewById(R.id.text);
        imageView = (ImageView) findViewById(R.id.icon);
        rootView = (RelativeLayout) findViewById(R.id.root_view);
    }

    public void setStates(List<TextLenthBarState> states) {
        this.states = states;
        Collections.sort(this.states);
    }

    public void atachToEditText(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                for (TextLenthBarState state : states) {
                    if (state.getCharsLimit() <= count) {
                        break;
                    }
                }
            }

            @Override public void afterTextChanged(Editable s) {

            }
        });
    }
}
