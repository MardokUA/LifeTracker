package laktionov.lifetracker.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import laktionov.lifetracker.R;


public class ButtonView extends RelativeLayout {

    private ImageButton ib_delete;
    private ImageButton ib_add;


    public ButtonView(Context context) {
        super(context);
        init();
    }

    public ButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.card_buttons, this);
        ib_delete = (ImageButton) findViewById(R.id.ib_delete);
        ib_add = (ImageButton) findViewById(R.id.ib_add);

        ib_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(v, "alpha", 1f);
                v.setAlpha(0f);
                animator.setDuration(500);
                animator.start();
            }
        });
    }

}
