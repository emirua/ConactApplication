package com.emilio.contactapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.emilio.contactapplication.fragments.ContactsListFragment;

import static android.Manifest.permission.READ_CONTACTS;

public class MainActivity extends AppCompatActivity {


    private static final int REQUEST_READ_PERMIT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (havePermits())
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_container, new ContactsListFragment()).commit();
    }

    private boolean havePermits() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                ) {
            return true;
        }
        final String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS};
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)
                ) {
            Snackbar.make(getWindow().getDecorView(), R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(PERMISSIONS, REQUEST_READ_PERMIT);
                        }
                    });
        } else {
            requestPermissions(PERMISSIONS, REQUEST_READ_PERMIT);
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
