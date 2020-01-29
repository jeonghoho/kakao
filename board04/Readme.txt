1. pom.xml 파일에 파일 업로드를 위한 라이브러리를 추가한다.

		<!-- 파일 업로드를 위해 필요한 라이브러리 -->
		<dependency>
			<groupId>commons-fileupload</groupId>
			<artifactId>commons-fileupload</artifactId>
			<version>1.2.1</version>
		</dependency>
		<dependency>
			<groupId>commons-io</groupId>
			<artifactId>commons-io</artifactId>
			<version>1.4</version>
		</dependency>

2. 파일 업로드를 위해 MultipartResolver를 Bean으로 추가한다. SpringMVC는 MultipartResolver인터페이스를 구현하고 있는 Bean이 등록되면 자동으로 사용하게 된다.

MVCConfig.java에서 다음을 추가함

	/*
	file upload를 위해서 MultipartResolver를 Bean으로 추가해야 한다.
	 */
	@Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(MAX_SIZE); // 10MB
        multipartResolver.setMaxUploadSizePerFile(MAX_SIZE); // 10MB
        multipartResolver.setMaxInMemorySize(0);
        return multipartResolver;
    }

3. 파일 업로드, 다운로드, 게시물 상세보기시 파일 목록 보기를 구현하였다.

글쓰기, 글수정하기 폼에서 enctype="multipart/form-data" 를 추가한다.