package com.animalhusbandry.utils;

/**
 * Created by grewalshavneet on 6/2/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

import com.animalhusbandry.R;


/**
 * Created by dhawansudisha on 2/20/2017.
 */

public class FloatingRoundImage extends FloatingActionButton {
    public FloatingRoundImage(Context context) {
        super(context);
    }

    public FloatingRoundImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingRoundImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap sBmp;

        if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
            float smallest = Math.min(bmp.getWidth(), bmp.getHeight());
            float factor = smallest / radius;
            sBmp = Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth() / factor), (int) (bmp.getHeight() / factor), false);
        } else {
            sBmp = bmp;
        }

        Bitmap output = Bitmap.createBitmap(radius, radius,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, radius + 5, radius + 5);

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(radius / 2,
                radius / 2, radius / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sBmp, rect, rect, paint);

        return output;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = null;
        if (b != null) {
            bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
        } else {
            BitmapDrawable bitmapDrawable = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                bitmapDrawable = ((BitmapDrawable) getResources().getDrawable(R.drawable.ic_login_buttton, null));
            } else {
                bitmapDrawable = ((BitmapDrawable) getResources().getDrawable(R.drawable.googleplus));
            }
            if (bitmapDrawable != null) {
                bitmap = bitmapDrawable.getBitmap();
            }
        }

        int w = getWidth();
        Bitmap roundBitmap = getCroppedBitmap(bitmap, w);
        canvas.drawBitmap(roundBitmap, 0, 0, null);
    }

    public void setAnimation(int fade_in) {

    }
}
