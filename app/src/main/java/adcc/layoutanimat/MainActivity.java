package adcc.layoutanimat;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    FrameLayout expand_complaint_filter_activity;
    Button btn_filter;
    int m = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expand_complaint_filter_activity = (FrameLayout) findViewById(R.id.expand_complaint_filter_activity);
        btn_filter = (Button) findViewById(R.id.btn_filter);

    }


    public void expandClick(View v) {


        if (m % 2 == 0) {

            //LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) all_use_tv.getLayoutParams();
            //params.height = 2;
            //all_use_tv.setLayoutParams(params);

            expand(expand_complaint_filter_activity);

            Drawable d = getResources().getDrawable(R.drawable.ic_arrow_drop_up_black_24dp);
            btn_filter.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);


        } else {
            collapse(expand_complaint_filter_activity);
            Drawable d = getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
            btn_filter.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
        }
        m++;
    }


    public static void expand(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}
