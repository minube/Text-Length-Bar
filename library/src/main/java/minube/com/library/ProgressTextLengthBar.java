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

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

public class ProgressTextLengthBar extends TextLengthBar {

    private ProgressBar progressBar;

    public ProgressTextLengthBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressTextLengthBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override protected void setupViews() {
        inflate(getContext(), R.layout.progress_text_length_bar, this);
        loadViews();
        fillViewsWithContent(0, minChars);
    }

    @Override protected void loadViews() {
        super.loadViews();
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    @Override protected void fillViewsWithContent(int count, int minChars) {
        super.fillViewsWithContent(count, minChars);
        setProgressAnimate((int) calculateProgress(count, minChars));
        if (progressBarColor > 0) {
            setProgressBarColor(ContextCompat.getColor(getContext(), progressBarColor));
        }
    }

    @Override protected void updateContentWithState(int count, boolean isNewState) {
        super.updateContentWithState(count, isNewState);
        if (currentState != null) {
            if(isNewState) {
                progressBar.setProgress(0);
            }
            setProgressBarColor(currentState.getProgressBarColor());
            setProgressAnimate((int) calculateProgress(calculateRealCount(count),
                currentState.getCharsLimit() - calculateStateTotal()));
        }
    }

    private int calculateRealCount(int count) {
        int currentStateIndex = states.indexOf(currentState);
        if (currentStateIndex > 0) {
            return count - currentState.getCharsLimit();
        }
        return count - minChars;
    }

    private int calculateStateTotal() {
        int currentStateIndex = states.indexOf(currentState);
        if (currentStateIndex > 0) {
            return states.get(currentStateIndex - 1).getCharsLimit();
        }
        return minChars;
    }

    private void setProgressBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), color)));
        } else {
            LayerDrawable drawable = (LayerDrawable) progressBar.getProgressDrawable();
            Drawable background = new ColorDrawable(color);
            drawable.setDrawableByLayerId(android.R.id.progress, background);
            progressBar.setProgressDrawable(drawable);
        }
    }

    private float calculateProgress(int count, int total) {
        return ((float) count / total) * 100;
    }

    private void setProgressAnimate(int progress) {
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", progressBar.getProgress(), progress);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }
}
