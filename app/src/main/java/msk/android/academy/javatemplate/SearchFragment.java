package msk.android.academy.javatemplate;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.DialogInterface;
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
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import msk.android.academy.javatemplate.network.ApiUtils;
import msk.android.academy.javatemplate.network.CheckNetwork;
import msk.android.academy.javatemplate.network.response.FilmModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import ru.alexbykov.nopermission.PermissionHelper;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;
import static android.app.Activity.RESULT_OK;
import static android.support.constraint.Constraints.TAG;

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
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //   super.onViewCreated(view, savedInstanceState);

        mic.setOnClickListener(view13 -> promptSpeechInput());

        camera.setOnClickListener(view1 -> {
            if (checkPermission(CAMERA)) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, TAKE_PICTURE);
                }
            }
        });

        mButton.setOnClickListener(view12 -> {
            if (CheckNetwork.hasConnection()) {
                ApiUtils.getApiService().sendSound(description.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(resModel -> {

                                }
                                , error -> {
                                    if (error instanceof SocketTimeoutException) {
                                    }
                                });
            } else {
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
                    try {
                        File file = createImageFile();
                        Bitmap bmp = (Bitmap) data.getExtras().get("data");
                        try (FileOutputStream s = new FileOutputStream(file)) {
                            bmp.compress(Bitmap.CompressFormat.PNG, 100, s);
                            s.flush();
                        } catch (Exception ignored) {

                        }
                        MultipartBody.Part filePart =
                                MultipartBody.Part.
                                        createFormData(
                                                "photo",
                                                file.getName(),
                                                RequestBody.create(
                                                        MediaType.parse("multipart/form-data"),
                                                        file
                                                )
                                        );
                        ApiUtils.getApiService().uploadPhoto(filePart)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(filmModel -> description.setText(filmModel.getTitle()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
    protected boolean checkPermission(String permission) {
        final boolean[] isTrue = {false};
        switch (permission) {
            case CAMERA:
                permissionHelper.check(Manifest.permission.CAMERA)
                        .onSuccess(() -> {
                            isTrue[0] = true;
                        })
                        .onDenied(() -> {
                            isTrue[0] = false;
                        })
                        .onNeverAskAgain(() -> {
                            new AlertDialog.Builder(getActivity())
                                    .setMessage("Без предоставления прав, приложение будет работать не корректно.")
                                    .setPositiveButton("OK", (dialogInterface, i) -> askAgain())
                                    .create()
                                    .show();

                        })
                        .run();
                break;
            case WRITE_EXTERNAL_STORAGE:
                permissionHelper.check(WRITE_EXTERNAL_STORAGE)
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
                break;
        }

        return isTrue[0];
    }

    protected void askAgain(){
        permissionHelper.startApplicationSettingsActivity();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(getActivity().getFilesDir(), imageFileName);
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


}
