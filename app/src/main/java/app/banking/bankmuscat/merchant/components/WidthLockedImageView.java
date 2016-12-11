/**
 * 
 */
package app.banking.bankmuscat.merchant.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author U36838
 * 
 */
public class WidthLockedImageView extends ImageView {

	/**
	 * @param context
	 */
	public WidthLockedImageView(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public WidthLockedImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public WidthLockedImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}
}
