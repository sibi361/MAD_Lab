package com.example.lab2_q2;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.io.UnsupportedEncodingException;

class AES256 {
    private static final int KEY_LENGTH = 256;
    private static final int ITERATION_COUNT = 65536;
    private static final String SALT = "gytuihjgyiueakjdwq";

    public static String encrypt(String strToEncrypt, String secretKey) {

        try {
            SecureRandom secureRandom = new SecureRandom();
            byte[] iv = new byte[16];
            secureRandom.nextBytes(iv);
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), SALT.getBytes(), ITERATION_COUNT, KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivspec);

            byte[] cipherText = cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8));
            byte[] encryptedData = new byte[iv.length + cipherText.length];
            System.arraycopy(iv, 0, encryptedData, 0, iv.length);
            System.arraycopy(cipherText, 0, encryptedData, iv.length, cipherText.length);

            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            // Handle the exception properly
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String strToDecrypt, String secretKey) {
        try {

            byte[] encryptedData = Base64.getDecoder().decode(strToDecrypt);
            byte[] iv = new byte[16];
            System.arraycopy(encryptedData, 0, iv, 0, iv.length);
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), SALT.getBytes(), ITERATION_COUNT, KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivspec);

            byte[] cipherText = new byte[encryptedData.length - 16];
            System.arraycopy(encryptedData, 16, cipherText, 0, cipherText.length);

            byte[] decryptedText = cipher.doFinal(cipherText);
            return new String(decryptedText, StandardCharsets.UTF_8);
        } catch (Exception e) {
            // Handle the exception properly
            e.printStackTrace();
            return null;
        }
    }
}

public class MainActivity extends AppCompatActivity {
    EditText plaintextField;
    EditText keyField;
    EditText ciphertextField;
    Button encryptBtn;
    Button decryptBtn;
    Button copyResultBtn;
    TextView outputTextview;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        plaintextField = findViewById(R.id.plaintextField);
        ciphertextField = findViewById(R.id.ciphertextField);
        keyField = findViewById(R.id.keyField);
        encryptBtn = findViewById(R.id.encryptBtn);
        decryptBtn = findViewById(R.id.decryptBtn);
        outputTextview = findViewById(R.id.outputTextview);
        copyResultBtn = findViewById(R.id.copyResultBtn);

        encryptBtn.setOnClickListener(v -> {
            final String plaintext = plaintextField.getText().toString();
            final String key = keyField.getText().toString();

            if (key.isEmpty() || plaintext.isEmpty()) {
                outputTextview.setText("Plaintext and key both must be provided.");
            } else {
                result = AES256.encrypt(plaintext, key);
                outputTextview.setText(String.format("Result: %s", result));

                plaintextField.setText("");
                copyResultBtn.setVisibility(View.VISIBLE);
            }
        });

        decryptBtn.setOnClickListener(v -> {
            final String cipherText = ciphertextField.getText().toString();
            final String key = keyField.getText().toString();

            if (key.isEmpty() || cipherText.isEmpty()) {
                outputTextview.setText("Ciphertext and key both must be provided.");
            } else {
                result = AES256.decrypt(cipherText, key);
                outputTextview.setText(String.format("Result: %s", result));

                ciphertextField.setText("");
                copyResultBtn.setVisibility(View.VISIBLE);
            }
        });

        copyResultBtn.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Cryptographic result", result);
            clipboard.setPrimaryClip(clip);
        });
    }
}