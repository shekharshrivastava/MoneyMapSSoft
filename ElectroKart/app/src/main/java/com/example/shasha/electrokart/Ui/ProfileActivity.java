package com.example.shasha.electrokart.Ui;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shasha.electrokart.Model.LoginModel;
import com.example.shasha.electrokart.R;
import com.soundcloud.android.crop.Crop;
import com.soundcloud.android.crop.CropImageActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView profilePicImage;
    private ImageView editButton;

    public static final long KB_5 = 5 * 1024;
    private Uri mImageCaptureUri;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;
    private static final int PIC_CROP = 2;
    public static final int REQUEST_CROP = 6709;
    public static final int USER_THUMB_DIMENSION = 300;
    private Bitmap photo;
    private File file;
    private String thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.BLACK);
        profilePicImage = (CircleImageView) findViewById(R.id.profilePicImage);
        editButton = (ImageView) findViewById(R.id.editButton);
        editButton.setOnClickListener(this);
        profilePicImage.setImageResource(R.drawable.default_user);
        TextView userName= (TextView)findViewById(R.id.textUserName);
        userName.setText(LoginModel.getInstance().getUserName());
        TextView userEmail= (TextView)findViewById(R.id.textEmailId);
        userEmail.setText(LoginModel.getInstance().getEmailId());
        TextView textMobileNumber= (TextView)findViewById(R.id.textMobileNumber);
        textMobileNumber.setText(LoginModel.getInstance().getUserNumber());
        getUserThumbnail();
    }

    private void getUserThumbnail() {
        SharedPreferences userProfile = this.getSharedPreferences("AppUserProfilePic", MODE_PRIVATE);
        String userThumb = userProfile.getString("userProfilePicture",null);
        if(userThumb !=null) {
            Bitmap userBitmap = setThumbnail(userThumb);
            profilePicImage.setImageBitmap(userBitmap);
        }else{
            profilePicImage.setImageResource(R.drawable.default_user);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);

        return true;    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_save) {
            if (thumbnail != null) {
                SharedPreferences userProfileSharedPref = this.getSharedPreferences("AppUserProfilePic", MODE_PRIVATE);
                SharedPreferences.Editor editor = userProfileSharedPref.edit();
                editor.putString("userProfilePicture", thumbnail);
                editor.commit();
                Toast.makeText(this,"Profile pic updated",Toast.LENGTH_SHORT).show();
            }


        }
        if(id==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editButton:
                selectImage();
                break;
        }

    }


    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    String mfilepath = (getFilesDir() + "/sample/" + "ProfilePictures");
            /*    mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        "qh_seqrite_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));*/
                    File dir = new File(mfilepath);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    mImageCaptureUri = Uri.fromFile(new File(mfilepath, "xyz" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
                    if (mImageCaptureUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                    }
                    try {
                        intent.putExtra("return-data", true);
                        startActivityForResult(intent, PICK_FROM_CAMERA);

                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }

                } else if (options[item].equals("Choose from Gallery")) {
                    try {
                        Intent intent1 = new Intent();
                        intent1.setType("image/*");
                        intent1.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent1, PICK_FROM_FILE);
                    } catch (ActivityNotFoundException var3) {
                        //   showImagePickerError(activity);
                    }
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;


        switch (requestCode) {
            case PICK_FROM_CAMERA:
                if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                    beginCrop(mImageCaptureUri);
                } else {
                    performCrop();
                }
                break;

            case PICK_FROM_FILE:
                mImageCaptureUri = data.getData();
                if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                    beginCrop(mImageCaptureUri);
                } else {
                    performCrop();
                }
                break;

            case REQUEST_CROP:
                try {
                    handleCrop(resultCode, data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case CROP_FROM_CAMERA:

                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        photo = extras.getParcelable("data");
                        try {
                            file = savebitmap(photo);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (file.getPath() != null) {
                            try {
                                byte[] compressedThumb = scaleSendableImage(file.getPath(), USER_THUMB_DIMENSION, USER_THUMB_DIMENSION, KB_5);
                                thumbnail = Base64.encodeToString(compressedThumb, Base64.DEFAULT);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //  mChatGroupDetails.setThumbnail(thumbnail);
                        }
                        profilePicImage.setImageBitmap(photo);
                    }
                }
                break;
        }
    }


    private File savebitmap(Bitmap photo) throws IOException {

        File mFolder = new File(getFilesDir() + "/sample");
        File imgFile = new File(mFolder.getAbsolutePath() + System.currentTimeMillis() + ".jpg");
        if (!mFolder.exists()) {
            mFolder.mkdir();
        }
        if (!imgFile.exists()) {
            imgFile.createNewFile();
        }

        try {
            FileOutputStream fos = new FileOutputStream(imgFile);
            photo.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgFile;

    }

    private void performCrop() {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(mImageCaptureUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, PIC_CROP);
        } catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void handleCrop(int resultCode, Intent result) throws IOException {
        Uri uri = Crop.getOutput(result);
//        imageView.setImageURI(Crop.getOutput(result));
//        Bundle extras = result.getExtras();
//        if (extras != null) {
        photo = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        file = savebitmap(photo);


        if ((file.getPath() != null)) {
            byte[] compressedThumb = scaleSendableImage(file.getPath(), USER_THUMB_DIMENSION, USER_THUMB_DIMENSION, KB_5);

            thumbnail = Base64.encodeToString(compressedThumb, Base64.DEFAULT);
            //  mChatGroupDetails.setThumbnail(thumbnail);
        }

        profilePicImage.setImageBitmap(photo);
    }

    public static byte[] scaleSendableImage(String filePath, int maxWidth, int maxHeight, long maxSize) {

        File bitmapFile = new File(filePath);
        //Decode image size
        Bitmap myBitmap = decodeSampledBitmapFromFile(filePath, maxWidth, maxHeight);
        myBitmap = getRotatedBitmap(filePath, myBitmap);
        if (myBitmap == null) {
            return null;
        }
        int width = myBitmap.getWidth();
        int height = myBitmap.getHeight();

        //scale only if any dimension is greater than required dimension
        if (width > maxWidth || height > maxHeight) {
            if (width > height) {
                // landscape
                float ratio = (float) width / maxWidth;
                width = maxWidth;
                height = (int) (height / ratio);
            } else if (height > width) {
                // portrait
                float ratio = (float) height / maxHeight;
                height = maxHeight;
                width = (int) (width / ratio);
            } else {
                // square
                height = maxHeight;
                width = maxWidth;
            }
            myBitmap = Bitmap.createScaledBitmap(myBitmap, width, height, true);
        }
        byte[] bitmapdata;
        int compress = 100;
        do {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.JPEG, compress, bos);
            bitmapdata = bos.toByteArray();
            compress--;
        } while (bitmapdata.length > maxSize);
        return bitmapdata;
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        //options.InPreferredConfig = Bitmap.Config.Rgb565;
        //options.InDither = true;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    || (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private static Bitmap getRotatedBitmap(String localFilePath, Bitmap scaledBitmap) {
        int rotate = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(localFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface
                .ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
        }

        /****** Image rotation ****/
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);

        Bitmap rotateBitmap = null;
        try {
            rotateBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(),
                    scaledBitmap.getHeight(), matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotateBitmap;
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        //  Crop.of(source, destination).asSquare().start(getActivity());

        //    Crop.of(source, destination);
        Intent cropIntent = new Intent(this, CropImageActivity.class);
        cropIntent.setData(source);
        cropIntent.putExtra("output", destination);
        cropIntent.putExtra("aspect_x", 1);
        cropIntent.putExtra("aspect_y", 1);
        startActivityForResult(cropIntent, 6709);
    }

    public Bitmap setThumbnail(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

}

