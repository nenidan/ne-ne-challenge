package com.github.nenidan.ne_ne_challenge.global.config;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseConfig {
	@PostConstruct
	public void init() throws IOException {
		FileInputStream serviceAccount =
			new FileInputStream("path/to/serviceAccountKey.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
			.setCredentials(GoogleCredentials.fromStream(serviceAccount))
			.build();

		FirebaseApp.initializeApp(options);
	}
}
