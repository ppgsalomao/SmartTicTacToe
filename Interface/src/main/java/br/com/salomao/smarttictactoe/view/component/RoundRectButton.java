package br.com.salomao.smarttictactoe.view.component;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.widget.Button;

import br.com.salomao.smarttictactoe.R;

public class RoundRectButton extends Button {

    private GradientDrawable backgroundDrawable;

    public RoundRectButton(Context context) {
        super(context);
        this.init();
    }

    public RoundRectButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public RoundRectButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    private void init() {
        int backgroundColor = Color.alpha(1);
        this.setRoundedCornerRadius(this.getContext().getResources().getDimension(
                R.dimen.round_rect_button_corner_radius));

        if(this.getBackground() instanceof ColorDrawable) {
            backgroundColor = ((ColorDrawable) this.getBackground()).getColor();
        } else if(this.getBackground() instanceof ShapeDrawable) {
            backgroundColor = ((ShapeDrawable) this.getBackground()).getPaint().getColor();
        }
        this.setBackgroundColor(backgroundColor);
    }

    public void setBorderColor(int color) {
        this.getBackgroundDrawable().setStroke((int) this.getContext().getResources().getDimension(
                R.dimen.round_rect_button_stroke_width), color);
        this.setBackground(this.backgroundDrawable);
    }

    public void setRoundedCornerRadius(float radius) {
        this.getBackgroundDrawable().setCornerRadius(radius);
    }

    @Override
    public void setBackgroundColor(int color) {
        this.getBackgroundDrawable().setColor(color);
        this.setBackground(this.backgroundDrawable);
    }

    private GradientDrawable getBackgroundDrawable() {
        if(this.backgroundDrawable == null) {
            this.backgroundDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.BOTTOM_TOP, new int[] { Color.alpha(1) });
        }

        return this.backgroundDrawable;
    }

}
