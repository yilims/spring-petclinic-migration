package org.springframework.samples.petclinic.aws.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class S3Service {

	private final S3Client s3Client;

	public S3Service(@Value("${spring.cloud.aws.credentials.accessKey}") String accessKey,
			@Value("${spring.cloud.aws.credentials.secretKey}") String secretKey,
			@Value("${spring.cloud.aws.region.static}") String region) {
		AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
		this.s3Client = S3Client.builder()
			.region(Region.of(region))
			.credentialsProvider(StaticCredentialsProvider.create(awsCreds))
			.build();
	}

	public String downloadFile(String bucketName, String keyName) throws IOException {
		GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(keyName).build();
		ResponseInputStream<GetObjectResponse> response = s3Client
			.getObject(request -> request.bucket("bucketName").key(keyName));
		String fileContent = StreamUtils.copyToString(response, StandardCharsets.UTF_8);
		return fileContent;
	}

}
