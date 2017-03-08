package minube.com.library;

import android.support.annotation.NonNull;

/**
 * Created by franciscoalfacemartin on 7/3/17.
 */

public class TextLengthBarState implements Comparable<TextLengthBarState> {

    protected int icon;
    protected String text;
    protected int charsLimit;
    protected int backgroundColor;

    private TextLengthBarState(Builder builder) {
        icon = builder.icon;
        text = builder.text;
        charsLimit = builder.charsLimit;
        backgroundColor = builder.backgroundColor;
    }

    @Override public int compareTo(@NonNull TextLengthBarState o) {
        return this.charsLimit < o.getCharsLimit() ? 0 : 1;
    }

    public static final class Builder {
        private int icon;
        private String text;
        private int charsLimit;
        private int backgroundColor;

        public Builder(int charsLimit, String text) {
            this.charsLimit = charsLimit;
            this.text = text;
        }

        public Builder icon(int val) {
            icon = val;
            return this;
        }

        public Builder backgroundColor(int val) {
            backgroundColor = val;
            return this;
        }

        public TextLengthBarState build() {
            return new TextLengthBarState(this);
        }
    }

    public int getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }

    public int getCharsLimit() {
        return charsLimit;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }
}
