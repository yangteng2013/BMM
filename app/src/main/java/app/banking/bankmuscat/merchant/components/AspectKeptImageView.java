package app.banking.bankmuscat.merchant.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AspectKeptImageView extends ImageView {
	/**
	 * @param context
	 */
	public AspectKeptImageView(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public AspectKeptImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public AspectKeptImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		try {
			Drawable drawable = getDrawable();

			if (drawable == null) {
				setMeasuredDimension(0, 0);
			} else {
				float imageSideRatio = (float) drawable.getIntrinsicWidth() / (float) drawable.getIntrinsicHeight();
				float viewSideRatio = (float) MeasureSpec.getSize(widthMeasureSpec) / (float) MeasureSpec.getSize(heightMeasureSpec);
				if (imageSideRatio >= viewSideRatio) {
					int width = MeasureSpec.getSize(widthMeasureSpec);
					int height = (int) (width / imageSideRatio);
					setMeasuredDimension(width, height);
				} else {
					int height = MeasureSpec.getSize(heightMeasureSpec);
					int width = (int) (height * imageSideRatio);
					setMeasuredDimension(width, height);
				}
			}
		} catch (Exception e) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
}
