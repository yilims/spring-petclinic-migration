package org.springframework.samples.petclinic.aws.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

public class S3Controller {

	@Autowired
	private S3Service s3Service;

	@GetMapping("/download")
	public String downloadFile(@RequestParam String bucketName, @RequestParam String keyName) throws IOException {
		return s3Service.downloadFile(bucketName, keyName);
	}

}
