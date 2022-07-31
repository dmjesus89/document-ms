package com.petrotec.documentms.services;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.scheduling.annotation.Async;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

/**
 * FROM THIS http://javasampleapproach.com/spring-framework/spring-boot/firebase-cloud-messaging-server-spring-to-push-notification-example-spring-boot
 */
@Singleton
public class AndroidPushNotificationsService {
	private static final Logger logger = LoggerFactory.getLogger(AndroidPushNotificationsService.class);

	//to get configurations from firebase check:
	//https://console.firebase.google.com/u/0/project/fuel-mobile/settings/general/android:com.petrotec.fuelmobile

	@Value("${petrotec.messaging.firebase-key:}")
	private String FIREBASE_SERVER_KEY;
	@Value("${petrotec.messaging.firebase-url:}")
	private String FIREBASE_API_URL;

	@Async
	public CompletableFuture<String> send(String body) {


		//TODO remove this
		logger.info("SENDING PUSH NOTIFICATION: " + body);

		try{
			HttpClient client = HttpClient.create(new URL(FIREBASE_API_URL));



			/**
			 https://fcm.googleapis.com/fcm/send
			 Content-Type:application/json
			 Authorization:key=FIREBASE_SERVER_KEY*/

			//This might be wrong... check code before migration to micronaut (2020/04/22)
			//The headers were using a RequestInterceptor
			String firebaseResponse = client.toBlocking().retrieve(
					HttpRequest.POST("",body)
							.header("Authorization","key=" + FIREBASE_SERVER_KEY)
							.header("Content-Type","application/json")
					,String.class);

			logger.info("FIREBASE CM RESPONSE----> " + firebaseResponse);

			return CompletableFuture.completedFuture(firebaseResponse);
		}catch (Exception ex){
			ex.printStackTrace();
			logger.error("ERROR WHILE SENDING NOTIFICATION TO",FIREBASE_API_URL);
		}


		return CompletableFuture.completedFuture(null);
	}

}