package msk.android.academy.javatemplate;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import msk.android.academy.javatemplate.network.ApiUtils;
import msk.android.academy.javatemplate.network.CheckNetwork;
import ru.alexbykov.nopermission.PermissionHelper;

import static android.app.Activity.RESULT_OK;

public class SearchFragment extends Fragment {
    String mCurrentPhotoPath;
    private Uri imageUri;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    PermissionHelper permissionHelper = new PermissionHelper(this);
    private static final int TAKE_PICTURE = 1;

    @BindView(R.id.btn)
    Button mButton;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.mic)
    ImageButton mic;
    @BindView(R.id.camera)
    ImageButton camera;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment,container,false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
     //   super.onViewCreated(view, savedInstanceState);

                mic.setOnClickListener(view13 -> promptSpeechInput());

        camera.setOnClickListener(view1 -> {
            if(checkCameraPermission()){
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);
            }
            else Toast.makeText(getActivity(), "False", Toast.LENGTH_SHORT).show();
        });

        mButton.setOnClickListener(view12 -> {
            if(CheckNetwork.hasConnection()){
                System.out.println(description.getText().toString());
                ApiUtils.getApiService().sendSound(description.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(resModel ->

                                        mAdapter = new MovieAdapter(getActivity(),resModel)

                                , error -> {
                            if (error instanceof SocketTimeoutException) {
                            }
                        });
            }
            else {
                Toast.makeText(getActivity(), "Please Check Internet Connect", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Showing google speech input dialog
     */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    description.setText(result.get(0));
                }
                break;
            }
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getActivity().getContentResolver().notifyChange(selectedImage, null);
                    ContentResolver cr = getActivity().getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);

                        Toast.makeText(getContext(), selectedImage.toString(),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }

    public void takePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    protected boolean checkCameraPermission() {
        final boolean[] isTrue = {false};
        permissionHelper.check(Manifest.permission.CAMERA)
                .onSuccess(() -> {
                    isTrue[0] = true;
                })
                .onDenied(() -> {
                    isTrue[0] = false;
                })
                .onNeverAskAgain(() -> {
                    permissionHelper.startApplicationSettingsActivity();
                })
                .run();
        return isTrue[0];
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
