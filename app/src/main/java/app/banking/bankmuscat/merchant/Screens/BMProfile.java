package app.banking.bankmuscat.merchant.Screens;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.io.ByteArrayOutputStream;
import java.util.UUID;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.api.APIManager;
import app.banking.bankmuscat.merchant.base.ActivityBase;
import app.banking.bankmuscat.merchant.common.Wallet;
import app.banking.bankmuscat.merchant.interfaces.ILoader;
import app.banking.bankmuscat.merchant.logger.CLog;
import app.banking.bankmuscat.merchant.util.constant.ErrorAndPopupCodes;


//import com.google.i18n.phonenumbers.NumberParseException;
//import com.google.i18n.phonenumbers.PhoneNumberUtil;
//import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
//import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class BMProfile extends ActivityBase implements ILoader {

    private static final String TAG = "BMProfile";
    private final APIManager apiManager = APIManager.createInstance(null);
    private Wallet.Data appData = Wallet.Data.getInstance();

    BitmapDrawable _profilePic;

    boolean _isProfilePicUpdated = false;

    public ImageView img_profilepic, imgverify;
    public ImageButton btn_editprofile;
    TextView headingTextView;
    LinearLayout email, mobile, dob, pin, account, language;

    TextView txt_name, txt_email, txt_mobile, txt_dob, txt_verify;

    //public EditText mobedtanswer1,mobedtanswer2;
    //Button next, cancel;

    @Override
    public int GetScreenLayout() {
        return R.layout.bm_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.bm_profile);
        // btnBack = (ImageView) findViewById(R.id.header_back_button);

        btn_editprofile = (ImageButton) findViewById(R.id.btn_editprofilepic);
        img_profilepic = (ImageView) findViewById(R.id.img_profilepic);

        imgverify = (ImageView) findViewById(R.id.imgverify);
        txt_verify = (TextView) findViewById(R.id.txt_verify);

        headingTextView = (TextView) findViewById(R.id.title_header);
        headingTextView.setText("Profile");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        txt_name = (TextView) findViewById(R.id.txt_name);

        txt_mobile = (TextView) findViewById(R.id.txt_mobile);

        txt_email = (TextView) findViewById(R.id.txt_email);

        txt_dob = (TextView) findViewById(R.id.txt_dob);

        if (appData.getUserData() != null) {


            txt_name.setText(appData.getUserData().firstName);
            txt_mobile.setText("+968" + appData.getUserData().mobileNumber);
            txt_email.setText(appData.getUserData().emailId);
            txt_dob.setText(appData.getUserData().dateOfBirth);

            if (appData.getUserData().isEmailVerified.compareToIgnoreCase("Y") == 0) {
                txt_verify.setText(getResources().getString(R.string.verified));
                txt_verify.setTextColor( getResources().getColor(R.color.AppthemeTextColor));
                //imgverify.setImageDrawable(getResources().getDrawable(R.drawable.verified));
                imgverify.setVisibility(View.VISIBLE);
            } else {
                txt_verify.setText(getResources().getString(R.string.verify));

                txt_verify.setTextColor( getResources().getColor(R.color.lightblue));
                // imgverify.setImageDrawable(getResources().getDrawable(R.drawable.verify));
                imgverify.setVisibility(View.INVISIBLE);
            }

            if (appData.getUserData().image != null) {
                if (appData.getUserData().image.length() != 0) {
                    try {


                        byte[] decodedString = Base64.decode(appData.getUserData().image, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                        img_profilepic.setImageBitmap(getRoundedCroppedBitmap(decodedByte, 200));
                    } catch (Exception e) {

                    }

                }
            }

        } else {
            txt_name.setText(appData.getUserName());
            txt_mobile.setText("+968" + appData.getMsisdn());
            txt_email.setText(appData.getEmail());
        }


        email = (LinearLayout) findViewById(R.id.btn_email);
        mobile = (LinearLayout) findViewById(R.id.btn_mobile);
        dob = (LinearLayout) findViewById(R.id.btn_dob);
        pin = (LinearLayout) findViewById(R.id.btn_pin);
        account = (LinearLayout) findViewById(R.id.btn_account);
        language = (LinearLayout) findViewById(R.id.btn_language);

        imgverify.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                VerifyEmail();
            }

        });

        txt_verify.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                VerifyEmail();
            }

        });


        txt_name.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                EditName();
            }

        });

        email.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                EditEmail();
            }

        });

        mobile.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                EditMobile();
            }

        });

        dob.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //EditDOB();
            }

        });

        pin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                EditPin();

            }

        });

        account.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                EditAccount();
            }

        });

        language.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                ChangeLanguage();
            }

        });


        img_profilepic.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                selectImage();

            }

        });

        btn_editprofile.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                selectImage();

            }

        });

    }

    void CloseWin() {
/*hideSoftKeyboard();
                NavUtils.navigateUpTo(BMEnterEmail.this, NavUtils
						.getParentActivityIntent(BMEnterEmail.this));*/
        this.finish();
    }


    void nextAction() {


		/* if (mobedtanswer2.getText().toString().trim().isEmpty()) {

			showAlert("Please enter answer");
		}


		else {


				if (!verifyInternetStatus()) {
					return;
				} else {

					*//*TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                    String imei = telephonyManager.getDeviceId();
					BMRegisterUser.this.validateMSISDN(mobileNumber,
							null != imei ? imei : mobileNumber);*//*

					Intent i = new Intent(getApplicationContext(), HomeScreen.class);
					startActivity(i);
				}
			}*/

    }

    // JSONObject profile;
    // ProfileData profileData;



    private static final int REQUEST_CODE_CAMERA = 98;
    private static final int REQUEST_CODE_PHOTOGALLERY = 99;
    private Uri fileUri = null;

    private void VerifyEmail() {
        if (appData.getUserData().isEmailVerified.compareToIgnoreCase("N") == 0) {
            if (!verifyInternetStatus()) {
                return;
            }
            showLoader();
        /*DbManager dbManager = DbManager.createAuto(BMLogin.this);
        dbManager.setImei(imei);
		dbManager.setMsisdn(msisdn);*/

            Wallet.Data.syncRead(getApplicationContext());

            try {


                final UUID resultId = apiManager.BMVerifyEmail(appData.getUserData().uniqueId);

                final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                    @Override
                    public void handleMessage(APIManager.SOAPOperationData data) {
                        try {

                            if (data.getId() == resultId) {
                                apiManager.removeListener(this);
                                hideLoader();

                                if (!data.hasError()) {
                                    CLog.i(TAG, "success");
                                    System.out.println("Service Id............." + data.getServiceId());
                                    appData.setServiceId(data.getServiceId());

                                    hideLoader();
                                    CLog.w(TAG, "out error : " + data.getError());
                                    showAlert(getResources().getString(R.string.verifyemailsuccess));

                                } else {
                                    hideLoader();
                                    CLog.w(TAG, "out error : " + data.getError());
                                    showAlert(data.getResponseData().txnMessage);
                                }
                            } else {
                                hideLoader();
                                CLog.w(TAG, "out error : " + data.getError());
                                showAlert(data.getResponseData().txnMessage);

                            }
                        } catch (Exception e) {

                            new AlertDialog.Builder(BMProfile.this)
                                    .setTitle("Error")
                                    .setMessage(ErrorAndPopupCodes.No_Response.getTag())
                                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            //ErrorAction();
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();
                        }
                    }
                };
                apiManager.addListener(l);
            } catch (Exception e) {
                hideLoader();
            }
        }
    }

    private void EditName() {


    }

    private void EditDOB() {


    }

    private void EditEmail() {


    }

    private void EditMobile() {

    }

    private void EditPin() {
        Intent i = new Intent(getApplicationContext(), BMChangePin.class);
        startActivity(i);
    }

    private void EditAccount() {

    }

    private void ChangeLanguage() {

        Intent i = new Intent(getApplicationContext(), BMLanguage.class);
        i.putExtra("Mode", CHANGE_LANGUAGE);
        startActivity(i);
    }


    /*private void selectImage() {
        try {
            final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add Photo!");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Choose from Gallery")) {
                        Intent intent = new Intent();
                        intent.setType("image*//*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult(intent, REQUEST_CODE_PHOTOGALLERY);
                    } else if (options[item].equals("Take Photo")) {
                    *//*Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MyImages");
                    imagesFolder.mkdirs(); // <----
                    File image = new File(imagesFolder, "image_001.jpg");
                    _cameraFileUri = Uri.fromFile(image);
                    takePicture.putExtra(MediaStore.EXTRA_OUTPUT, _cameraFileUri);

                    startActivityForResult(takePicture, REQUEST_CODE_CAMERA);*//*
                   *//* Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
                    startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
*//*
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);
                        }
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        } catch (Exception ex) {
        }
    }*/


    private void selectImage() {
        try {

            Intent i = new Intent(getApplicationContext(),
                    BMLoadImage.class);

            // if(appData.getUserData().image.length() == 0)
            if (appData.getUserData().image == null)
                i.putExtra("Mode", 10);
            else if (appData.getUserData().image.length() == 0)
                i.putExtra("Mode", 10);
            else
                i.putExtra("Mode", 0);
            startActivityForResult(i, 777);


        } catch (Exception ex) {
        }
    }


  /*  public void LoadImage() {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 1);
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (resultCode == RESULT_OK) {

                if (requestCode == 777) {
                   /* showAlert("777");*/
                    int selection = data.getIntExtra("Selection", 1);
                    switch (selection) {
                        case 1:
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            startActivityForResult(intent, REQUEST_CODE_PHOTOGALLERY);
                            break;
                        case 2:
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                          /*  if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                               //takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);



                                *//*takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
                                takePictureIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
                                takePictureIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);*//*

                                startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);

                            }*/

                            startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);
                            break;
                        case 3: {

                            showOptionDialog(getResources().getString(R.string.deletepictureconfirm), true);

                            /*new AlertDialog.Builder(BMProfile.this)
                                    .setTitle("")
                                    .setMessage(getResources().getString(R.string.deletepictureconfirm))
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            RemoveProfilePic();
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();*/

                            // RemoveProfilePic();
                            break;
                        }

                        default:
                            break;
                    }

                }


                else if(requestCode == 1234){
                    Bundle extras = data.getExtras();
                    Bitmap profileImage = extras.getParcelable("data");
                    ChangeProfilePic(profileImage);
                }

                else {
                    /*showAlert("other");*/
                    Bitmap bitmap = null;
                    _isProfilePicUpdated = true;
                    if (requestCode == REQUEST_CODE_PHOTOGALLERY) {
                        Uri selectedImageUri = data.getData();
                        try {

                          //  CropImage.activity(selectedImageUri).setFixAspectRatio(true).setGuidelines(CropImageView.Guidelines.ON).start(this);

                                PerformImageCrop(selectedImageUri);


                        } catch (Exception ex) {
                        }
                    } else if (requestCode == REQUEST_CODE_CAMERA) {

                        bitmap = (Bitmap) data.getExtras().get("data");
                        Uri selectedImageUri = data.getData();
                        try {

                          //  CropImage.activity(selectedImageUri).setFixAspectRatio(true).setGuidelines(CropImageView.Guidelines.ON).start(this);

                            PerformImageCrop(selectedImageUri);

                        } catch (Exception ex) {
                            ChangeProfilePic(bitmap);
                        }
                    }


                }
            }
        } catch (Exception ex) {
        }

    }


    private void PerformImageCrop(Uri imageUri){
        try {


            Intent cropIntent = new Intent("com.android.camera.action.CROP");

            cropIntent.setDataAndType(imageUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 500);
            cropIntent.putExtra("outputY", 500);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, 1234);

        }
        catch(ActivityNotFoundException anfe){

        }
    }


    public void OnOptionDialogClose(String message) {

        super.OnOptionDialogClose(message);
        if (message.compareToIgnoreCase(getResources().getString(R.string.deletepictureconfirm)) == 0) {

            RemoveProfilePic();
        }

    }

    private void RemoveProfilePic() {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();
        /*DbManager dbManager = DbManager.createAuto(BMLogin.this);
        dbManager.setImei(imei);
		dbManager.setMsisdn(msisdn);*/

        Wallet.Data.syncRead(getApplicationContext());

        try {

            final UUID resultId = apiManager.BMDeleteProfilePic(appData.getUniqueId());

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            apiManager.removeListener(this);
                            hideLoader();

                            if (!data.hasError()) {
                                // CLog.i(TAG, "success");
                                System.out.println("Service Id............." + data.getServiceId());
                                appData.setServiceId(data.getServiceId());


                                appData.getUserData().image = "";
                                img_profilepic.setImageDrawable(getResources().getDrawable(R.drawable.profile));//null);


                            } else {
                            /*if ("225".equals(data.getError())) {

								DbManager dbManager = DbManager.createAuto(BMLogin.this);

								String uniqueId = (String) data.getResponse().getProperty("uniqueId").toString().trim();
								appData.setServiceId(data.getServiceId());
								appData.setValidateMsisdn(true);
								System.out.println("SERVICE ID......." + data.getServiceId());
								appData.setUniqueId(uniqueId);
								CLog.i(TAG, "UniqueID  success:" + uniqueId);
								dbManager.setUniqueId(uniqueId);
								Intent i = new Intent(getApplicationContext(),
										RegistrationForgetPin.class);
								i.putExtra("Registration", "Registration");
								startActivity(i);
								hideLoader();
							} else if ("260".equals(data.getError())) {

								Intent i = new Intent(getApplicationContext(),
										RegistrationPin.class);
								startActivity(i);
								hideLoader();
							}

							else {
								hideLoader();
								CLog.w(TAG, "in error : " + data.getError());
								showAlert(data.getResponseData().txnMessage);
							}*/
                                hideLoader();
                                CLog.w(TAG, "in error : " + data.getError());
                                showAlert(data.getResponseData().txnMessage);

                               /* Intent i = new Intent(getApplicationContext(), BMChangesMade.class);
                                 if(mode ==CHANGE_EMAIL) {
                                     i = new Intent(getApplicationContext(), BMChangesMade.class);
                                     appData.setEmail(newEmail);
                                 }
                                else if(mode ==REGISTER_USER)
                                 i = new Intent(getApplicationContext(), BMSetPin.class);
                                else if(mode ==FORGOT_PIN)
                                    i = new Intent(getApplicationContext(), BMChangePin.class);
                               CloseWin();
                                startActivity(i);*/
                            }
                        } else {
                            hideLoader();
                            CLog.w(TAG, "out error : " + data.getError());
                            showAlert(data.getResponseData().txnMessage);
                          /*  Intent i = new Intent(getApplicationContext(), BMResetPin.class);
                            startActivity(i);*/
                            //hideLoader();
                        }
                    } catch (Exception e) {

                        new AlertDialog.Builder(BMProfile.this)
                                .setTitle("Error")
                                .setMessage(ErrorAndPopupCodes.No_Response.getTag())
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        //ErrorAction();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();

                    }
                }
            };
            apiManager.addListener(l);
        } catch (Exception e) {
            hideLoader();
        }
    }


    private void ChangeProfilePic(final Bitmap bitmap) {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();
        /*DbManager dbManager = DbManager.createAuto(BMLogin.this);
        dbManager.setImei(imei);
		dbManager.setMsisdn(msisdn);*/

        Wallet.Data.syncRead(getApplicationContext());

        try {


            ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, imageStream);
            byte[] bitmapData = imageStream.toByteArray();
            final String imageEncoded = Base64.encodeToString(bitmapData, Base64.NO_WRAP);


         /*   byte[] decodedString = Base64.decode(imageEncoded, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            btn_editprofile.setImageBitmap(decodedByte);*/


            APIManager.ResponseData data = appData.getUserData();

            final UUID resultId = apiManager.BMChangeProfilePic(data.uniqueId, imageEncoded);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            apiManager.removeListener(this);
                            hideLoader();

                            if (!data.hasError()) {
                                CLog.i(TAG, "success");
                                System.out.println("Service Id............." + data.getServiceId());
                                appData.setServiceId(data.getServiceId());


                                appData.getUserData().image = imageEncoded;

                                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), GetRoundedImage(bitmap));

                                if (bitmapDrawable != null)
                                    _profilePic = bitmapDrawable;
                                img_profilepic.setImageDrawable(bitmapDrawable);


                            } else {
							/*if ("225".equals(data.getError())) {

								DbManager dbManager = DbManager.createAuto(BMLogin.this);

								String uniqueId = (String) data.getResponse().getProperty("uniqueId").toString().trim();
								appData.setServiceId(data.getServiceId());
								appData.setValidateMsisdn(true);
								System.out.println("SERVICE ID......." + data.getServiceId());
								appData.setUniqueId(uniqueId);
								CLog.i(TAG, "UniqueID  success:" + uniqueId);
								dbManager.setUniqueId(uniqueId);
								Intent i = new Intent(getApplicationContext(),
										RegistrationForgetPin.class);
								i.putExtra("Registration", "Registration");
								startActivity(i);
								hideLoader();
							} else if ("260".equals(data.getError())) {

								Intent i = new Intent(getApplicationContext(),
										RegistrationPin.class);
								startActivity(i);
								hideLoader();
							}

							else {
								hideLoader();
								CLog.w(TAG, "in error : " + data.getError());
								showAlert(data.getResponseData().txnMessage);
							}*/
                                hideLoader();
                                CLog.w(TAG, "in error : " + data.getError());
                                showAlert(data.getResponseData().txnMessage);

                               /* Intent i = new Intent(getApplicationContext(), BMChangesMade.class);
                                 if(mode ==CHANGE_EMAIL) {
                                     i = new Intent(getApplicationContext(), BMChangesMade.class);
                                     appData.setEmail(newEmail);
                                 }
                                else if(mode ==REGISTER_USER)
                                 i = new Intent(getApplicationContext(), BMSetPin.class);
                                else if(mode ==FORGOT_PIN)
                                    i = new Intent(getApplicationContext(), BMChangePin.class);
                               CloseWin();
                                startActivity(i);*/
                            }
                        } else {
                            hideLoader();
                            CLog.w(TAG, "out error : " + data.getError());
                            showAlert(data.getResponseData().txnMessage);
                          /*  Intent i = new Intent(getApplicationContext(), BMResetPin.class);
                            startActivity(i);*/
                            //hideLoader();
                        }
                    } catch (Exception e) {

                        new AlertDialog.Builder(BMProfile.this)
                                .setTitle("Error")
                                .setMessage(ErrorAndPopupCodes.No_Response.getTag())
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        //ErrorAction();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();

                    }
                }
            };
            apiManager.addListener(l);
        } catch (Exception e) {
            hideLoader();
        }
    }

    private Bitmap GetRoundedImage(Bitmap orginalImage) {
        try {
            float factorH = 200 / (float) orginalImage.getHeight();
            float factorW = 200 / (float) orginalImage.getWidth();
            float factorToUse = (factorH > factorW) ? factorW : factorH;
            Bitmap bm = Bitmap.createScaledBitmap(orginalImage,
                    (int) (orginalImage.getWidth() * factorToUse),
                    (int) (orginalImage.getHeight() * factorToUse),
                    true);

            return getRoundedCroppedBitmap(bm, 200);
        } catch (Exception ex) {
        }
        return null;
    }

    public static Bitmap getRoundedCroppedBitmap(Bitmap bitmap, int radius) {
        try {

			/*if (bitmap.getWidth() > 500 || bitmap.getHeight() > 500) {
                bitmap = ResizeBitmap(bitmap, 500, 500);
			}*/
            Bitmap finalBitmap;
            if (bitmap.getWidth() != radius || bitmap.getHeight() != radius)
                finalBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius,
                        true);
            else
                finalBitmap = bitmap;
            Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(),
                    finalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, finalBitmap.getWidth(),
                    finalBitmap.getHeight());

            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            paint.setDither(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.parseColor("#BAB399"));
            canvas.drawCircle(finalBitmap.getWidth() / 2 + 0.7f,
                    finalBitmap.getHeight() / 2 + 0.7f,
                    finalBitmap.getWidth() / 2 + 0.1f, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(finalBitmap, rect, rect, paint);
            return output;
        } catch (Exception ex) {
        }
        return null;
    }


    /*public class ProfileData {

        @SerializedName("mobileNumber")
        public String mobileNumber;

        @SerializedName("emailId")
        public String emailId;

        @SerializedName("pin")
        public String pin;

        @SerializedName("image")
        public String image;


    }

    Gson _gson;

    public Gson GetGson() {
        if (null == _gson) {
            GsonBuilder builder = new GsonBuilder();
            _gson = new Gson();
            //	_gson = new GsonBuilder().registerTypeAdapter(Date.class, new Object()).create();
        }
        return _gson;
    }*/


    @Override
    protected void onResume() {
        super.onResume();

        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_mobile = (TextView) findViewById(R.id.txt_mobile);
        txt_email = (TextView) findViewById(R.id.txt_email);
        txt_dob = (TextView) findViewById(R.id.txt_dob);

        if (appData.getUserData() != null) {

            txt_name.setText(appData.getUserData().firstName);
            txt_mobile.setText("+968" + appData.getUserData().mobileNumber);
            txt_email.setText(appData.getUserData().emailId);
            txt_dob.setText(appData.getUserData().dateOfBirth);
        } else {
            txt_name.setText(appData.getUserName());
            txt_mobile.setText("+968" + appData.getMsisdn());
            txt_email.setText(appData.getEmail());
        }

    }


}
