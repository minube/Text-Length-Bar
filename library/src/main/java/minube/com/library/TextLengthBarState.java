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

import android.support.annotation.NonNull;

public class TextLengthBarState implements Comparable<TextLengthBarState> {

    protected int icon = -1;
    protected int backgroundColor = -1;
    protected int progressBarColor = -1;
    protected int textColor = -1;
    protected String text;
    protected int charsLimit;

    private TextLengthBarState(Builder builder) {
        icon = builder.icon;
        text = builder.text;
        charsLimit = builder.charsLimit;
        backgroundColor = builder.backgroundColor;
        textColor = builder.textColor;
        progressBarColor = builder.progressColor;
    }

    @Override public int compareTo(@NonNull TextLengthBarState o) {
        return this.charsLimit < o.getCharsLimit() ? 0 : 1;
    }

    public static final class Builder {
        private int icon;
        private String text;
        private int charsLimit;
        private int backgroundColor;
        private int progressColor;
        private int textColor;

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

        public Builder progressColor(int val) {
            progressColor = val;
            return this;
        }

        public Builder textColor(int val) {
            textColor = val;
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

    public int getProgressBarColor() {
        return progressBarColor;
    }

    public int getTextColor() {
        return progressBarColor;
    }
}
