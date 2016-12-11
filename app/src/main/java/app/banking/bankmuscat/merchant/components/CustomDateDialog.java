package app.banking.bankmuscat.merchant.components;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.lang.reflect.Field;

public class CustomDateDialog extends DatePickerDialog {

	private String title;
	public CustomDateDialog(Context context,
			OnDateSetListener callBack, int year, int monthOfYear,
			int dayOfMonth) {
		super(context, callBack, year, monthOfYear, dayOfMonth);
		
		try {

			Field[] datePickerDialogFields = this.getClass().getDeclaredFields();
			for (Field datePickerDialogField : datePickerDialogFields) {
				Toast.makeText(context, datePickerDialogField.getName() , Toast.LENGTH_LONG).show();
				if (datePickerDialogField.getName().equals("mDatePicker")) {
					datePickerDialogField.setAccessible(true);
					DatePicker datePicker = (DatePicker) datePickerDialogField
							.get(this);
					Field datePickerFields[] = datePickerDialogField.getType()
							.getDeclaredFields();
					for (Field datePickerField : datePickerFields) {
						
						if ("mDayPicker".equals(datePickerField.getName())
								|| "mDaySpinner".equals(datePickerField
										.getName())) {
							datePickerField.setAccessible(true);
							Object dayPicker = new Object();
							dayPicker = datePickerField.get(datePicker);
							((View) dayPicker).setVisibility(View.GONE);
						}
						
						if("mMonthSpinner".equals(datePickerField
								.getName())) {
							datePickerField.setAccessible(true);
							Object dayPicker = new Object();
							dayPicker = datePickerField.get(datePicker);
							((View) dayPicker).setVisibility(View.GONE);
						}
					}
				}
								

			}
		} catch (Exception ex) {
			Toast.makeText(context, ex.getMessage() , Toast.LENGTH_LONG).show();
		}
	}
	
	public void setPermenantTitle(String myTitle) {
		this.title = myTitle;
		setTitle(title);
	}
	
	@Override
	public void onDateChanged(DatePicker view, int year, int month, int day) {
		super.onDateChanged(view, year, month, day);
		setTitle("Change Date");
	}
	

}

	
