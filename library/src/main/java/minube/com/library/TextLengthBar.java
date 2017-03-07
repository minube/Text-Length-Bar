package minube.com.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    public TextLengthBar(Context context) {
        super(context);
    }

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
        textSize = typedArray.getDimension(R.styleable.Tlb_textSize, 16);

        textColor = typedArray.getColor(R.styleable.Tlb_textColor,
            ContextCompat.getColor(context, android.R.color.white));

        backgroundColor = typedArray.getColor(R.styleable.Tlb_textColor,
            ContextCompat.getColor(context, android.R.color.holo_red_light));

        content = typedArray.getString(R.styleable.Tlb_text);

        icon = typedArray.getDrawable(R.styleable.Tlb_barIcon);

        textFontPath = typedArray.getString(R.styleable.Tlb_textFontPath);
    }

    private void setupViews() {
        loadViews();

        message.setText(content);
        message.setTextColor(textColor);
        message.setTextSize(textSize);

        rootView.setBackgroundColor(backgroundColor);

        imageView.setImageDrawable(icon);
    }

    private void loadViews() {
        message = (TextView) findViewById(R.id.text);
        imageView = (ImageView) findViewById(R.id.icon);
        rootView = (RelativeLayout) findViewById(R.id.root_view);
    }
}
