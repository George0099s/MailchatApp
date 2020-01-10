package com.example.mailchat.Activities;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mailchat.Activities.ChoosePrivateMailchatID;
import com.example.mailchat.Models.User;
import com.example.mailchat.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;

public class AddPrivateUserPhoto extends AppCompatActivity {
    private static final String TAG = "AddPrivateUserPhoto";
    FirebaseAuth mAuth;
    ImageView userPhoto;
    private Uri imageUri;
    private static final int IMAGE_REQUEAST = 1;
    String userID;
    StorageTask<UploadTask.TaskSnapshot> uploadTask;
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;

    private FirebaseUser fUser;
    StorageReference storageReference;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_private_user_photo);
        User user = getIntent().getParcelableExtra("user");
        Log.d(TAG, "onCreate: cit" + user.getCity());

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        findViewById(R.id.chose_photo).setOnClickListener(v -> openImage());
        findViewById(R.id.finish_private_registr).setOnClickListener(v -> {
            uploadImage();
            Intent intent = new Intent(AddPrivateUserPhoto.this, ChoosePrivateMailchatID.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });
    }


    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEAST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(AddPrivateUserPhoto.this);
        pd.setMessage("Uploading");
        pd.show();

        if (imageUri != null) {
            final StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(task -> {
                if (task.isSuccessful()) {
//                        throw task.getException();
                }
                return fileRef.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String mUri = downloadUri.toString();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(fUser.getUid());
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("imageURL", mUri);
                    User user = getIntent().getParcelableExtra("user");
                    Log.d(TAG, "uploadImage: citititi  "+ user.getCity());
                    user.setImageURL(mUri);
                    reference.updateChildren(map);

                    pd.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            });

        } else {
            Toast.makeText(getApplicationContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEAST && data != null
                && data.getData() != null) {
            imageUri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            userPhoto = findViewById(R.id.addUserPhoto);
                userPhoto.setMinimumWidth(180);
                int dimensionInPixel = 180;
                int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPixel, getResources().getDisplayMetrics());
                userPhoto.getLayoutParams().height = dimensionInDp;
                userPhoto.getLayoutParams().width = dimensionInDp;
                userPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                userPhoto.setImageBitmap(bitmap);
            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(getApplicationContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onActivityResult: task " + uploadTask);
            } else {
                uploadImage();
            }
        }
    }

//    private void chooseImage() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
//                && data != null && data.getData() != null )
//        {
//            filePath = data.getData();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                userPhoto = findViewById(R.id.addUserPhoto);
//                userPhoto.setMinimumWidth(180);
//                int dimensionInPixel = 180;
//                int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPixel, getResources().getDisplayMetrics());
//                userPhoto.getLayoutParams().height = dimensionInDp;
//                userPhoto.getLayoutParams().width = dimensionInDp;
//                userPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                userPhoto.setImageBitmap(bitmap);
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
//        }
//    }

//    private void uploadImage() {
//        FirebaseUser user = mAuth.getCurrentUser();
//        userID = user.getUid();
//
//        if(filePath != null)
//        {
//            final ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setTitle("Uploading...");
//            progressDialog.show();
//
//            StorageReference ref = storageReference.child("images/"+ userID );
//            ref.putFile(filePath)
//                    .addOnSuccessListener(taskSnapshot -> {
//
//                        progressDialog.dismiss();
//                        Toast.makeText(AddPrivateUserPhoto.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                    })
//
//                    .addOnFailureListener(e -> {
//
//                        progressDialog.dismiss();
//                        Toast.makeText(AddPrivateUserPhoto.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
//                    })
//
//                    .addOnProgressListener(taskSnapshot -> {
//
//                        double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
//                                .getTotalByteCount());
//                        progressDialog.setMessage("Uploaded "+(int)progress+"%");
//                    });
//        }
//    }


    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(event);
    }
}


