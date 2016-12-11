package app.banking.bankmuscat.merchant.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager.BadTokenException;

import app.banking.bankmuscat.R;


public class CustomeProgressBar {
	ProgressDialog pdialog;
	static CustomeProgressBar instance;
	private ProgressDialog progress;

	public static CustomeProgressBar getInstance() {
		if (null == instance) {
			instance = new CustomeProgressBar();
		}
		return instance;
	}

	public void dismissProgrsses() {
		if (null != pdialog && pdialog.isShowing()) {
			pdialog.dismiss();
			pdialog = null;
		} else {
			return;
		}
	}

	public ProgressDialog showHorizontalProgress(Activity mContect) {
		pdialog = new ProgressDialog(mContect);
		try {
			pdialog.setMessage("Progressing");
			pdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pdialog.setIndeterminate(true);
			pdialog.setCanceledOnTouchOutside(false);
			pdialog.show();
		} catch (BadTokenException e) {
			pdialog.setCancelable(false);
		}

		return pdialog;
	}



	public void hideLoader() {
		if (null != progress) {
			progress.hide();
			progress.dismiss();
		}
		progress = null;
	}

	public void showLoader(String message, Activity mContect) {
		hideLoader();
		progress = ProgressDialog.show(mContect, "", message, true, false);

	}

	public ProgressDialog createProgressDialog(Activity mContect) {
		try {
			if (null == pdialog) {
				pdialog = new ProgressDialog(mContect);
			}
			if (null != pdialog && !pdialog.isShowing()) {
				pdialog.show();
				pdialog.setContentView(R.layout.custom_progressbar);
				Window window = pdialog.getWindow();
				window.setLayout(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);
			}
		} catch (BadTokenException e) {
		}
		if (null != pdialog) {
			pdialog.setCancelable(false);
		}
		return pdialog;

	}

}
